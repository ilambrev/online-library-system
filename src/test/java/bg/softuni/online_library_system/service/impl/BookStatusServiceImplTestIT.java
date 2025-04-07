package bg.softuni.online_library_system.service.impl;

import bg.softuni.online_library_system.model.entity.*;
import bg.softuni.online_library_system.model.enums.BookGenreEnum;
import bg.softuni.online_library_system.model.enums.BookStatusEnum;
import bg.softuni.online_library_system.model.enums.GenderEnum;
import bg.softuni.online_library_system.model.enums.UserRoleEnum;
import bg.softuni.online_library_system.repository.*;
import bg.softuni.online_library_system.service.BookStatusService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class BookStatusServiceImplTestIT {

    @Autowired
    private BookStatusService bookStatusServiceToTest;

    @Autowired
    private BookStatusRepository bookStatusRepository;

    @Autowired
    private BookRepository bookRepository;

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
    private ModelMapper modelMapper;
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
    void testCancelReservation() {
        UserRoleEntity testUserRoleEntity = new UserRoleEntity()
                .setRole(UserRoleEnum.USER)
                .setDescription("User");

        UserRoleEntity savedTestUserRole = userRoleRepository.save(testUserRoleEntity);

        UserEntity testUserEntity = createTestUserEntity();
        testUserEntity.setPassword(passwordEncoder.encode("1234567890"));
        testUserEntity.setRole(savedTestUserRole);

        UserEntity savedTestUserEntity = userRepository.save(testUserEntity);

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

        BookEntity savedTestBookEntity = bookRepository.save(testBookEntity);

        BookStatusEntity testBookStatusEntity = createTestBookStatus();
        testBookStatusEntity
                .setUser(savedTestUserEntity)
                .setBook(savedTestBookEntity)
                .setReservationDate(LocalDateTime.now());

        BookStatusEntity savedTestBookStatusEntity = bookStatusRepository.save(testBookStatusEntity);

        bookStatusServiceToTest.cancelReservation(savedTestBookStatusEntity.getId());

        Optional<BookStatusEntity> result = bookStatusRepository.findById(savedTestBookStatusEntity.getId());

        assertEquals(BookStatusEnum.CANCELLED_RESERVATION.name(),
                result.<Object>map(bookStatusEntity -> bookStatusEntity.getStatus()
                        .name()).orElse(null));
        assertTrue(result.map(bookStatusEntity -> bookStatusEntity.getBook().isAvailable())
                .orElse(false));
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
                .setImageURL("https://someimage.com")
                .setAvailable(false);
    }

    private static BookStatusEntity createTestBookStatus() {
        return new BookStatusEntity()
                .setStatus(BookStatusEnum.RESERVED);
    }

    private static PublisherEntity createTestPublisherEntity() {
        return new PublisherEntity()
                .setName("Crown Publishing");
    }
}