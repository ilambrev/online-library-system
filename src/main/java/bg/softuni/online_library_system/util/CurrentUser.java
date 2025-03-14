package bg.softuni.online_library_system.util;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component("currentUser")
@SessionScope
public class CurrentUser {
    private String username;
    private String firstName;
    private String lastName;
    private String imageURL;
    private String role;
    private boolean isLogged;
    private final HttpSession httpSession;

    @Autowired
    public CurrentUser(HttpSession httpSession) {
        this.httpSession = httpSession;
    }

    public String getUsername() {
        return username;
    }

    public CurrentUser setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public CurrentUser setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public CurrentUser setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getImageURL() {
        return imageURL;
    }

    public CurrentUser setImageURL(String imageURL) {
        this.imageURL = imageURL;
        return this;
    }

    public String getRole() {
        return role;
    }

    public CurrentUser setRole(String role) {
        this.role = role;
        return this;
    }

    public boolean isLogged() {
        return isLogged;
    }

    public CurrentUser setLogged(boolean logged) {
        isLogged = logged;
        return this;
    }

    public String getFullName() {
        StringBuilder sb = new StringBuilder();
        if (this.firstName != null) {
            sb.append(this.firstName).append(" ");
        }
        if (this.lastName != null) {
            sb.append(this.lastName);
        }
        String fullName = sb.toString().trim();

        return fullName.isEmpty() ? this.username : fullName;
    }

    public void logout() {
        this.username = null;
        this.firstName = null;
        this.lastName = null;
        this.imageURL = null;
        this.role = null;
        this.isLogged = false;
        this.httpSession.invalidate();
    }
}