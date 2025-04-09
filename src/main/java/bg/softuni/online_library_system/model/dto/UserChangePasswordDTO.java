package bg.softuni.online_library_system.model.dto;

import bg.softuni.online_library_system.model.validation.FieldMatch;
import bg.softuni.online_library_system.model.validation.ValidPassword;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

import static bg.softuni.online_library_system.common.constant.ValidationConstants.*;

@FieldMatch(
        first = "newPassword",
        second = "confirmNewPassword",
        message = NEW_PASSWORDS_MATCH
)
public class UserChangePasswordDTO {

    @ValidPassword
    private String password;

    @NotEmpty(message = NOT_EMPTY_NEW_PASSWORD)
    @Pattern(regexp = PASSWORD_PATTERN,
            message = NEW_PASSWORD_REQUIREMENTS)
    private String newPassword;

    @NotEmpty(message = NEW_PASSWORD_CONFIRMATION)
    private String confirmNewPassword;

    public UserChangePasswordDTO() {
    }

    public String getPassword() {
        return password;
    }

    public UserChangePasswordDTO setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public UserChangePasswordDTO setNewPassword(String newPassword) {
        this.newPassword = newPassword;
        return this;
    }

    public String getConfirmNewPassword() {
        return confirmNewPassword;
    }

    public UserChangePasswordDTO setConfirmNewPassword(String confirmNewPassword) {
        this.confirmNewPassword = confirmNewPassword;
        return this;
    }
}