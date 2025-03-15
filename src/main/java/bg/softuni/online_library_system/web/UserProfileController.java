package bg.softuni.online_library_system.web;

import bg.softuni.online_library_system.model.enums.GenderEnum;
import bg.softuni.online_library_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
        model.addAttribute("userProfileDTO", this.userService.getUserByUsername(username));
        model.addAttribute("genders", GenderEnum.values());

        return "profile";
    }
}