package bg.softuni.online_library_system.model.dto;

import bg.softuni.online_library_system.model.enums.GenderEnum;
import bg.softuni.online_library_system.model.enums.UserRoleEnum;
import bg.softuni.online_library_system.model.validation.ValidImageType;
import bg.softuni.online_library_system.model.validation.ValidPassword;
import jakarta.validation.constraints.*;
import org.springframework.web.multipart.MultipartFile;

import static bg.softuni.online_library_system.common.constant.ValidationConstants.*;

public class UserProfileDTO {

    @NotEmpty(message = FIRST_NAME_REQUIRED)
    @Size(min = 2, max = 20, message = FIRST_NAME_LENGTH)
    private String firstName;

    @NotEmpty(message = LAST_NAME_REQUIRED)
    @Size(min = 2, max = 20, message = LAST_NAME_LENGTH)
    private String lastName;

    private String username;

    @ValidPassword
    private String password;

    private String newPassword;

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

    private String imageURL;

    @NotNull(message = GENDER_NOT_NULL)
    private GenderEnum gender;

    private UserRoleEnum role;

    @ValidImageType(message = INVALID_FILE_TYPE)
    private MultipartFile imageFile;

    public UserProfileDTO() {
    }

    public String getFirstName() {
        return firstName;
    }

    public UserProfileDTO setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserProfileDTO setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserProfileDTO setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserProfileDTO setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public UserProfileDTO setNewPassword(String newPassword) {
        this.newPassword = newPassword;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserProfileDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public UserProfileDTO setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public UserProfileDTO setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getImageURL() {
        return imageURL;
    }

    public UserProfileDTO setImageURL(String imageURL) {
        this.imageURL = imageURL;
        return this;
    }

    public GenderEnum getGender() {
        return gender;
    }

    public UserProfileDTO setGender(GenderEnum gender) {
        this.gender = gender;
        return this;
    }

    public UserRoleEnum getRole() {
        return role;
    }

    public UserProfileDTO setRole(UserRoleEnum role) {
        this.role = role;
        return this;
    }

    public MultipartFile getImageFile() {
        return imageFile;
    }

    public UserProfileDTO setImageFile(MultipartFile imageFile) {
        this.imageFile = imageFile;
        return this;
    }
}