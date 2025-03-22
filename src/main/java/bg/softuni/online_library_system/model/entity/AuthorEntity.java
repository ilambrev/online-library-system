package bg.softuni.online_library_system.model.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "authors")
public class AuthorEntity extends BaseEntity {
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "presentation", nullable = false, columnDefinition = "TEXT")
    private String presentation;

    @Column(name = "image_url")
    private String imageURL;

    @OneToMany(targetEntity = BookEntity.class, mappedBy = "author", fetch = FetchType.EAGER)
    @OrderBy("title ASC")
    private List<BookEntity> books;

    public AuthorEntity() {
    }

    public String getFirstName() {
        return firstName;
    }

    public AuthorEntity setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public AuthorEntity setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getPresentation() {
        return presentation;
    }

    public AuthorEntity setPresentation(String presentation) {
        this.presentation = presentation;
        return this;
    }

    public String getImageURL() {
        return imageURL;
    }

    public AuthorEntity setImageURL(String imageURL) {
        this.imageURL = imageURL;
        return this;
    }

    public List<BookEntity> getBooks() {
        return books;
    }

    public AuthorEntity setBooks(List<BookEntity> books) {
        this.books = books;
        return this;
    }
}