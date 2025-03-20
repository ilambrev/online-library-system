package bg.softuni.online_library_system.service;

import bg.softuni.online_library_system.model.dto.PublisherDTO;
import bg.softuni.online_library_system.model.entity.PublisherEntity;

import java.util.List;

public interface PublisherService {

    List<PublisherDTO> getAllPublishers();

    PublisherEntity getPublisherByName(String name);

    Long addPublisher(String name);

    PublisherEntity getPublisherById(Long id);
}