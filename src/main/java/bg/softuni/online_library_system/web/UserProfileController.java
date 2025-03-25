package bg.softuni.online_library_system.web;

import bg.softuni.online_library_system.model.dto.UserChangePasswordDTO;
import bg.softuni.online_library_system.model.dto.UserProfileDTO;
import bg.softuni.online_library_system.model.enums.GenderEnum;
import bg.softuni.online_library_system.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

import static bg.softuni.online_library_system.common.constant.ValidationConstants.BINDING_RESULT_PATH;

@Controller
@RequestMapping("/user")
public class UserProfileController {
    private final UserService userService;

    @Autowired
    public UserProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public String getUserProfile(@RequestParam String username, Model model) {
        model.addAttribute("userProfileDTO", this.userService.getUserProfileData(username));
        model.addAttribute("genders", GenderEnum.values());

        return "profile";
    }

    @PatchMapping("/profile")
    public String editUserProfile(@RequestParam String username, UserProfileDTO userProfileDTO) throws IOException {
        this.userService.editUser(userProfileDTO);

        return String.format("redirect:/user/profile?username=%s", username);
    }

    @GetMapping("/change-password")
    public String changePassword(@RequestParam String username, Model model) {
        if (!model.containsAttribute("userChangePasswordDTO")) {
            model.addAttribute("userChangePasswordDTO", new UserChangePasswordDTO());
        }

        return "change-password";
    }

    @PostMapping("/change-password")
    public String changePassword(@RequestParam String username, @Valid UserChangePasswordDTO userChangePasswordDTO,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userChangePasswordDTO", userChangePasswordDTO);
            redirectAttributes.addFlashAttribute(BINDING_RESULT_PATH.concat("userChangePasswordDTO"), bindingResult);

            return String.format("redirect:/user/change-password?username=%s", username);
        }

        if (!this.userService.changeUserPassword(username, userChangePasswordDTO)) {
            return String.format("redirect:/user/change-password?username=%s", username);
        }

        return "redirect:/users/logout";
    }
}