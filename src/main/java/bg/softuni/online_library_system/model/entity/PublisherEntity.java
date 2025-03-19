package bg.softuni.online_library_system.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "publishers")
public class PublisherEntity extends BaseEntity {

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "created", nullable = false)
    private LocalDateTime created;

    public PublisherEntity() {
    }

    public String getName() {
        return name;
    }

    public PublisherEntity setName(String name) {
        this.name = name;
        return this;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public PublisherEntity setCreated(LocalDateTime created) {
        this.created = created;
        return this;
    }
}