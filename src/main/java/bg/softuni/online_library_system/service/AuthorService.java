package bg.softuni.online_library_system.service;

import bg.softuni.online_library_system.model.dto.AddAuthorDTO;

import java.io.IOException;

public interface AuthorService {

    boolean addAuthor(AddAuthorDTO addAuthorDTO) throws IOException;
}