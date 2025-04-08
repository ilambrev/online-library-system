package bg.softuni.online_library_system.web;

import bg.softuni.online_library_system.model.entity.BookEntity;
import bg.softuni.online_library_system.model.entity.BookStatusEntity;
import bg.softuni.online_library_system.model.entity.UserEntity;
import bg.softuni.online_library_system.model.enums.BookStatusEnum;
import bg.softuni.online_library_system.model.security.CustomUserDetails;
import bg.softuni.online_library_system.repository.*;
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

import java.time.LocalDateTime;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
public class UserProfileControllerTestIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private PublisherRepository publisherRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookStatusRepository bookStatusRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestUtil testUtil;

    @BeforeEach
    void setupSecurityContext() {
        CustomUserDetails userDetails = CustomUserDetailsTestHelper.createMockUser();
        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

        bookStatusRepository.deleteAll();
        bookRepository.deleteAll();
        authorRepository.deleteAll();
        publisherRepository.deleteAll();
        userRepository.deleteAll();
    }

    @AfterEach
    void cleanUp() {
        SecurityContextHolder.clearContext();

        bookStatusRepository.deleteAll();
        bookRepository.deleteAll();
        authorRepository.deleteAll();
        publisherRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void changePasswordGet() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/change-password").with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("change-password"));
    }

    @Test
    void getBookReservationsGet() throws Exception {
        UserEntity testUserEntity = this.testUtil.createUser();
        BookEntity testBookEntity = this.testUtil.createBook();
        testBookEntity.setAvailable(false);
        testBookEntity = this.bookRepository.save(testBookEntity);
        BookStatusEntity testBookStatusEntity = new BookStatusEntity()
                .setBook(testBookEntity)
                .setUser(testUserEntity)
                .setReservationDate(LocalDateTime.now().minusHours(5))
                .setStatus(BookStatusEnum.RESERVED);
        this.bookStatusRepository.save(testBookStatusEntity);

        mockMvc.perform(MockMvcRequestBuilders.get("/user/reservations").with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("user-book-reservations"));
    }

    @Test
    void getBorrowedBooksGet() throws Exception {
        UserEntity testUserEntity = this.testUtil.createUser();
        BookEntity testBookEntity = this.testUtil.createBook();
        testBookEntity.setAvailable(false);
        testBookEntity = this.bookRepository.save(testBookEntity);
        BookStatusEntity testBookStatusEntity = new BookStatusEntity()
                .setBook(testBookEntity)
                .setUser(testUserEntity)
                .setReservationDate(LocalDateTime.now().minusHours(5))
                .setBorrowDate(LocalDateTime.now())
                .setStatus(BookStatusEnum.BORROWED);
        this.bookStatusRepository.save(testBookStatusEntity);

        mockMvc.perform(MockMvcRequestBuilders.get("/user/borrowed-books").with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("user-borrowed-books"));
    }
}