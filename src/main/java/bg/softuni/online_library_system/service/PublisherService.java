package bg.softuni.online_library_system.service;

import bg.softuni.online_library_system.model.dto.PublisherDTO;

import java.util.List;

public interface PublisherService {

    List<PublisherDTO> getAllPublishers();
}