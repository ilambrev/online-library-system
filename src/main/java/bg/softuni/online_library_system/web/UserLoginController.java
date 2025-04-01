package bg.softuni.online_library_system.web;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static bg.softuni.online_library_system.common.constant.ValidationConstants.BAD_CREDENTIALS;

@Controller
@RequestMapping("/users")
public class UserLoginController {

    @ModelAttribute
    public void addErrorMessage(Model model) {
        model.addAttribute("errorMessage", BAD_CREDENTIALS);
    }

    @GetMapping("/login")
    public String login(Model model, HttpSession session) {
        if (!model.containsAttribute("username")) {
            model.addAttribute("username", null);
        }
        if (!model.containsAttribute("badCredentials")) {
            model.addAttribute("badCredentials", false);
        }

        return "login";
    }

    @PostMapping("/login-error")
    public String onError(@ModelAttribute("username") String username, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("username", username);
        redirectAttributes.addFlashAttribute("badCredentials", true);

        return "redirect:/users/login";
    }
}