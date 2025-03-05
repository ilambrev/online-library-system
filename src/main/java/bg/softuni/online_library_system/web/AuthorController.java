package bg.softuni.online_library_system.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/authors")
public class AuthorController {

    @GetMapping("/add")
    public String addAuthor() {

        return "author-add";
    }
}