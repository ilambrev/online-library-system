package bg.softuni.online_library_system.web;

import bg.softuni.online_library_system.model.enums.GenderEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserRegistrationControllerTestIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void userRegisterGet() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/register").with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("register"));
    }

    @Test
    void userRegisterPost() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/users/register")
                        .param("firstName", "Dave")
                        .param("lastName", "Lombardo")
                        .param("username", "lombardo")
                        .param("password", "123456")
                        .param("confirmPassword", "123456")
                        .param("email", "lombardo@test.com")
                        .param("phoneNumber", "+359111222333")
                        .param("address", "Sofia, 25 Somewhere Str.")
                        .param("imageFile", "")
                        .param("gender", GenderEnum.MALE.name())
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users/register"));
    }
}