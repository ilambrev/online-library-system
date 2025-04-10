package bg.softuni.online_library_system.model.dto;

import bg.softuni.online_library_system.model.validation.ValidImageType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

import static bg.softuni.online_library_system.common.constant.ValidationConstants.*;

public class AddAuthorDTO {

    @NotEmpty(message = FIRST_NAME_REQUIRED)
    @Size(min = 2, max = 20, message = FIRST_NAME_LENGTH)
    private String firstName;

    @NotEmpty(message = LAST_NAME_REQUIRED)
    @Size(min = 2, max = 20, message = LAST_NAME_LENGTH)
    private String lastName;

    @NotEmpty(message = PRESENTATION_REQUIRED)
    @Size(min = 5, max = 1024, message = PRESENTATION_SIZE)
    private String presentation;

    @ValidImageType(message = INVALID_FILE_TYPE)
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