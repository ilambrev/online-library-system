package bg.softuni.online_library_system.web;

import bg.softuni.online_library_system.model.dto.UserChangePasswordDTO;
import bg.softuni.online_library_system.model.dto.UserProfileDTO;
import bg.softuni.online_library_system.model.enums.BookStatusEnum;
import bg.softuni.online_library_system.model.enums.GenderEnum;
import bg.softuni.online_library_system.model.security.CustomUserDetails;
import bg.softuni.online_library_system.service.BookStatusService;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

import static bg.softuni.online_library_system.common.constant.ValidationConstants.BINDING_RESULT_PATH;

@Controller
@RequestMapping("/user")
public class UserProfileController {
    private final UserService userService;
    private final BookStatusService bookStatusService;

    @Autowired
    public UserProfileController(UserService userService, BookStatusService bookStatusService) {
        this.userService = userService;
        this.bookStatusService = bookStatusService;
    }

    @GetMapping("/profile")
    public String getUserProfile(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        model.addAttribute("userProfileDTO", this.userService.getUserProfileData(userDetails.getUsername()));
        model.addAttribute("genders", GenderEnum.values());

        return "profile";
    }

    @PatchMapping("/profile")
    public String editUserProfile(@AuthenticationPrincipal CustomUserDetails userDetails,
                                  HttpServletRequest request, HttpServletResponse response,
                                  UserProfileDTO userProfileDTO) throws IOException {
        this.userService.editUser(userProfileDTO.setUsername(userDetails.getUsername()), request, response);

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

    @GetMapping("/reservations")
    public String searchBookReservations(@AuthenticationPrincipal CustomUserDetails userDetails,
                                         Model model) {

        model.addAttribute("reservations",
                this.bookStatusService.getAllByUserIdAndStatus(userDetails.getUsername(), BookStatusEnum.RESERVED));

        return "user-book-reservations";
    }

    @PatchMapping("/reservations/cancel")
    public String cancelBookReservation(@RequestParam Long id,
                                        @AuthenticationPrincipal CustomUserDetails userDetails,
                                        HttpServletRequest request, HttpServletResponse response) {

        this.bookStatusService.cancelReservation(id);
        this.userService.refreshAuthenticatedUser(userDetails.getUsername(), request, response);

        return "redirect:/user/reservations";
    }
}