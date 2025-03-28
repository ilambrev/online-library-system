package bg.softuni.online_library_system.service;

import bg.softuni.online_library_system.model.dto.AddBookDTO;
import bg.softuni.online_library_system.model.dto.BookAboutDTO;
import bg.softuni.online_library_system.model.dto.BookCartDTO;
import bg.softuni.online_library_system.model.dto.BookDTO;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.util.List;

public interface BookService {

    Long addBook(AddBookDTO addBookDTO) throws IOException;

    Page<BookDTO> getAllBooksOrderByFirstName(int page, int size);

    BookAboutDTO getBookById(Long id);

    void changeBookStatus(Long id, boolean isBookAvailable);

    List<BookCartDTO> getAllBooksById(List<Long> ids);

    boolean isBookAvailable(Long id);
}