package bg.softuni.online_library_system.web;

import bg.softuni.online_library_system.model.entity.BookEntity;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BookSelectionControllerTestIT {

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
    void setupSecurityContext() {
        CustomUserDetails userDetails = CustomUserDetailsTestHelper.createMockUser();
        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

        bookRepository.deleteAll();
        authorRepository.deleteAll();
        publisherRepository.deleteAll();
    }

    @AfterEach
    void cleanUp() {
        SecurityContextHolder.clearContext();

        bookRepository.deleteAll();
        authorRepository.deleteAll();
        publisherRepository.deleteAll();
    }

    @Test
    void addBookToCartPost() throws Exception {
        BookEntity testBookEntity = this.testUtil.createBook();

        String expectedUri = String.format("/books/%d/about", testBookEntity.getId());
        mockMvc.perform(MockMvcRequestBuilders.post("/cart/add")
                        .queryParam("id", testBookEntity.getId().toString())
                        .sessionAttr("cartCount", 0)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(expectedUri));
    }

    @Test
    void getCartContentGet() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/cart/content").with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("cart"));
    }

    @Test
    void deleteBookFromCartGet() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/cart/remove")
                        .queryParam("id", "1")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/cart/content"));
    }
}