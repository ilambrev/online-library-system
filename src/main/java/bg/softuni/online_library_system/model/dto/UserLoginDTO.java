package bg.softuni.online_library_system.model.dto;

import jakarta.validation.constraints.NotEmpty;

import static bg.softuni.online_library_system.common.constant.ValidationMessages.EMPTY_PASSWORD;
import static bg.softuni.online_library_system.common.constant.ValidationMessages.EMPTY_USERNAME;

public class UserLoginDTO {

    @NotEmpty(message = EMPTY_USERNAME)
    private String username;

    @NotEmpty(message = EMPTY_PASSWORD)
    private String password;

    public UserLoginDTO() {
    }

    public String getUsername() {
        return username;
    }

    public UserLoginDTO setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserLoginDTO setPassword(String password) {
        this.password = password;
        return this;
    }
}