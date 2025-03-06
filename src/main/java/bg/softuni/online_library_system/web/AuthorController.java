package bg.softuni.online_library_system.web;

import bg.softuni.online_library_system.model.dto.AddAuthorDTO;
import bg.softuni.online_library_system.service.AuthorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
@RequestMapping("/authors")
public class AuthorController {
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/add")
    public String addAuthor(Model model) {
        model.addAttribute("addAuthorDTO", new AddAuthorDTO());

        return "author-add";
    }

    @PostMapping("/add")
    public String addAuthor(AddAuthorDTO addAuthorDTO) throws IOException {
        this.authorService.addAuthor(addAuthorDTO);

        return "redirect:/";
    }
}