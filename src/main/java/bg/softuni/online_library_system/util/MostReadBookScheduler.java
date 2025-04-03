package bg.softuni.online_library_system.util;

import bg.softuni.online_library_system.model.entity.BookEntity;
import bg.softuni.online_library_system.model.entity.MostReadBookEntity;
import bg.softuni.online_library_system.repository.BookRepository;
import bg.softuni.online_library_system.repository.MostReadBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Limit;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class MostReadBookScheduler {
    private final BookRepository bookRepository;
    private final MostReadBookRepository mostReadBookRepository;

    @Autowired
    public MostReadBookScheduler(BookRepository bookRepository, MostReadBookRepository mostReadBookRepository) {
        this.bookRepository = bookRepository;
        this.mostReadBookRepository = mostReadBookRepository;
    }

    @Transactional
    @Scheduled(cron = "0 */5 * * * *")
    //    @Scheduled(cron = "0 0 0 * * *")
    public void updateMostBorrowedBooks() {
        List<BookEntity> books = this.bookRepository.findByOrderByBorrowCounterDesc(Limit.of(3));
        List<MostReadBookEntity> mostReadBooks = this.mostReadBookRepository.findAll();

        if (books.size() == mostReadBooks.size()) {
            for (int i = 0; i < mostReadBooks.size(); i++) {
                mostReadBooks.get(i).setBookId(books.get(i).getId())
                        .setTitle(books.get(i).getTitle())
                        .setImageURL(books.get(i).getImageURL())
                        .setBorrowCounter(books.get(i).getBorrowCounter())
                        .setAuthorId(books.get(i).getAuthor().getId())
                        .setAuthorFirstName(books.get(i).getAuthor().getFirstName())
                        .setAuthorLastName(books.get(i).getAuthor().getLastName())
                ;
            }
        }

        this.mostReadBookRepository.saveAll(mostReadBooks);

        System.out.println("Updated most read books at: " + LocalDateTime.now());
    }
}