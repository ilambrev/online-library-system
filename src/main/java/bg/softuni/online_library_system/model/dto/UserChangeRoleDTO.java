package bg.softuni.online_library_system.model.dto;

import bg.softuni.online_library_system.model.enums.GenderEnum;
import bg.softuni.online_library_system.model.enums.UserRoleEnum;

public class UserChangeRoleDTO {

    private Long id;

    private String firstName;

    private String lastName;

    private String username;

    private String email;

    private String phoneNumber;

    private String address;

    private String imageURL;

    private GenderEnum gender;

    private UserRoleEnum role;

    public UserChangeRoleDTO() {
    }

    public Long getId() {
        return id;
    }

    public UserChangeRoleDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserChangeRoleDTO setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserChangeRoleDTO setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserChangeRoleDTO setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserChangeRoleDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public UserChangeRoleDTO setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public UserChangeRoleDTO setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getImageURL() {
        return imageURL;
    }

    public UserChangeRoleDTO setImageURL(String imageURL) {
        this.imageURL = imageURL;
        return this;
    }

    public GenderEnum getGender() {
        return gender;
    }

    public UserChangeRoleDTO setGender(GenderEnum gender) {
        this.gender = gender;
        return this;
    }

    public UserRoleEnum getRole() {
        return role;
    }

    public UserChangeRoleDTO setRole(UserRoleEnum role) {
        this.role = role;
        return this;
    }
}