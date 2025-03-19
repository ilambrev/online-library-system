package bg.softuni.online_library_system.service.impl;

import bg.softuni.online_library_system.model.dto.PublisherDTO;
import bg.softuni.online_library_system.repository.PublisherRepository;
import bg.softuni.online_library_system.service.PublisherService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublisherServiceImpl implements PublisherService {
    private final PublisherRepository publisherRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public PublisherServiceImpl(PublisherRepository publisherRepository, ModelMapper modelMapper) {
        this.publisherRepository = publisherRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<PublisherDTO> getAllPublishers() {
        return this.publisherRepository.findAll()
                .stream()
                .map(p -> this.modelMapper.map(p, PublisherDTO.class))
                .toList();
    }
}