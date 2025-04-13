package bg.softuni.online_library_system.service.impl;

import bg.softuni.online_library_system.exception.ObjectNotFoundException;
import bg.softuni.online_library_system.model.dto.AddAuthorDTO;
import bg.softuni.online_library_system.model.dto.AuthorDTO;
import bg.softuni.online_library_system.model.entity.AuthorEntity;
import bg.softuni.online_library_system.repository.AuthorRepository;
import bg.softuni.online_library_system.repository.BookRepository;
import bg.softuni.online_library_system.service.CloudinaryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthorServiceImplTest {

    private AuthorServiceImpl serviceToTest;

    @Mock
    private AuthorRepository mockAuthorRepository;

    @Mock
    private BookRepository mockBookRepository;

    @Mock
    private CloudinaryService mockCloudinaryService;

    @Mock
    private ModelMapper mockModelMapper;

    @BeforeEach
    void setUp() {
        serviceToTest = new AuthorServiceImpl(mockAuthorRepository, mockBookRepository,
                mockCloudinaryService, mockModelMapper);
    }

    @Test
    void testAddAuthor() throws IOException {
        Long authorId = 1L;
        MultipartFile mockFile = mock(MultipartFile.class);
        AddAuthorDTO testAddAuthorDTO = createTestAddAuthorDTO(mockFile);
        AuthorEntity testAuthorEntity = createTestAuthor();
        testAuthorEntity.setId(authorId);

        when(mockCloudinaryService.uploadFile(mockFile, "authors"))
                .thenReturn("https://someimage.com");

        when(mockModelMapper.map(testAddAuthorDTO, AuthorEntity.class))
                .thenReturn(testAuthorEntity);

        when(mockAuthorRepository.save(any(AuthorEntity.class)))
                .thenReturn(testAuthorEntity);

        assertEquals(1L, serviceToTest.addAuthor(testAddAuthorDTO));
        verify(mockCloudinaryService).uploadFile(mockFile, "authors");
        verify(mockModelMapper).map(testAddAuthorDTO, AuthorEntity.class);
        verify(mockAuthorRepository).save(any(AuthorEntity.class));
    }

    @Test
    void testAddAuthorWhenCloudinaryUploadFailsThrowsIOException() throws IOException {
        MultipartFile mockFile = mock(MultipartFile.class);
        AddAuthorDTO testAddAuthorDTO = createTestAddAuthorDTO(mockFile);
        AuthorEntity testAuthorEntity = createTestAuthor();

        when(mockCloudinaryService.uploadFile(mockFile, "authors"))
                .thenThrow(new IOException());

        when(mockModelMapper.map(testAddAuthorDTO, AuthorEntity.class))
                .thenReturn(testAuthorEntity);

        assertThrows(IOException.class, () -> serviceToTest.addAuthor(testAddAuthorDTO));
        verify(mockAuthorRepository, never()).save(any());
    }

    @Test
    void getAuthorByIdWithExistingId() {
        Long authorId = 1L;
        AuthorEntity testAuthorEntity = createTestAuthor();
        testAuthorEntity.setId(authorId);

        when(mockAuthorRepository.findById(authorId)).thenReturn(Optional.of(testAuthorEntity));

        AuthorEntity result = serviceToTest.getAuthorById(authorId);

        assertEquals(1L, result.getId(),
                "Author id is not populated properly.");
        assertEquals("Douglas", result.getFirstName(),
                "Author firstName is not populated properly.");
        assertEquals("Adams", result.getLastName(),
                "Author lastName is not populated properly.");
        assertEquals("Great author.", result.getPresentation(),
                "Author presentation is not populated properly.");
        assertEquals("https://someimage.com", result.getImageURL(),
                "Author imageURL is not populated properly.");
        assertEquals(0, result.getBooks().size(),
                "Author books is not populated properly.");
    }

    @Test
    void getAuthorByIdWithNotExistingId() {
        Long authorId = 2L;

        when(mockAuthorRepository.findById(authorId))
                .thenReturn(Optional.empty());

        assertNull(serviceToTest.getAuthorById(authorId));
    }

    @Test
    void getAuthorWithExistingId() {
        Long authorId = 1L;
        AuthorEntity testAuthorEntity = createTestAuthor();
        testAuthorEntity.setId(authorId);
        AuthorDTO testAuthorDTO = createTestAuthorDTO();
        testAuthorDTO.setId(authorId);

        when(mockAuthorRepository.findById(authorId)).thenReturn(Optional.of(testAuthorEntity));
        when(mockModelMapper.map(testAuthorEntity, AuthorDTO.class)).thenReturn(testAuthorDTO);

        AuthorDTO result = serviceToTest.getAuthor(authorId);

        assertNotNull(result);
        assertEquals(1L, result.getId(),
                "Author id is not populated properly.");
        assertEquals("Douglas", result.getFirstName(),
                "Author firstName is not populated properly.");
        assertEquals("Adams", result.getLastName(),
                "Author lastName is not populated properly.");
        assertEquals("Great author.", result.getPresentation(),
                "Author presentation is not populated properly.");
        assertEquals("https://someimage.com", result.getImageURL(),
                "Author imageURL is not populated properly.");
        assertEquals(0, result.getBooks().size(),
                "Author books is not populated properly.");
    }

    @Test
    void getAuthorWithNonExistingId() {
        Long authorId = 2L;

        when(mockAuthorRepository.findById(authorId)).thenReturn(Optional.empty());

        assertThrows(ObjectNotFoundException.class, () -> serviceToTest.getAuthor(authorId));
    }

    private static AuthorEntity createTestAuthor() {
        return new AuthorEntity()
                .setFirstName("Douglas")
                .setLastName("Adams")
                .setPresentation("Great author.")
                .setImageURL("https://someimage.com");
    }

    private static AddAuthorDTO createTestAddAuthorDTO(MultipartFile mockFile) {
        return new AddAuthorDTO()
                .setFirstName("Douglas")
                .setLastName("Adams")
                .setPresentation("Great author.")
                .setImageFile(mockFile);
    }

    private static AuthorDTO createTestAuthorDTO() {
        return new AuthorDTO()
                .setFirstName("Douglas")
                .setLastName("Adams")
                .setPresentation("Great author.")
                .setImageURL("https://someimage.com")
                .setBooks(new ArrayList<>());
    }
}