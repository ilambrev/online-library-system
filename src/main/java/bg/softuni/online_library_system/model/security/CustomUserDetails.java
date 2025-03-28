package bg.softuni.online_library_system.model.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class CustomUserDetails implements UserDetails {
    private final String username;
    private final String password;
    private final String firstName;
    private final String lastName;
    private final String imageURL;
    private final int borrowedBooks;
    private final Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(String username, String password, String firstName, String lastName,
                             String imageURL, int borrowedBooks, Collection<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.imageURL = imageURL;
        this.borrowedBooks = borrowedBooks;
        this.authorities = authorities;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getImageURL() {
        return imageURL;
    }

    public int getBorrowedBooks() {
        return borrowedBooks;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
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
}