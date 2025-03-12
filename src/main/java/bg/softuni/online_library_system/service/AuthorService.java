package bg.softuni.online_library_system.service;

import bg.softuni.online_library_system.model.dto.AddAuthorDTO;
import bg.softuni.online_library_system.model.dto.AuthorDTO;

import java.io.IOException;
import java.util.List;

public interface AuthorService {

    Long addAuthor(AddAuthorDTO addAuthorDTO) throws IOException;

    AuthorDTO getAuthor(Long id);

    long getAuthorsCount();

    List<AuthorDTO> getAllAuthorsOrderByFirstName();

    List<AuthorDTO> getAllAuthors();

    List<AuthorDTO> getAuthorsByFirstNameStartingWith(String letter);

    List<AuthorDTO> getAuthorsByLastNameStartingWith(String letter);
}