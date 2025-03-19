package bg.softuni.online_library_system.service.impl;

import bg.softuni.online_library_system.model.dto.AddBookDTO;
import bg.softuni.online_library_system.model.entity.AuthorEntity;
import bg.softuni.online_library_system.model.entity.BookEntity;
import bg.softuni.online_library_system.model.entity.BookGenreEntity;
import bg.softuni.online_library_system.repository.BookRepository;
import bg.softuni.online_library_system.service.AuthorService;
import bg.softuni.online_library_system.service.BookGenreService;
import bg.softuni.online_library_system.service.BookService;
import bg.softuni.online_library_system.service.CloudinaryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;

import static bg.softuni.online_library_system.common.constant.CloudinaryConstants.BOOKS_IMAGES_DIRECTORY;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final BookGenreService bookGenreService;
    private final CloudinaryService cloudinaryService;
    private final ModelMapper modelMapper;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, AuthorService authorService,
                           BookGenreService bookGenreService, CloudinaryService cloudinaryService,
                           ModelMapper modelMapper) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.bookGenreService = bookGenreService;
        this.cloudinaryService = cloudinaryService;
        this.modelMapper = modelMapper;
    }

    @Override
    public Long addBook(AddBookDTO addBookDTO) throws IOException {
        String imageUrl = this.cloudinaryService.uploadFile(addBookDTO.getImageFile(), BOOKS_IMAGES_DIRECTORY);
        AuthorEntity author = this.authorService.getAuthorById(Long.parseLong(addBookDTO.getAuthor()));
        BookGenreEntity genre = this.bookGenreService.getBookGenreByName(addBookDTO.getGenre());

        BookEntity newBook = this.modelMapper.map(addBookDTO, BookEntity.class);
        newBook.setImageURL(imageUrl)
                .setCreated(LocalDateTime.now())
                .setAvailable(true)
                .setAuthor(author)
                .setGenre(genre);

//        return this.bookRepository.save(newBook).getId();
        return 0L;
    }
}