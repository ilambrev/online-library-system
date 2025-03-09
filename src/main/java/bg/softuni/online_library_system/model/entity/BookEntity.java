package bg.softuni.online_library_system.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

//@Entity
//@Table(name = "books")
public class BookEntity extends BaseEntity {

    private String name;

    private String description;


}