package bg.softuni.online_library_system.service;

import bg.softuni.online_library_system.model.dto.AddBookDTO;

import java.io.IOException;

public interface BookService {

    Long addBook(AddBookDTO addBookDTO) throws IOException;
}