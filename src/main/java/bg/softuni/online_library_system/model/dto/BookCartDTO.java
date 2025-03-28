package bg.softuni.online_library_system.model.dto;

public class BookCartDTO {

    private Long id;

    private String title;

    private String imageURL;

    public BookCartDTO() {
    }

    public Long getId() {
        return id;
    }

    public BookCartDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public BookCartDTO setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getImageURL() {
        return imageURL;
    }

    public BookCartDTO setImageURL(String imageURL) {
        this.imageURL = imageURL;
        return this;
    }
}