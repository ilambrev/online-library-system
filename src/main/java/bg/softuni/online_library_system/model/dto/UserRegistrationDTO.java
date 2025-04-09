package bg.softuni.online_library_system.model.dto;

import bg.softuni.online_library_system.model.enums.GenderEnum;
import bg.softuni.online_library_system.model.validation.FieldMatch;
import bg.softuni.online_library_system.model.validation.UniqueUsername;
import bg.softuni.online_library_system.model.validation.ValidImageType;
import jakarta.validation.constraints.*;
import org.springframework.web.multipart.MultipartFile;

import static bg.softuni.online_library_system.common.constant.ValidationConstants.*;

@FieldMatch(
        first = "password",
        second = "confirmPassword",
        message = PASSWORDS_MATCH
)
public class UserRegistrationDTO {

    @NotEmpty(message = FIRST_NAME_REQUIRED)
    @Size(min = 2, max = 20, message = FIRST_NAME_LENGTH)
    private String firstName;

    @NotEmpty(message = LAST_NAME_REQUIRED)
    @Size(min = 2, max = 20, message = LAST_NAME_LENGTH)
    private String lastName;

    @NotEmpty(message = NOT_EMPTY_USERNAME)
    @UniqueUsername(message = UNIQUE_USERNAME)
    @Size(min = 6, max = 40, message = USERNAME_LENGTH)
    private String username;

    @NotEmpty(message = NOT_EMPTY_PASSWORD)
    @Pattern(regexp = PASSWORD_PATTERN,
            message = PASSWORD_REQUIREMENTS)
    private String password;

    @NotEmpty(message = PASSWORDS_CONFIRMATION)
    private String confirmPassword;

    @NotNull(message = EMAIL_REQUIRED)
    @Email(message = EMAIL_FORMAT)
    @Size(min = 6, max = 40, message = EMAIL_LENGTH)
    private String email;

    @NotEmpty(message = PHONE_NUMBER_REQUIRED)
    @Pattern(regexp = PHONE_NUMBER_PATTERN, message = PHONE_NUMBER_WRONG_FORMAT)
    private String phoneNumber;

    @NotEmpty(message = ADDRESS_REQUIRED)
    @Size(min = 3, max = 60, message = ADDRESS_LENGTH)
    private String address;

    @ValidImageType(message = INVALID_FILE_TYPE)
    private MultipartFile imageFile;

    @NotNull(message = GENDER_NOT_NULL)
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