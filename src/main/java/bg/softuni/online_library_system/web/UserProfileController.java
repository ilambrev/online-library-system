package bg.softuni.online_library_system.web;

import bg.softuni.online_library_system.model.dto.UserChangePasswordDTO;
import bg.softuni.online_library_system.model.dto.UserProfileDTO;
import bg.softuni.online_library_system.model.enums.GenderEnum;
import bg.softuni.online_library_system.model.security.CustomUserDetails;
import bg.softuni.online_library_system.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public String getUserProfile(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        model.addAttribute("userProfileDTO", this.userService.getUserProfileData(userDetails.getUsername()));
        model.addAttribute("genders", GenderEnum.values());

        return "profile";
    }

    @PatchMapping("/profile")
    public String editUserProfile(UserProfileDTO userProfileDTO) throws IOException {
        this.userService.editUser(userProfileDTO);

        return "redirect:/user/profile";
    }

    @GetMapping("/change-password")
    public String changePassword(Model model) {
        if (!model.containsAttribute("userChangePasswordDTO")) {
            model.addAttribute("userChangePasswordDTO", new UserChangePasswordDTO());
        }

        return "change-password";
    }

    @PostMapping("/change-password")
    public String changePassword(@Valid UserChangePasswordDTO userChangePasswordDTO,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes,
                                 @AuthenticationPrincipal CustomUserDetails userDetails,
                                 HttpServletRequest request,
                                 HttpServletResponse response) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userChangePasswordDTO", userChangePasswordDTO);
            redirectAttributes.addFlashAttribute(BINDING_RESULT_PATH.concat("userChangePasswordDTO"), bindingResult);

            return "redirect:/user/change-password";
        }

        if (!this.userService.changeUserPassword(userDetails.getUsername(), userChangePasswordDTO)) {
            return "redirect:/user/change-password";
        }

        new SecurityContextLogoutHandler()
                .logout(request, response, SecurityContextHolder.getContext().getAuthentication());

        return "redirect:/users/login";
    }
}