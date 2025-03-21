package bg.softuni.online_library_system.service.impl;

import bg.softuni.online_library_system.model.dto.AddBookDTO;
import bg.softuni.online_library_system.model.dto.BookDTO;
import bg.softuni.online_library_system.model.entity.AuthorEntity;
import bg.softuni.online_library_system.model.entity.BookEntity;
import bg.softuni.online_library_system.model.entity.BookGenreEntity;
import bg.softuni.online_library_system.model.entity.PublisherEntity;
import bg.softuni.online_library_system.repository.BookRepository;
import bg.softuni.online_library_system.service.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.regex.Pattern;

import static bg.softuni.online_library_system.common.constant.CloudinaryConstants.BOOKS_IMAGES_DIRECTORY;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final BookGenreService bookGenreService;
    private final PublisherService publisherService;
    private final CloudinaryService cloudinaryService;
    private final ModelMapper modelMapper;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, AuthorService authorService,
                           BookGenreService bookGenreService, PublisherService publisherService,
                           CloudinaryService cloudinaryService, ModelMapper modelMapper) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.bookGenreService = bookGenreService;
        this.publisherService = publisherService;
        this.cloudinaryService = cloudinaryService;
        this.modelMapper = modelMapper;
    }

    @Override
    public Long addBook(AddBookDTO addBookDTO) throws IOException {
        Pattern pattern = Pattern.compile("^\\d+$");
        String imageUrl = this.cloudinaryService.uploadFile(addBookDTO.getImageFile(), BOOKS_IMAGES_DIRECTORY);
        AuthorEntity author = this.authorService.getAuthorById(Long.parseLong(addBookDTO.getAuthor()));
        BookGenreEntity genre = this.bookGenreService.getBookGenreByName(addBookDTO.getGenre());
        PublisherEntity publisher = this.publisherService.getPublisherByName(addBookDTO.getPublisher());
        if (publisher == null) {
            Long newPublisherId = this.publisherService.addPublisher(addBookDTO.getPublisher());
            publisher = this.publisherService.getPublisherById(newPublisherId);
        }

        BookEntity newBook = this.modelMapper.map(addBookDTO, BookEntity.class);
        newBook.setImageURL(imageUrl)
                .setCreated(LocalDateTime.now())
                .setAvailable(true)
                .setAuthor(author)
                .setGenre(genre)
                .setPublisher(publisher);

        return this.bookRepository.save(newBook).getId();
    }

    @Override
    public Page<BookDTO> getAllBooksOrderByFirstName(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("title").ascending());

        return this.bookRepository.findAll(pageable)
                .map(bookEntity -> this.modelMapper.map(bookEntity, BookDTO.class));
    }
}