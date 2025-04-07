package bg.softuni.online_library_system.service.impl;

import bg.softuni.online_library_system.model.dto.BookStatusDTO;
import bg.softuni.online_library_system.model.entity.*;
import bg.softuni.online_library_system.model.enums.BookGenreEnum;
import bg.softuni.online_library_system.model.enums.BookStatusEnum;
import bg.softuni.online_library_system.model.enums.GenderEnum;
import bg.softuni.online_library_system.model.enums.UserRoleEnum;
import bg.softuni.online_library_system.repository.BookRepository;
import bg.softuni.online_library_system.repository.BookStatusRepository;
import bg.softuni.online_library_system.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookStatusServiceImplTest {

    private BookStatusServiceImpl serviceToTest;

    @Mock
    private BookStatusRepository mockBookStatusRepository;

    @Mock
    private BookRepository mockBookRepository;

    @Mock
    private UserService mockUserService;

    @Mock
    private ModelMapper mockModelMapper;

    @BeforeEach
    void setUp() {
        serviceToTest = new BookStatusServiceImpl(mockBookStatusRepository, mockBookRepository,
                mockUserService, mockModelMapper);
    }

    @Test
    void testGetAllByUserAndStatusWithExistingUser() {
        UserEntity testUserEntity = createTestUser();
        testUserEntity.setId(1L);
        BookEntity testBookEntity = createTestBook();
        testBookEntity.setId(1L);
        BookStatusEntity testBookStatusEntity = createTestBookStatus();
        testBookStatusEntity.setId(1L);
        testBookStatusEntity.setUser(testUserEntity);
        testBookStatusEntity.setBook(testBookEntity);
        BookStatusDTO testBookStatusDTO = createTestBookStatusDTO();
        testBookStatusDTO.setId(1L);

        when(mockUserService.getUserByUsername(testUserEntity.getUsername())).thenReturn(testUserEntity);
        when(mockBookStatusRepository.findAllByUserIdAndStatus(testUserEntity.getId(), BookStatusEnum.BORROWED))
                .thenReturn(List.of(testBookStatusEntity));
        when(mockModelMapper.map(testBookStatusEntity, BookStatusDTO.class)).thenReturn(testBookStatusDTO);

        List<BookStatusDTO> result = serviceToTest
                .getAllByUserIdAndStatus(testUserEntity.getUsername(), BookStatusEnum.BORROWED);

        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals("The Hitchhiker's Guide to the Galaxy", result.get(0).getBookTitle());
        assertEquals("https://someimage.com", result.get(0).getBookImageURL());
    }

    @Test
    void testGetAllByUserAndStatusWithNonExistingUser() {
        UserEntity testUserEntity = createTestUser();
        testUserEntity.setId(2L);

        when(mockUserService.getUserByUsername(testUserEntity.getUsername())).thenReturn(null);

        List<BookStatusDTO> result = serviceToTest
                .getAllByUserIdAndStatus(testUserEntity.getUsername(), BookStatusEnum.BORROWED);

        assertEquals(0, result.size());
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
                .setAvailable(false)
                .setGenre(new BookGenreEntity().setGenre(BookGenreEnum.SCIENCE_FICTION).setDescription("Great genre"))
                .setPublisher(new PublisherEntity().setName("Crown Publishing").setCreated(LocalDateTime.now()));
    }

    private static BookStatusEntity createTestBookStatus() {
        return new BookStatusEntity()
                .setStatus(BookStatusEnum.BORROWED)
                .setReservationDate(LocalDateTime.of(2025, Month.MARCH, 12, 10, 24))
                .setBorrowDate(LocalDateTime.of(2025, Month.MARCH, 12, 15, 16));
    }

    private static BookStatusDTO createTestBookStatusDTO() {
        return new BookStatusDTO()
                .setBookTitle("The Hitchhiker's Guide to the Galaxy")
                .setBookImageURL("https://someimage.com");
    }
}