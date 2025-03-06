package bg.softuni.online_library_system.model.dto;

import org.springframework.web.multipart.MultipartFile;

public class AddAuthorDTO {

    private String firstName;

    private String lastName;

    private String presentation;

    private MultipartFile imageFile;

    public AddAuthorDTO() {
    }

    public String getFirstName() {
        return firstName;
    }

    public AddAuthorDTO setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public AddAuthorDTO setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getPresentation() {
        return presentation;
    }

    public AddAuthorDTO setPresentation(String presentation) {
        this.presentation = presentation;
        return this;
    }

    public MultipartFile getImageFile() {
        return imageFile;
    }

    public AddAuthorDTO setImageFile(MultipartFile imageFile) {
        this.imageFile = imageFile;
        return this;
    }
}