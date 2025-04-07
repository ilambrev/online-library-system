package bg.softuni.online_library_system.service.impl;

import bg.softuni.online_library_system.model.entity.BaseEntity;
import bg.softuni.online_library_system.model.entity.PublisherEntity;
import bg.softuni.online_library_system.repository.PublisherRepository;
import bg.softuni.online_library_system.service.PublisherService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
public class PublisherServiceImplTestIT {

    @Autowired
    private PublisherService publisherServiceToTest;

    @Autowired
    private PublisherRepository publisherRepository;

    @BeforeEach
    void setUp() {
        publisherRepository.deleteAll();
    }

    @AfterEach
    void tearDown() {
        publisherRepository.deleteAll();
    }

    @Test
    void testGetPublisherByName() {
        PublisherEntity testPublisherEntity = createPublisherEntity();
        PublisherEntity savedPublisherEntity = publisherRepository.save(testPublisherEntity);

        PublisherEntity result = publisherServiceToTest.getPublisherByName(savedPublisherEntity.getName());

        assertEquals(savedPublisherEntity.getId(), result.getId());
        assertEquals(savedPublisherEntity.getName(), result.getName());
        assertEquals(savedPublisherEntity.getCreated(), result.getCreated());
    }

    @Test
    void testAddPublisher() {
        Long id = publisherServiceToTest.addPublisher("Crown Publishing");
        Optional<PublisherEntity> expected = publisherRepository.findById(id);

        assertEquals(expected.map(BaseEntity::getId).orElse(null), id);
    }

    @Test
    void testGetPublisherByIdWithExistingId() {
        PublisherEntity testPublisherEntity = createPublisherEntity();
        PublisherEntity savedPublisherEntity = publisherRepository.save(testPublisherEntity);

        PublisherEntity result = publisherServiceToTest.getPublisherById(savedPublisherEntity.getId());

        assertEquals(savedPublisherEntity.getId(), result.getId());
        assertEquals(savedPublisherEntity.getName(), result.getName());
        assertEquals(savedPublisherEntity.getCreated(), result.getCreated());
    }

    @Test
    void testGetPublisherByIdWithNonExistingId() {
        PublisherEntity result = publisherServiceToTest.getPublisherById(0L);

        assertNull(result);
    }

    private static PublisherEntity createPublisherEntity() {
        return new PublisherEntity()
                .setName("Crown Publishing")
                .setCreated(LocalDateTime.of(2025, Month.FEBRUARY, 25, 14, 33));
    }
}