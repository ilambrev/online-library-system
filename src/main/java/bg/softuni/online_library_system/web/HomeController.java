package bg.softuni.online_library_system.web;

import bg.softuni.online_library_system.repository.AuthorRepository;
import bg.softuni.online_library_system.repository.BookRepository;
import bg.softuni.online_library_system.repository.MostReadBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class HomeController {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final MostReadBookRepository mostReadBookRepository;

    @Autowired
    public HomeController(AuthorRepository authorRepository, BookRepository bookRepository, MostReadBookRepository mostReadBookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.mostReadBookRepository = mostReadBookRepository;
    }

    @ModelAttribute
    public void getMostReadBooks(Model model) {
        model.addAttribute("mostReadBooks", this.mostReadBookRepository.findAll());
    }

    @GetMapping("/")
    public String getHome() {

        return "index";
    }

    @GetMapping("/home")
    public String getHomeSafe() {

        return "index";
    }

    @GetMapping("/about")
    public String getAbout(Model model) {
        model.addAttribute("authorsCount", this.authorRepository.count());
        model.addAttribute("booksCount", this.bookRepository.count());

        return "about";
    }

    @GetMapping("/contacts")
    public String getContacts() {

        return "contacts";
    }
}