package bg.softuni.online_library_system.model.dto;

public class UserSearchDTO {
    public String username;

    public UserSearchDTO() {
    }

    public String getUsername() {
        return username;
    }

    public UserSearchDTO setUsername(String username) {
        this.username = username;
        return this;
    }
}