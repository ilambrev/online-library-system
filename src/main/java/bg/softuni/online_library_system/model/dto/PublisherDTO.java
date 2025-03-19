package bg.softuni.online_library_system.model.dto;

import java.time.LocalDateTime;

public class PublisherDTO {

    private String name;

    private LocalDateTime created;

    public PublisherDTO() {
    }

    public String getName() {
        return name;
    }

    public PublisherDTO setName(String name) {
        this.name = name;
        return this;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public PublisherDTO setCreated(LocalDateTime created) {
        this.created = created;
        return this;
    }
}