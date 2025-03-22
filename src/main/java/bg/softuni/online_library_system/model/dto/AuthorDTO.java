package bg.softuni.online_library_system.model.dto;

import java.util.List;

public class AuthorDTO {

    private Long id;

    private String firstName;

    private String lastName;

    private String presentation;

    private String imageURL;

    private List<BookDTO> books;

    public AuthorDTO() {
    }

    public Long getId() {
        return id;
    }

    public AuthorDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public AuthorDTO setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public AuthorDTO setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getPresentation() {
        return presentation;
    }

    public AuthorDTO setPresentation(String presentation) {
        this.presentation = presentation;
        return this;
    }

    public String getImageURL() {
        return imageURL;
    }

    public AuthorDTO setImageURL(String imageURL) {
        this.imageURL = imageURL;
        return this;
    }

    public List<BookDTO> getBooks() {
        return books;
    }

    public AuthorDTO setBooks(List<BookDTO> books) {
        this.books = books;
        return this;
    }
}