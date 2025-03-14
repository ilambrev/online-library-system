package bg.softuni.online_library_system.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String getHome() {

        return "index";
    }

    @GetMapping("/about")
    public String showAbout() {

        return "about";
    }
}