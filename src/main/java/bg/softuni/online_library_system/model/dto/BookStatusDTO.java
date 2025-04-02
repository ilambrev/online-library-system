package bg.softuni.online_library_system.model.dto;

public class BookStatusDTO {

    private Long id;

    private String bookTitle;

    private String bookImageURL;

    public BookStatusDTO() {
    }

    public Long getId() {
        return id;
    }

    public BookStatusDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public BookStatusDTO setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
        return this;
    }

    public String getBookImageURL() {
        return bookImageURL;
    }

    public BookStatusDTO setBookImageURL(String bookImageURL) {
        this.bookImageURL = bookImageURL;
        return this;
    }
}