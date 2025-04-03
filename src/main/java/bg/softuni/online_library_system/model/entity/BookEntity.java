package bg.softuni.online_library_system.model.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "books")
public class BookEntity extends BaseEntity {

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", columnDefinition = "TEXT", nullable = false)
    private String description;

    @Column(name = "isbn", nullable = false)
    private String isbn;

    @Column(name = "year")
    private int year;

    @Column(name = "pages")
    private int pages;

    @Column(name = "image_url")
    private String imageURL;

    @Column(name = "created", nullable = false)
    private LocalDateTime created;

    @Column(name = "borrow_counter")
    private int borrowCounter;

    @Column(name = "is_available")
    private boolean isAvailable;

    @ManyToOne(targetEntity = AuthorEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", referencedColumnName = "id", nullable = false)
    private AuthorEntity author;

    @ManyToOne(targetEntity = BookGenreEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "genre_id", referencedColumnName = "id", nullable = false)
    private BookGenreEntity genre;

    @ManyToOne(targetEntity = PublisherEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "publisher_id", referencedColumnName = "id", nullable = false)
    private PublisherEntity publisher;

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

    public int getYear() {
        return year;
    }

    public BookEntity setYear(int year) {
        this.year = year;
        return this;
    }

    public int getPages() {
        return pages;
    }

    public BookEntity setPages(int pages) {
        this.pages = pages;
        return this;
    }

    public String getImageURL() {
        return imageURL;
    }

    public BookEntity setImageURL(String imageURL) {
        this.imageURL = imageURL;
        return this;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public BookEntity setCreated(LocalDateTime created) {
        this.created = created;
        return this;
    }

    public int getBorrowCounter() {
        return borrowCounter;
    }

    public BookEntity setBorrowCounter(int borrowCounter) {
        this.borrowCounter = borrowCounter;
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

    public PublisherEntity getPublisher() {
        return publisher;
    }

    public BookEntity setPublisher(PublisherEntity publisher) {
        this.publisher = publisher;
        return this;
    }
}