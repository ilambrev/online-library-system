package bg.softuni.online_library_system.web.rest;

import bg.softuni.online_library_system.model.dto.PublisherDTO;
import bg.softuni.online_library_system.service.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/publishers")
public class PublishersRestController {
    private final PublisherService publisherService;

    @Autowired
    public PublishersRestController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<PublisherDTO>> getAll() {
        return ResponseEntity.ok(this.publisherService.getAllPublishers());
    }
}