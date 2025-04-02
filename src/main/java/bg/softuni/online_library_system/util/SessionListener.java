package bg.softuni.online_library_system.util;

import bg.softuni.online_library_system.service.BookSelectionService;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SessionListener implements HttpSessionListener {
    private final BookSelectionService bookSelectionService;

    @Autowired
    public SessionListener(BookSelectionService bookSelectionService) {

        this.bookSelectionService = bookSelectionService;
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
                this.bookSelectionService.makeBooksAvailable(selectedBooksIds);
            }
        }
    }
}