package bg.softuni.online_library_system.web;

import bg.softuni.online_library_system.model.dto.UserLoginDTO;
import bg.softuni.online_library_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserLoginController {
    private final UserService userService;

    @Autowired
    public UserLoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("userLoginDTO", new UserLoginDTO());

        return "login";
    }

    @PostMapping("/login")
    public String login(UserLoginDTO userLoginDTO) {
        System.out.println();
        if (!this.userService.loginUser(userLoginDTO)) {
            return "redirect:/users/login";
        }

        return "redirect:/";
    }
}