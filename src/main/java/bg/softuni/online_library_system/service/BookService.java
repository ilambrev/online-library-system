package bg.softuni.online_library_system.service;

import bg.softuni.online_library_system.model.dto.AddBookDTO;
import bg.softuni.online_library_system.model.dto.BookDTO;
import org.springframework.data.domain.Page;

import java.io.IOException;

public interface BookService {

    Long addBook(AddBookDTO addBookDTO) throws IOException;

    Page<BookDTO> getAllBooksOrderByFirstName(int page, int size);

    BookDTO getBookById(Long id);
}