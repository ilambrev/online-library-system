package bg.softuni.online_library_system.web;

import bg.softuni.online_library_system.model.dto.AddAuthorDTO;
import bg.softuni.online_library_system.model.dto.AuthorDTO;
import bg.softuni.online_library_system.service.AuthorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

import static bg.softuni.online_library_system.common.constant.ValidationConstants.BINDING_RESULT_PATH;
import static bg.softuni.online_library_system.common.constant.ValidationConstants.INVALID_FILE_SIZE;

@Controller
@RequestMapping("/authors")
public class AuthorController {
    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @ModelAttribute
    public void addErrorMessage(Model model) {
        model.addAttribute("errorMessage", INVALID_FILE_SIZE);
    }

    @GetMapping("/add")
    public String addAuthor(Model model) {
        if (!model.containsAttribute("addAuthorDTO")) {
            model.addAttribute("addAuthorDTO", new AddAuthorDTO());
        }
        if (!model.containsAttribute("isFileSizeExceeded")) {
            model.addAttribute("isFileSizeExceeded", false);
        }

        return "author-add";
    }

    @PostMapping("/add")
    public String addAuthor(@Valid AddAuthorDTO addAuthorDTO,
                            BindingResult bindingResult,
                            RedirectAttributes redirectAttributes) throws IOException {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("addAuthorDTO", addAuthorDTO);
            redirectAttributes.addFlashAttribute(BINDING_RESULT_PATH.concat("addAuthorDTO"), bindingResult);

            return "redirect:/authors/add";
        }

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