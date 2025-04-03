package bg.softuni.online_library_system.web;

import bg.softuni.online_library_system.repository.MostReadBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class HomeController {
    private final MostReadBookRepository mostReadBookRepository;

    @Autowired
    public HomeController(MostReadBookRepository mostReadBookRepository) {
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
    public String showAbout() {

        return "about";
    }
}