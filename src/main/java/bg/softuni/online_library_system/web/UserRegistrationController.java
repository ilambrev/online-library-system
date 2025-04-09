package bg.softuni.online_library_system.web;

import bg.softuni.online_library_system.model.dto.UserRegistrationDTO;
import bg.softuni.online_library_system.model.enums.GenderEnum;
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

import java.io.IOException;

import static bg.softuni.online_library_system.common.constant.ValidationConstants.BINDING_RESULT_PATH;
import static bg.softuni.online_library_system.common.constant.ValidationConstants.INVALID_FILE_SIZE;

@Controller
@RequestMapping("/users")
public class UserRegistrationController {
    private final UserService userService;

    @Autowired
    public UserRegistrationController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute
    public void addErrorMessage(Model model) {
        model.addAttribute("errorMessage", INVALID_FILE_SIZE);
    }

    @GetMapping("/register")
    public String register(Model model) {
        if (!model.containsAttribute("userRegistrationDTO")) {
            model.addAttribute("userRegistrationDTO", new UserRegistrationDTO());
        }
        if (!model.containsAttribute("genders")) {
            model.addAttribute("genders", GenderEnum.values());
        }
        if (!model.containsAttribute("isFileSizeExceeded")) {
            model.addAttribute("isFileSizeExceeded", false);
        }

        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid UserRegistrationDTO userRegistrationDTO,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes) throws IOException {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userRegistrationDTO", userRegistrationDTO);
            redirectAttributes.addFlashAttribute(BINDING_RESULT_PATH.concat("userRegistrationDTO"), bindingResult);

            return "redirect:/users/register";
        }

        this.userService.registerUser(userRegistrationDTO);

        return "redirect:/users/login";
    }
}