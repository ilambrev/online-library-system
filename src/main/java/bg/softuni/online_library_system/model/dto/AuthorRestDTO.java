package bg.softuni.online_library_system.model.dto;

public class AuthorRestDTO {

    private Long id;

    private String firstName;

    private String lastName;

    private String presentation;

    private String imageURL;

    public AuthorRestDTO() {
    }

    public Long getId() {
        return id;
    }

    public AuthorRestDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public AuthorRestDTO setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public AuthorRestDTO setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getPresentation() {
        return presentation;
    }

    public AuthorRestDTO setPresentation(String presentation) {
        this.presentation = presentation;
        return this;
    }

    public String getImageURL() {
        return imageURL;
    }

    public AuthorRestDTO setImageURL(String imageURL) {
        this.imageURL = imageURL;
        return this;
    }
}