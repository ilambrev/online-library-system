package bg.softuni.online_library_system.service;

import bg.softuni.online_library_system.model.dto.BookCartDTO;

import java.util.List;

public interface BookSelectionService {

    void changeBookStatus(Long id, boolean isBookAvailable);

    boolean isBookAvailable(Long id);

    List<BookCartDTO> getAllBooksById(List<Long> ids);

    void makeBooksAvailable(List<Long> ids);

    void reserveBooks(List<Long> ids, String username);
}