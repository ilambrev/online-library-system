package bg.softuni.online_library_system.model.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static bg.softuni.online_library_system.common.constant.CartConstants.BOOK_BORROW_PERIOD;

public class BookStatusDTO {

    private Long id;

    private String bookTitle;

    private String bookImageURL;

    private LocalDateTime borrowDate;

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

    public LocalDateTime getBorrowDate() {
        return borrowDate;
    }

    public BookStatusDTO setBorrowDate(LocalDateTime borrowDate) {
        this.borrowDate = borrowDate;
        return this;
    }

    public String getReturnDeadline() {
        return borrowDate.plusDays(BOOK_BORROW_PERIOD)
                .format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
    }
}