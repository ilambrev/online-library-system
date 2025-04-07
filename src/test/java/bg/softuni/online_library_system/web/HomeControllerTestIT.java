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
public class HomeControllerTestIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getHomeGet() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/").with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    void getHomeSafeGet() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/home").with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    void getAboutGet() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/about").with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("about"));
    }

    @Test
    void getContactGet() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/contacts").with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("contacts"));
    }
}