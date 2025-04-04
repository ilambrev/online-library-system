package bg.softuni.online_library_system.util;

import bg.softuni.online_library_system.model.entity.BookEntity;
import bg.softuni.online_library_system.model.entity.BookStatusEntity;
import bg.softuni.online_library_system.model.enums.BookStatusEnum;
import bg.softuni.online_library_system.repository.BookRepository;
import bg.softuni.online_library_system.repository.BookStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static bg.softuni.online_library_system.common.constant.CartConstants.BOOK_RESERVATION_PERIOD_IN_HOURS;

@Component
public class ExpiredBookReservationScheduler {
    private final BookRepository bookRepository;
    private final BookStatusRepository bookStatusRepository;

    @Autowired
    public ExpiredBookReservationScheduler(BookRepository bookRepository, BookStatusRepository bookStatusRepository) {
        this.bookRepository = bookRepository;
        this.bookStatusRepository = bookStatusRepository;
    }

    @Transactional
    // @Scheduled(cron = "0 */3 * * * *") // For testing purposes on every 3 min
    @Scheduled(cron = "0 0 * * * *")
    public void cancelExpiredBookReservations() {
        LocalDateTime expirationDate = LocalDateTime.now().minusHours(BOOK_RESERVATION_PERIOD_IN_HOURS);
        List<BookStatusEntity> expiredReservations = this.bookStatusRepository
                .findAllByStatusAndReservationDateBefore(BookStatusEnum.RESERVED, expirationDate);

        List<BookEntity> books = this.bookRepository.findBooksByIds(expiredReservations
                .stream().map(r -> r.getBook().getId()).toList());

        expiredReservations.forEach(r -> r.setStatus(BookStatusEnum.EXPIRED_RESERVATION));
        books.forEach(b -> b.setAvailable(true));

        this.bookStatusRepository.saveAll(expiredReservations);
        this.bookRepository.saveAll(books);

        System.out.println("Expired book reservations canceled at: " + LocalDateTime.now());
    }
}