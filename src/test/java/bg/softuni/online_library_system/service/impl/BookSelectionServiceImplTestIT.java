package bg.softuni.online_library_system.service.impl;

import bg.softuni.online_library_system.model.dto.BookDTO;
import bg.softuni.online_library_system.model.entity.*;
import bg.softuni.online_library_system.model.enums.BookGenreEnum;
import bg.softuni.online_library_system.model.enums.BookStatusEnum;
import bg.softuni.online_library_system.model.enums.GenderEnum;
import bg.softuni.online_library_system.model.enums.UserRoleEnum;
import bg.softuni.online_library_system.repository.*;
import bg.softuni.online_library_system.service.BookSelectionService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BookSelectionServiceImplTestIT {

    @Autowired
    private BookSelectionService bookSelectionServiceToTest;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookStatusRepository bookStatusRepository;

    @Autowired
    private BookGenreRepository bookGenreRepository;

    @Autowired
    private PublisherRepository publisherRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @BeforeEach
    void setUp() {
        bookStatusRepository.deleteAll();
        bookRepository.deleteAll();
        publisherRepository.deleteAll();
        authorRepository.deleteAll();
        userRepository.deleteAll();
        userRoleRepository.deleteAll();
    }

    @AfterEach
    void tearDown() {
        bookStatusRepository.deleteAll();
        bookRepository.deleteAll();
        publisherRepository.deleteAll();
        authorRepository.deleteAll();
        userRepository.deleteAll();
        userRoleRepository.deleteAll();
    }

    @Test
    void testChangeBookStatusWithExistingId() {
        boolean isBookAvailable = false;
        Long id = saveTestBookEntity(isBookAvailable);

        bookSelectionServiceToTest.changeBookStatus(id, !isBookAvailable);
        Optional<BookEntity> result = bookRepository.findById(id);

        assertTrue(result.map(BookEntity::isAvailable).orElse(false));
    }

    @Test
    void testisBookAvailableWithExistingId() {
        boolean isBookAvailable = true;
        Long id = saveTestBookEntity(isBookAvailable);

        assertTrue(bookSelectionServiceToTest.isBookAvailable(id));
    }

    @Test
    void testisBookAvailableWithNoExistingId() {
        boolean isBookAvailable = true;
        saveTestBookEntity(isBookAvailable);
        Long id2 = 0L;

        assertFalse(bookSelectionServiceToTest.isBookAvailable(id2));
    }

    @Test
    void testGetAllBooksById() {
        boolean isBookAvailable = true;
        Long id = saveTestBookEntity(isBookAvailable);

        List<BookDTO> result = bookSelectionServiceToTest.getAllBooksById(List.of(id));

        assertEquals(1, result.size());
        assertEquals(id, result.get(0).getId());
        assertEquals("The Hitchhiker's Guide to the Galaxy", result.get(0).getTitle());
        assertEquals("https://someimage.com", result.get(0).getImageURL());
    }

    @Test
    void testMakeBooksAvailable() {
        boolean isBookAvailable = false;
        Long id = saveTestBookEntity(isBookAvailable);

        bookSelectionServiceToTest.makeBooksAvailable(List.of(id));

        Optional<BookEntity> result = bookRepository.findById(id);

        assertTrue(result.map(BookEntity::isAvailable).orElse(false));
    }

    @Test
    void testReserveBooks() {
        UserRoleEntity testUserRoleEntity = new UserRoleEntity()
                .setRole(UserRoleEnum.USER)
                .setDescription("User");

        UserRoleEntity savedTestUserRole = userRoleRepository.save(testUserRoleEntity);

        UserEntity testUserEntity = createTestUserEntity();
        testUserEntity.setPassword(passwordEncoder.encode("1234567890"));
        testUserEntity.setRole(savedTestUserRole);

        UserEntity savedTestUserEntity = userRepository.save(testUserEntity);

        boolean isBookAvailable = false;
        Long id = saveTestBookEntity(isBookAvailable);

        bookSelectionServiceToTest.reserveBooks(List.of(id), savedTestUserEntity.getUsername());

        List<BookStatusEntity> bookStatusEntities = bookStatusRepository
                .findAllByUserIdAndStatus(savedTestUserEntity.getId(), BookStatusEnum.RESERVED);

        assertEquals(1, bookStatusEntities.size());
        assertEquals(id, bookStatusEntities.get(0).getBook().getId());
        assertEquals(BookStatusEnum.RESERVED, bookStatusEntities.get(0).getStatus());
        assertEquals(savedTestUserEntity.getId(), bookStatusEntities.get(0).getUser().getId());
    }

    private Long saveTestBookEntity(boolean isBookAvailable) {
        PublisherEntity testPublisherEntity = createTestPublisherEntity();
        testPublisherEntity.setCreated(LocalDateTime.now());
        PublisherEntity savedTestPublisherEntity = publisherRepository.save(testPublisherEntity);

        BookGenreEntity testBookGenreEntity = bookGenreRepository.findByGenre(BookGenreEnum.SCIENCE_FICTION);

        AuthorEntity testAuthorEntity = createTestAuthor();
        AuthorEntity savedTestAuthorEntity = authorRepository.save(testAuthorEntity);

        BookEntity testBookEntity = createTestBook();
        testBookEntity.setPublisher(savedTestPublisherEntity);
        testBookEntity.setGenre(testBookGenreEntity);
        testBookEntity.setCreated(LocalDateTime.now());
        testBookEntity.setAuthor(savedTestAuthorEntity);
        testBookEntity.setAvailable(isBookAvailable);

        BookEntity savedTestBookEntity = bookRepository.save(testBookEntity);

        return savedTestBookEntity.getId();
    }

    private static UserEntity createTestUserEntity() {
        return new UserEntity()
                .setFirstName("Dave")
                .setLastName("Lombardo")
                .setUsername("lombardo")
                .setEmail("lombardo@test.com")
                .setPhoneNumber("+359555111222")
                .setAddress("Somewhere")
                .setImageURL("https://someimage.com")
                .setGender(GenderEnum.MALE)
                .setActive(true);
    }

    private static AuthorEntity createTestAuthor() {
        return new AuthorEntity()
                .setFirstName("Douglas")
                .setLastName("Adams")
                .setPresentation("Great author.")
                .setImageURL("https://someimage.com");
    }

    private static BookEntity createTestBook() {
        return new BookEntity()
                .setTitle("The Hitchhiker's Guide to the Galaxy")
                .setDescription("Great book!")
                .setIsbn("9781400052929")
                .setYear(2004)
                .setPages(224)
                .setImageURL("https://someimage.com");
    }

    private static PublisherEntity createTestPublisherEntity() {
        return new PublisherEntity()
                .setName("Crown Publishing");
    }
}