package bg.softuni.online_library_system.model.dto;

import bg.softuni.online_library_system.model.enums.BookGenreEnum;
import bg.softuni.online_library_system.model.validation.ExistingAuthor;
import bg.softuni.online_library_system.model.validation.ValidImageType;
import bg.softuni.online_library_system.model.validation.YearNotInTheFuture;
import jakarta.validation.constraints.*;
import org.springframework.web.multipart.MultipartFile;

import static bg.softuni.online_library_system.common.constant.ValidationConstants.*;

public class AddBookDTO {

    @NotEmpty(message = TITLE_REQUIRED)
    @Size(min = 2, max = 100, message = TITLE_SIZE)
    private String title;

    @NotEmpty(message = DESCRIPTION_REQUIRED)
    @Size(min = 5, max = 1024, message = DESCRIPTION_SIZE)
    private String description;

    @NotNull(message = ISBN_NOT_NULL)
    @Pattern(regexp = ISB_PATTERN, message = ISBN_VALID_TYPE)
    private String isbn;

    @NotNull(message = YEAR_NOT_NULL)
    @YearNotInTheFuture(message = YEAR_IN_THE_FUTURE)
    @Min(value = 1000, message = YEAR_VALUE)
    private int year;

    @NotNull(message = PAGES_NOT_NULL)
    @Positive(message = PAGES_POSITIVE)
    private int pages;

    @ValidImageType(message = INVALID_FILE_TYPE)
    private MultipartFile imageFile;

    @NotEmpty(message = AUTHOR_REQUIRED)
    @ExistingAuthor(message = NON_EXISTING_AUTHOR)
    private String author;

    private String authorName;

    @NotNull(message = GENRE_NOT_NULL)
    private BookGenreEnum genre;

    @NotEmpty(message = PUBLISHER_REQUIRED)
    @Size(min = 2, max = 100, message = PUBLISHER_NAME_SIZE)
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