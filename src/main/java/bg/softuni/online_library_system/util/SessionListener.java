package bg.softuni.online_library_system.util;

import bg.softuni.online_library_system.service.BookService;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SessionListener implements HttpSessionListener {
    private final BookService bookService;

    @Autowired
    public SessionListener(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        HttpSessionListener.super.sessionDestroyed(se);

        Object selectedBooksObject = se.getSession().getAttribute("selectedBooks");
        if (selectedBooksObject instanceof List<?>) {
            List<Long> selectedBooksIds = ((List<?>) selectedBooksObject).stream()
                    .filter(item -> item instanceof Long)
                    .map(item -> (Long) item)
                    .toList();
            if (!selectedBooksIds.isEmpty()) {
                this.bookService.makeBooksAvailable(selectedBooksIds);
            }
        }
    }
}