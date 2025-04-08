package bg.softuni.online_library_system.testutils;

import bg.softuni.online_library_system.model.entity.*;
import bg.softuni.online_library_system.model.enums.BookGenreEnum;
import bg.softuni.online_library_system.model.enums.GenderEnum;
import bg.softuni.online_library_system.model.enums.UserRoleEnum;
import bg.softuni.online_library_system.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TestUtil {
    private final BookRepository bookRepository;
    private final BookGenreRepository bookGenreRepository;
    private final PublisherRepository publisherRepository;
    private final AuthorRepository authorRepository;
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public TestUtil(BookRepository bookRepository, BookGenreRepository bookGenreRepository,
                    PublisherRepository publisherRepository, AuthorRepository authorRepository,
                    UserRepository userRepository, UserRoleRepository userRoleRepository,
                    PasswordEncoder passwordEncoder) {
        this.bookRepository = bookRepository;
        this.bookGenreRepository = bookGenreRepository;
        this.publisherRepository = publisherRepository;
        this.authorRepository = authorRepository;
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserEntity createUser() {
        UserRoleEntity userRole = this.userRoleRepository.findByRole(UserRoleEnum.ADMIN);

        UserEntity user = new UserEntity()
                .setFirstName("Dave")
                .setLastName("Lombardo")
                .setUsername("lombardo")
                .setEmail("lombardo@test.com")
                .setPhoneNumber("+359555111222")
                .setAddress("Somewhere")
                .setImageURL("https://someimage.com")
                .setGender(GenderEnum.MALE)
                .setActive(true)
                .setPassword(this.passwordEncoder.encode("0123456789"))
                .setRole(userRole);

        return this.userRepository.save(user);
    }

    public BookEntity createBook() {
        AuthorEntity author = createAuthor();
        PublisherEntity publisher = createPublisher();
        BookGenreEntity genre = this.bookGenreRepository.findByGenre(BookGenreEnum.SCIENCE_FICTION);
        BookEntity book = new BookEntity()
                .setTitle("The Hitchhiker's Guide to the Galaxy")
                .setDescription("Great book!")
                .setIsbn("9781400052929")
                .setYear(2004)
                .setPages(224)
                .setImageURL("https://someimage.com")
                .setAuthor(author)
                .setPublisher(publisher)
                .setGenre(genre)
                .setAvailable(true)
                .setCreated(LocalDateTime.now());

        return this.bookRepository.save(book);
    }

    public PublisherEntity createPublisher() {
        PublisherEntity publisher = new PublisherEntity()
                .setName("Crown Publishing")
                .setCreated(LocalDateTime.now());

        return this.publisherRepository.save(publisher);
    }

    public AuthorEntity createAuthor() {
        AuthorEntity author = new AuthorEntity()
                .setFirstName("Douglas")
                .setLastName("Adams")
                .setPresentation("Great author.")
                .setImageURL("https://someimage.com");

        return this.authorRepository.save(author);
    }
}