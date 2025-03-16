package bg.softuni.online_library_system.web;

import bg.softuni.online_library_system.model.dto.UserLoginDTO;
import bg.softuni.online_library_system.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static bg.softuni.online_library_system.common.constant.ValidationConstants.BINDING_RESULT_PATH;
import static bg.softuni.online_library_system.common.constant.ValidationMessages.BAD_CREDENTIALS;

@Controller
@RequestMapping("/users")
public class UserLoginController {
    private final UserService userService;

    @Autowired
    public UserLoginController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute
    public void addErrorMessage(Model model) {
        model.addAttribute("errorMessage", BAD_CREDENTIALS);
    }

    @GetMapping("/login")
    public String login(Model model) {
        if (!model.containsAttribute("userLoginDTO")) {
            model.addAttribute("userLoginDTO", new UserLoginDTO());
        }
        if (!model.containsAttribute("badCredentials")) {
            model.addAttribute("badCredentials", false);
        }

        return "login";
    }

    @PostMapping("/login")
    public String login(@Valid UserLoginDTO userLoginDTO,
                        BindingResult bindingResult,
                        RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userLoginDTO", userLoginDTO);
            redirectAttributes.addFlashAttribute(BINDING_RESULT_PATH.concat("userLoginDTO"), bindingResult);

            return "redirect:/users/login";
        }

        if (!this.userService.loginUser(userLoginDTO)) {
            redirectAttributes.addFlashAttribute("userLoginDTO", userLoginDTO);
            redirectAttributes.addFlashAttribute("badCredentials", true);

            return "redirect:/users/login";
        }

        return "redirect:/";
    }

    @PostMapping("/logout")
    public String logout() {
        this.userService.logoutUser();

        return "redirect:/";
    }
}