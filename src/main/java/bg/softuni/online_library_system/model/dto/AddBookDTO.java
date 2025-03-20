package bg.softuni.online_library_system.model.dto;

import bg.softuni.online_library_system.model.enums.BookGenreEnum;
import org.springframework.web.multipart.MultipartFile;

public class AddBookDTO {

    private String title;

    private String description;

    private String isbn;

    private int year;

    private int pages;

    private MultipartFile imageFile;

    private String author;

    private BookGenreEnum genre;

    private String publisher;

    public AddBookDTO() {
    }

    public String getTitle() {
        return title;
    }

    public AddBookDTO setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public AddBookDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getIsbn() {
        return isbn;
    }

    public AddBookDTO setIsbn(String isbn) {
        this.isbn = isbn;
        return this;
    }

    public int getYear() {
        return year;
    }

    public AddBookDTO setYear(int year) {
        this.year = year;
        return this;
    }

    public int getPages() {
        return pages;
    }

    public AddBookDTO setPages(int pages) {
        this.pages = pages;
        return this;
    }

    public MultipartFile getImageFile() {
        return imageFile;
    }

    public AddBookDTO setImageFile(MultipartFile imageFile) {
        this.imageFile = imageFile;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public AddBookDTO setAuthor(String author) {
        this.author = author;
        return this;
    }

    public BookGenreEnum getGenre() {
        return genre;
    }

    public AddBookDTO setGenre(BookGenreEnum genre) {
        this.genre = genre;
        return this;
    }

    public String getPublisher() {
        return publisher;
    }

    public AddBookDTO setPublisher(String publisher) {
        this.publisher = publisher;
        return this;
    }
}