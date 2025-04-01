package bg.softuni.online_library_system.web;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String getHome(HttpSession session) {

        return "index";
    }

    @GetMapping("/home")
    public String getHomeSafe() {

        return "index";
    }

    @GetMapping("/about")
    public String showAbout() {

        return "about";
    }
}