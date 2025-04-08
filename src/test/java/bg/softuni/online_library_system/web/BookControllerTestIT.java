package bg.softuni.online_library_system.web;

import bg.softuni.online_library_system.model.security.CustomUserDetails;
import bg.softuni.online_library_system.repository.AuthorRepository;
import bg.softuni.online_library_system.repository.BookRepository;
import bg.softuni.online_library_system.repository.PublisherRepository;
import bg.softuni.online_library_system.testutils.CustomUserDetailsTestHelper;
import bg.softuni.online_library_system.testutils.TestUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerTestIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private PublisherRepository publisherRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private TestUtil testUtil;

    @BeforeEach
    void setUp() {
        CustomUserDetails userDetails = CustomUserDetailsTestHelper.createMockUser();
        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

        bookRepository.deleteAll();
        publisherRepository.deleteAll();
        authorRepository.deleteAll();
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
        bookRepository.deleteAll();
        publisherRepository.deleteAll();
        authorRepository.deleteAll();
    }

    @Test
    void addBookGet() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/books/add").with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("book-add"));
    }

    @Test
    void getAllGet() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/books/all").with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("books"));
    }

    @Test
    void aboutBookGet() throws Exception {
        Long id = testUtil.createBook().getId();

        String uriTemplate = String.format("/books/%d/about", id);

        mockMvc.perform(MockMvcRequestBuilders.get(uriTemplate).with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("book-about"));
    }

    @Test
    void searchBookReservationsGet() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/books/reservations")
                        .queryParam("username", "lombardo")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("book-reservations"));
    }

    @Test
    void searchBorrowedBooksGet() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/books/borrow")
                        .queryParam("username", "lombardo")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("book-return"));
    }

    @Test
    void overdueWarningGet() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/books/overdue-warning").with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("overdue-warning"));
    }
}