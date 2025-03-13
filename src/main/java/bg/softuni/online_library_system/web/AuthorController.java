package bg.softuni.online_library_system.web;

import bg.softuni.online_library_system.model.dto.AddAuthorDTO;
import bg.softuni.online_library_system.model.dto.AuthorDTO;
import bg.softuni.online_library_system.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
@RequestMapping("/authors")
public class AuthorController {
    private final AuthorService authorService;

    @Autowired
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
        Long id = this.authorService.addAuthor(addAuthorDTO);

        return String.format("redirect:/authors/%d/about", id);
    }

    @GetMapping("/all")
    public String getAllAuthors(@RequestParam(defaultValue = "0") int page,
                         @RequestParam(defaultValue = "6") int size,
                         Model model) {

        Page<AuthorDTO> authorsPage = this.authorService.getAllAuthorsOrderByFirstName(page, size);

        model.addAttribute("authorsCount", authorsPage.getTotalElements());
        model.addAttribute("authors", authorsPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", authorsPage.getTotalPages());

        return "authors";
    }

    @GetMapping("/{id}/about")
    public String aboutAuthor(@PathVariable("id") Long id, Model model) {
        AuthorDTO author = this.authorService.getAuthor(id);

        if (author != null) {
            model.addAttribute("authorDTO", author);

            return "author-about";
        }

        return "redirect:/authors/all";
    }
}