package bg.softuni.online_library_system.model.validation;

import bg.softuni.online_library_system.model.entity.UserEntity;
import bg.softuni.online_library_system.service.UserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class ValidPasswordValidator implements ConstraintValidator<ValidPassword, String> {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ValidPasswordValidator(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserEntity user = this.userService.getUserByUsername(authentication.getName());

        return this.passwordEncoder.matches(value, user.getPassword());
    }
}