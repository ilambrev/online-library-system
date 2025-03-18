package bg.softuni.online_library_system.model.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "books")
public class BookEntity extends BaseEntity {

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "isbn", nullable = false)
    private String isbn;

    @Column(name = "image_url")
    private String imageURL;

    @Column(name = "entry_date", nullable = false)
    private LocalDateTime entryDate;

    @Column(name = "read_counter")
    private int readCounter;

    @Column(name = "is_available")
    private boolean isAvailable;

    @ManyToOne(targetEntity = AuthorEntity.class)
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private AuthorEntity author;

    @ManyToOne(targetEntity = BookGenreEntity.class)
    @JoinColumn(name = "genre_id", referencedColumnName = "id")
    private BookGenreEntity genre;

    public BookEntity() {
    }

    public String getTitle() {
        return title;
    }

    public BookEntity setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public BookEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getIsbn() {
        return isbn;
    }

    public BookEntity setIsbn(String isbn) {
        this.isbn = isbn;
        return this;
    }

    public String getImageURL() {
        return imageURL;
    }

    public BookEntity setImageURL(String imageURL) {
        this.imageURL = imageURL;
        return this;
    }

    public LocalDateTime getEntryDate() {
        return entryDate;
    }

    public BookEntity setEntryDate(LocalDateTime entryDate) {
        this.entryDate = entryDate;
        return this;
    }

    public int getReadCounter() {
        return readCounter;
    }

    public BookEntity setReadCounter(int readCounter) {
        this.readCounter = readCounter;
        return this;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public BookEntity setAvailable(boolean available) {
        isAvailable = available;
        return this;
    }

    public AuthorEntity getAuthor() {
        return author;
    }

    public BookEntity setAuthor(AuthorEntity author) {
        this.author = author;
        return this;
    }

    public BookGenreEntity getGenre() {
        return genre;
    }

    public BookEntity setGenre(BookGenreEntity genre) {
        this.genre = genre;
        return this;
    }
}