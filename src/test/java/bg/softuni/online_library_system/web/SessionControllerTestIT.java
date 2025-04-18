package bg.softuni.online_library_system.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
public class SessionControllerTestIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void addBookGet() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/session-expired").with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("session-expired"));
    }
}