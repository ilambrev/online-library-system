package bg.softuni.online_library_system.model.dto;

import bg.softuni.online_library_system.model.enums.GenderEnum;
import bg.softuni.online_library_system.model.validation.FieldMatch;
import bg.softuni.online_library_system.model.validation.UniqueUsername;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

import static bg.softuni.online_library_system.common.constant.ValidationMessages.*;

@FieldMatch(
        first = "password",
        second = "confirmPassword",
        message = PASSWORDS_MATCH
)
public class UserRegistrationDTO {

    private String firstName;

    private String lastName;

    @NotEmpty(message = NOT_EMPTY_USERNAME)
    @UniqueUsername(message = UNIQUE_USERNAME)
    @Size(min = 5, max = 40, message = USERNAME_LENGTH)
    private String username;

    @NotEmpty(message = NOT_EMPTY_PASSWORD)
    @Pattern(regexp = PASSWORD_PATTERN,
            message = PASSWORD_REQUIREMENTS)
    private String password;

    @NotEmpty(message = PASSWORDS_CONFIRMATION)
    private String confirmPassword;

    private String email;

    private String phoneNumber;

    private String address;

    private MultipartFile imageFile;

    private GenderEnum gender;

    public UserRegistrationDTO() {
    }

    public String getFirstName() {
        return firstName;
    }

    public UserRegistrationDTO setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserRegistrationDTO setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserRegistrationDTO setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserRegistrationDTO setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public UserRegistrationDTO setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserRegistrationDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public UserRegistrationDTO setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public UserRegistrationDTO setAddress(String address) {
        this.address = address;
        return this;
    }

    public MultipartFile getImageFile() {
        return imageFile;
    }

    public UserRegistrationDTO setImageFile(MultipartFile imageFile) {
        this.imageFile = imageFile;
        return this;
    }

    public GenderEnum getGender() {
        return gender;
    }

    public UserRegistrationDTO setGender(GenderEnum gender) {
        this.gender = gender;
        return this;
    }
}