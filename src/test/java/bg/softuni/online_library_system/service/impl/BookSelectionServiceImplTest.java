package bg.softuni.online_library_system.service.impl;

import bg.softuni.online_library_system.exception.ObjectNotFoundException;
import bg.softuni.online_library_system.repository.BookRepository;
import bg.softuni.online_library_system.repository.BookStatusRepository;
import bg.softuni.online_library_system.service.BookSelectionService;
import bg.softuni.online_library_system.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookSelectionServiceImplTest {

    private BookSelectionService serviceToTest;

    @Mock
    UserService mockUserService;

    @Mock
    BookRepository mockBookRepository;

    @Mock
    BookStatusRepository mockBookStatusRepository;

    @Mock
    ModelMapper mockModelMapper;

    @BeforeEach
    void setUp() {
        serviceToTest = new BookSelectionServiceImpl(mockUserService, mockBookRepository,
                mockBookStatusRepository, mockModelMapper);
    }

    @Test
    void testChangeBookStatusWithNonExistingBookId() {
        Long id = 0L;

        when(mockBookRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ObjectNotFoundException.class,
                () -> serviceToTest.changeBookStatus(id, true));
    }
}