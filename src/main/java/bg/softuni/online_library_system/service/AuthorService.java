package bg.softuni.online_library_system.service;

import bg.softuni.online_library_system.model.dto.AddAuthorDTO;
import bg.softuni.online_library_system.model.dto.AuthorDTO;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.util.List;

public interface AuthorService {

    Long addAuthor(AddAuthorDTO addAuthorDTO) throws IOException;

    AuthorDTO getAuthor(Long id);

    List<AuthorDTO> getAllAuthorsOrderByFirstName();

    Page<AuthorDTO> getAllAuthorsOrderByFirstName(int page, int size);

    List<AuthorDTO> getAllAuthors();

    List<AuthorDTO> getAuthorsByFirstNameStartingWith(String letter);

    List<AuthorDTO> getAuthorsByLastNameStartingWith(String letter);
}