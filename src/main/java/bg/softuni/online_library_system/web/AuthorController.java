package bg.softuni.online_library_system.web;

import bg.softuni.online_library_system.model.dto.AddAuthorDTO;
import bg.softuni.online_library_system.model.dto.AuthorDTO;
import bg.softuni.online_library_system.service.AuthorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.List;

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

    @GetMapping("/all")
    public String getAll(Model model) {
        List<AuthorDTO> authors = this.authorService.getAllAuthors();
        model.addAttribute("authors", authors);

        return "authors";
    }

    @GetMapping("/{id}/about")
    public String about(@PathVariable("id") Long id, Model model) {
        AuthorDTO author = this.authorService.getAuthor(id);

        if (author != null) {
            model.addAttribute("authorDTO", author);

            return "author-about";
        }

        return "/authors/all";
    }
}