package bg.softuni.online_library_system.service.impl;

import bg.softuni.online_library_system.model.entity.*;
import bg.softuni.online_library_system.model.enums.BookGenreEnum;
import bg.softuni.online_library_system.model.enums.BookStatusEnum;
import bg.softuni.online_library_system.model.enums.GenderEnum;
import bg.softuni.online_library_system.model.enums.UserRoleEnum;
import bg.softuni.online_library_system.repository.BookStatusRepository;
import bg.softuni.online_library_system.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserDetailsServiceImplTest {

    private UserDetailsServiceImpl serviceToTest;

    @Mock
    private UserRepository mockUserRepository;

    @Mock
    private BookStatusRepository mockBookStatusRepository;

    @BeforeEach
    void setUp() {
        serviceToTest = new UserDetailsServiceImpl(mockUserRepository, mockBookStatusRepository);
    }

    @Test
    void testUserNotFoundException() {
        assertThrows(UsernameNotFoundException.class,
                () -> serviceToTest.loadUserByUsername("johnnie"));
    }

    @Test
    void testUserFound() {
        UserEntity testUserEntity = createTestUser();
        when(mockUserRepository.findByUsername(testUserEntity.getUsername()))
                .thenReturn(Optional.of(testUserEntity));

        UserDetails userDetails = serviceToTest.loadUserByUsername(testUserEntity.getUsername());

        assertNotNull(userDetails);
        assertEquals(testUserEntity.getUsername(), userDetails.getUsername(),
                "Username is not populated properly.");
        assertEquals(testUserEntity.getPassword(), userDetails.getPassword(),
                "Password is not populated properly.");
        assertEquals(1, userDetails.getAuthorities().size(),
                "Authorities are not populated properly.");
        assertTrue(containsAuthority(userDetails, "ROLE_" + UserRoleEnum.ADMIN),
                "The user role is not ADMIN");
    }

    @Test
    void testHasOverdueBooksMethod() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        UserEntity testUserEntity = createTestUser();
        AuthorEntity testAuthorEntity = createTestAuthor();
        BookEntity testBookEntity = createTestBook();
        testBookEntity.setAuthor(testAuthorEntity);
        BookStatusEntity testBookStatusEntity = createTestBookStatus();
        testBookStatusEntity.setBook(testBookEntity);
        testBookStatusEntity.setUser(testUserEntity);

        List<BookStatusEntity> statusesBorrowed = new ArrayList<>();
        statusesBorrowed.add(testBookStatusEntity);

        assertFalse((Boolean) getHasOverdueBooks().invoke(serviceToTest, statusesBorrowed));

        LocalDateTime borrowDate = LocalDateTime.of(2025, Month.FEBRUARY, 25, 14, 33);

        statusesBorrowed.clear();
        statusesBorrowed.add(testBookStatusEntity.setBorrowDate(borrowDate));

        assertTrue((Boolean) getHasOverdueBooks().invoke(serviceToTest, statusesBorrowed));

        borrowDate = LocalDateTime.now().minusDays(10);
        statusesBorrowed.add(testBookStatusEntity.setBorrowDate(borrowDate));

        assertFalse((Boolean) getHasOverdueBooks().invoke(serviceToTest, statusesBorrowed));
    }

    private boolean containsAuthority(UserDetails userDetails, String expectedAuthority) {
        return userDetails.getAuthorities()
                .stream()
                .anyMatch(a -> expectedAuthority.equals(a.getAuthority()));
    }

    private static UserEntity createTestUser() {
        return new UserEntity()
                .setFirstName("Dave")
                .setLastName("Lombardo")
                .setUsername("lombardo")
                .setPassword("")
                .setEmail("")
                .setPhoneNumber("+359555111222")
                .setAddress("Somewhere")
                .setImageURL("https://someimage.com")
                .setGender(GenderEnum.MALE)
                .setRole(new UserRoleEntity().setRole(UserRoleEnum.ADMIN).setDescription("Admin"))
                .setActive(false);
    }

    private static AuthorEntity createTestAuthor() {
        return new AuthorEntity()
                .setFirstName("Douglas")
                .setLastName("Adams")
                .setPresentation("Great author.")
                .setImageURL("https://someimage.com")
                .setBooks(List.of(createTestBook()));
    }

    private static BookEntity createTestBook() {
        return new BookEntity()
                .setTitle("The Hitchhiker's Guide to the Galaxy")
                .setDescription("Great book!")
                .setIsbn("9781400052929")
                .setYear(2004)
                .setPages(224)
                .setImageURL("https://someimage.com")
                .setCreated(LocalDateTime.of(2025, Month.FEBRUARY, 25, 14, 33))
                .setBorrowCounter(1)
                .setAvailable(true)
                .setGenre(new BookGenreEntity().setGenre(BookGenreEnum.SCIENCE_FICTION).setDescription("Great genre"))
                .setPublisher(new PublisherEntity().setName("Crown Publishing").setCreated(LocalDateTime.now()));
    }

    private static BookStatusEntity createTestBookStatus() {
        return new BookStatusEntity()
                .setStatus(BookStatusEnum.BORROWED);
    }

    private Method getHasOverdueBooks() throws NoSuchMethodException {
        Method method = UserDetailsServiceImpl.class.getDeclaredMethod("hasOverdueBooks", List.class);
        method.setAccessible(true);

        return method;
    }
}