package bg.softuni.online_library_system.web;

import bg.softuni.online_library_system.model.security.CustomUserDetails;
import bg.softuni.online_library_system.repository.UserRepository;
import bg.softuni.online_library_system.repository.UserRoleRepository;
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
public class AdminControllerTestIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private TestUtil testUtil;

    @BeforeEach
    void setUp() {
        CustomUserDetails userDetails = CustomUserDetailsTestHelper.createMockUser();
        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

        userRepository.deleteAll();
        userRoleRepository.deleteAll();
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();

        userRepository.deleteAll();
        userRoleRepository.deleteAll();
    }

    @Test
    void getAllUsersGet() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/users").with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("users"));
    }

    @Test
    void changeUserRoleGet() throws Exception {
        Long id = this.testUtil.createUser().getId();

        String uriTemplate = String.format("/admin/users/%d/change-role", id);

        mockMvc.perform(MockMvcRequestBuilders.get(uriTemplate).with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("profile-change-role"));
    }
}