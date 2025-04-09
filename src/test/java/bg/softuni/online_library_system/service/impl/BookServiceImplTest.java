package bg.softuni.online_library_system.service.impl;

import bg.softuni.online_library_system.model.dto.AddBookDTO;
import bg.softuni.online_library_system.model.entity.AuthorEntity;
import bg.softuni.online_library_system.model.entity.BookEntity;
import bg.softuni.online_library_system.model.entity.BookGenreEntity;
import bg.softuni.online_library_system.model.entity.PublisherEntity;
import bg.softuni.online_library_system.model.enums.BookGenreEnum;
import bg.softuni.online_library_system.repository.BookRepository;
import bg.softuni.online_library_system.service.AuthorService;
import bg.softuni.online_library_system.service.BookGenreService;
import bg.softuni.online_library_system.service.CloudinaryService;
import bg.softuni.online_library_system.service.PublisherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookServiceImplTest {

    private BookServiceImpl serviceToTest;

    @Mock
    private CloudinaryService mockCloudinaryService;

    @Mock
    private AuthorService mockAuthorService;

    @Mock
    private BookGenreService mockBookGenreService;

    @Mock
    private PublisherService mockPublisherService;

    @Mock
    private BookRepository mockBookRepository;

    @Mock
    private ModelMapper mockModelMapper;

    @BeforeEach
    void setUp() {
        serviceToTest = new BookServiceImpl(mockBookRepository, mockAuthorService, mockBookGenreService,
                mockPublisherService, mockCloudinaryService, mockModelMapper);
    }

    @Test
    void testAddBook() throws IOException {
        MultipartFile mockFile = mock(MultipartFile.class);
        Long bookId = 2L;
        Long authorId = 1L;
        Long bookGenreId = 3L;
        Long publisherId = 4L;

        AddBookDTO testAddBookDTO = createTestAddBookDTO();

        AuthorEntity testAuthorEntity = createTestAuthorEntity();
        testAuthorEntity.setId(authorId);

        testAddBookDTO
                .setAuthor(authorId.toString())
                .setImageFile(mockFile);

        BookGenreEntity testBookGenreEntity = createTestBookGenreEntity();
        testBookGenreEntity.setId(bookGenreId);

        PublisherEntity testPublisherEntity = createTestPublisherEntity();
        testPublisherEntity.setId(publisherId);

        BookEntity testBookEntity = createTestBookEntity();
        testBookEntity.setId(bookId);
        testBookEntity
                .setGenre(testBookGenreEntity)
                .setAuthor(testAuthorEntity)
                .setPublisher(testPublisherEntity);

        when(mockCloudinaryService.uploadFile(mockFile, "books"))
                .thenReturn("https://someimage.com");
        when(mockAuthorService.getAuthorById(Long.parseLong(testAddBookDTO.getAuthor()))).thenReturn(testAuthorEntity);
        when(mockBookGenreService.getBookGenreByName(testAddBookDTO.getGenre())).thenReturn(testBookGenreEntity);
        when(mockPublisherService.getPublisherByName(testAddBookDTO.getPublisher())).thenReturn(testPublisherEntity);
        when(mockModelMapper.map(testAddBookDTO, BookEntity.class)).thenReturn(testBookEntity);
        when(mockBookRepository.save(testBookEntity)).thenReturn(testBookEntity);

        Long result = serviceToTest.addBook(testAddBookDTO);

        assertEquals(bookId, result);
    }

    private static AddBookDTO createTestAddBookDTO() {
        return new AddBookDTO()
                .setTitle("The Hitchhiker's Guide to the Galaxy")
                .setDescription("Great book!")
                .setIsbn("9781400052929")
                .setYear(2004)
                .setPages(224)
                .setGenre(BookGenreEnum.SCIENCE_FICTION)
                .setPublisher("Crown Publishing");
    }

    private static BookEntity createTestBookEntity() {
        return new BookEntity()
                .setTitle("The Hitchhiker's Guide to the Galaxy")
                .setDescription("Great book!")
                .setIsbn("9781400052929")
                .setYear(2004)
                .setPages(224)
                .setImageURL("https://someimage.com")
                .setCreated(LocalDateTime.of(2025, Month.FEBRUARY, 25, 14, 33))
                .setAvailable(true);
    }

    private static AuthorEntity createTestAuthorEntity() {
        return new AuthorEntity()
                .setFirstName("Douglas")
                .setLastName("Adams")
                .setPresentation("Great author.")
                .setImageURL("https://someimage.com");
    }

    private static BookGenreEntity createTestBookGenreEntity() {
        return new BookGenreEntity()
                .setGenre(BookGenreEnum.SCIENCE_FICTION)
                .setDescription("Science fiction.");
    }

    private static PublisherEntity createTestPublisherEntity() {
        return new PublisherEntity()
                .setName("Crown Publishing")
                .setCreated(LocalDateTime.now());
    }
}