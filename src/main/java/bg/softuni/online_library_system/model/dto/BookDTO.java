package bg.softuni.online_library_system.model.dto;

import bg.softuni.online_library_system.model.enums.BookGenreEnum;

public class BookDTO {

    private Long id;

    private String title;

    private String description;

    private String isbn;

    private int year;

    private int pages;

    private String imageURL;

    private boolean isAvailable;

    private Long authorId;

    private BookGenreEnum bookGenre;

    private String publisherName;

    public BookDTO() {
    }

    public Long getId() {
        return id;
    }

    public BookDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public BookDTO setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public BookDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getIsbn() {
        return isbn;
    }

    public BookDTO setIsbn(String isbn) {
        this.isbn = isbn;
        return this;
    }

    public int getYear() {
        return year;
    }

    public BookDTO setYear(int year) {
        this.year = year;
        return this;
    }

    public int getPages() {
        return pages;
    }

    public BookDTO setPages(int pages) {
        this.pages = pages;
        return this;
    }

    public String getImageURL() {
        return imageURL;
    }

    public BookDTO setImageURL(String imageURL) {
        this.imageURL = imageURL;
        return this;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public BookDTO setAvailable(boolean available) {
        isAvailable = available;
        return this;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public BookDTO setAuthorId(Long authorId) {
        this.authorId = authorId;
        return this;
    }

    public BookGenreEnum getBookGenre() {
        return bookGenre;
    }

    public BookDTO setBookGenre(BookGenreEnum bookGenre) {
        this.bookGenre = bookGenre;
        return this;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public BookDTO setPublisherName(String publisherName) {
        this.publisherName = publisherName;
        return this;
    }
}