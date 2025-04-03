package bg.softuni.online_library_system.util;

import bg.softuni.online_library_system.model.entity.BookEntity;
import bg.softuni.online_library_system.model.entity.BookStatusEntity;
import bg.softuni.online_library_system.repository.BookRepository;
import bg.softuni.online_library_system.repository.BookStatusRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Arrays;
import java.util.List;

@Component
public class BookBorrowInterceptor implements HandlerInterceptor {
    private final BookRepository bookRepository;
    private final BookStatusRepository bookStatusRepository;

    @Autowired
    public BookBorrowInterceptor(BookRepository bookRepository, BookStatusRepository bookStatusRepository) {
        this.bookRepository = bookRepository;
        this.bookStatusRepository = bookStatusRepository;
    }

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request,
                             @NonNull HttpServletResponse response,
                             @NonNull Object handler) {

        String[] bookIds = request.getParameterValues("id");
        if (bookIds != null) {
            List<Long> statusIds = Arrays.stream(bookIds)
                    .map(Long::parseLong)
                    .toList();

            List<BookStatusEntity> bookStatuses = this.bookStatusRepository.findBookStatusesByIds(statusIds);
            List<BookEntity> books = this.bookRepository.findBooksByIds(bookStatuses
                    .stream().map(s -> s.getBook().getId()).toList());
            books.forEach(b -> b.setBorrowCounter(b.getBorrowCounter() + 1));

            this.bookRepository.saveAll(books);
        }

        return true;
    }
}