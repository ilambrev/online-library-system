package bg.softuni.online_library_system.service.impl;

import bg.softuni.online_library_system.exception.ObjectNotFoundException;
import bg.softuni.online_library_system.model.dto.AddAuthorDTO;
import bg.softuni.online_library_system.model.dto.AuthorDTO;
import bg.softuni.online_library_system.model.dto.AuthorRestDTO;
import bg.softuni.online_library_system.model.entity.AuthorEntity;
import bg.softuni.online_library_system.model.entity.BookEntity;
import bg.softuni.online_library_system.repository.AuthorRepository;
import bg.softuni.online_library_system.repository.BookRepository;
import bg.softuni.online_library_system.service.AuthorService;
import bg.softuni.online_library_system.service.CloudinaryService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static bg.softuni.online_library_system.common.constant.CloudinaryConstants.AUTHORS_IMAGES_DIRECTORY;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final CloudinaryService cloudinaryService;
    private final ModelMapper modelMapper;

    public AuthorServiceImpl(AuthorRepository authorRepository, BookRepository bookRepository,
                             CloudinaryService cloudinaryService, ModelMapper modelMapper) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.cloudinaryService = cloudinaryService;
        this.modelMapper = modelMapper;
    }

    @Override
    public Long addAuthor(AddAuthorDTO addAuthorDTO) throws IOException {
        AuthorEntity newAuthor = this.modelMapper.map(addAuthorDTO, AuthorEntity.class);

        if (addAuthorDTO.getImageFile().isEmpty()) {
            newAuthor.setImageURL("/images/author.png");
        } else {
            String imageUrl = this.cloudinaryService.uploadFile(addAuthorDTO.getImageFile(), AUTHORS_IMAGES_DIRECTORY);
            newAuthor.setImageURL(imageUrl);
        }

        return this.authorRepository.save(newAuthor).getId();
    }

    @Override
    public AuthorDTO getAuthor(Long id) {
        AuthorEntity author = getAuthorById(id);
        if (author == null) {
            throw new ObjectNotFoundException(String.format("Author with id %d not found.", id));
        }

        return this.modelMapper.map(author, AuthorDTO.class);
    }

    @Override
    public AuthorEntity getAuthorById(Long id) {
        return this.authorRepository.findById(id).orElse(null);
    }

    @Override
    public Page<AuthorDTO> getAllAuthorsOrderByFirstName(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("firstName").ascending());

        Page<AuthorEntity> authorsPage = this.authorRepository.findAll(pageable);
        List<AuthorEntity> authors = authorsPage.getContent();

        List<Long> authorIds = authors.stream().map(AuthorEntity::getId).toList();
        List<BookEntity> books = this.bookRepository.findBooksByAuthorIds(authorIds);

        Map<Long, List<BookEntity>> booksByAuthor = books.stream()
                .collect(Collectors.groupingBy(BookEntity::getId));

        authors.forEach(author -> author
                .setBooks(booksByAuthor.getOrDefault(author.getId(), new ArrayList<>())));

        return authorsPage
                .map(authorEntity -> this.modelMapper.map(authorEntity, AuthorDTO.class));
    }

    @Override
    public List<AuthorRestDTO> getAllAuthors() {
        return mapAuthors(authorRepository.findAll());
    }

    @Override
    public List<AuthorRestDTO> getAuthorsByFirstNameStartingWith(String letter) {
        return mapAuthors(this.authorRepository.findByFirstNameStartingWith(letter));
    }

    @Override
    public List<AuthorRestDTO> getAuthorsByLastNameStartingWith(String letter) {
        return mapAuthors(this.authorRepository.findByLastNameStartingWith(letter));
    }

    private List<AuthorRestDTO> mapAuthors(List<AuthorEntity> authors) {
        return authors.stream()
                .map(a -> this.modelMapper.map(a, AuthorRestDTO.class))
                .toList();
    }
}