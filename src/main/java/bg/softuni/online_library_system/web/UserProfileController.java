package bg.softuni.online_library_system.web;

import bg.softuni.online_library_system.model.dto.UserChangePasswordDTO;
import bg.softuni.online_library_system.model.dto.UserProfileDTO;
import bg.softuni.online_library_system.model.enums.BookStatusEnum;
import bg.softuni.online_library_system.model.enums.GenderEnum;
import bg.softuni.online_library_system.model.security.CustomUserDetails;
import bg.softuni.online_library_system.service.BookStatusService;
import bg.softuni.online_library_system.service.UserService;
import jakarta.servlet.http.Cookie;
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
import static bg.softuni.online_library_system.common.constant.ValidationConstants.INVALID_FILE_SIZE;

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

    @ModelAttribute
    public void addErrorMessage(Model model) {
        model.addAttribute("errorMessage", INVALID_FILE_SIZE);
    }

    @ModelAttribute
    public void addGenders(Model model) {
        model.addAttribute("genders", GenderEnum.values());
    }

    @GetMapping("/profile")
    public String getUserProfile(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        UserProfileDTO userProfileDTO = this.userService.getUserProfileData(userDetails.getUsername());

        if (!model.containsAttribute("userProfileDTO")) {
            model.addAttribute("userProfileDTO", userProfileDTO);
        }
        if (!model.containsAttribute("isFileSizeExceeded")) {
            model.addAttribute("isFileSizeExceeded", false);
        }

        return "profile";
    }

    @PatchMapping("/profile")
    public String editUserProfile(@Valid @ModelAttribute("userProfileDTO") UserProfileDTO userProfileDTO,
                                  BindingResult bindingResult,
                                  @AuthenticationPrincipal CustomUserDetails userDetails,
                                  HttpServletRequest request, HttpServletResponse response) throws IOException {

        if (bindingResult.hasErrors()) {
            return "profile";
        }

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

        this.userService.changeUserPassword(userDetails.getUsername(), userChangePasswordDTO);

        SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
        logoutHandler.logout(request, response, SecurityContextHolder.getContext().getAuthentication());

        Cookie cookie = new Cookie("JSESSIONID", null);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return "redirect:/users/login";
    }

    @GetMapping("/reservations")
    public String getBookReservations(@AuthenticationPrincipal CustomUserDetails userDetails,
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

    @GetMapping("/borrowed-books")
    public String getBorrowedBooks(@AuthenticationPrincipal CustomUserDetails userDetails,
                                   Model model) {

        model.addAttribute("borrowedBooks",
                this.bookStatusService.getAllByUserIdAndStatus(userDetails.getUsername(), BookStatusEnum.BORROWED));

        return "user-borrowed-books";
    }
}