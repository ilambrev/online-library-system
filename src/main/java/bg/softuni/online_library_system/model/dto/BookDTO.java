package bg.softuni.online_library_system.model.dto;

public class BookDTO {

    private Long id;

    private String title;

    private String imageURL;

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

    public String getImageURL() {
        return imageURL;
    }

    public BookDTO setImageURL(String imageURL) {
        this.imageURL = imageURL;
        return this;
    }
}