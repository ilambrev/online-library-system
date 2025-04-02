package bg.softuni.online_library_system.service;

import bg.softuni.online_library_system.model.dto.BookDTO;

import java.util.List;

public interface BookSelectionService {

    void changeBookStatus(Long id, boolean isBookAvailable);

    boolean isBookAvailable(Long id);

    List<BookDTO> getAllBooksById(List<Long> ids);

    void makeBooksAvailable(List<Long> ids);

    void reserveBooks(List<Long> ids, String username);
}