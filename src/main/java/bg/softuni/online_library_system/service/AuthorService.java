package bg.softuni.online_library_system.service;

import bg.softuni.online_library_system.model.dto.AddAuthorDTO;
import bg.softuni.online_library_system.model.dto.AuthorDTO;
import bg.softuni.online_library_system.model.dto.AuthorRestDTO;
import bg.softuni.online_library_system.model.entity.AuthorEntity;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.util.List;

public interface AuthorService {

    Long addAuthor(AddAuthorDTO addAuthorDTO) throws IOException;

    AuthorDTO getAuthor(Long id);

    AuthorEntity getAuthorById(Long id);

    Page<AuthorDTO> getAllAuthorsOrderByFirstName(int page, int size);

    List<AuthorRestDTO> getAllAuthors();

    List<AuthorRestDTO> getAuthorsByFirstNameStartingWith(String letter);

    List<AuthorRestDTO> getAuthorsByLastNameStartingWith(String letter);
}