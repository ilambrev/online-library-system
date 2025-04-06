package bg.softuni.online_library_system.service.impl;

import bg.softuni.online_library_system.model.dto.PublisherDTO;
import bg.softuni.online_library_system.model.entity.PublisherEntity;
import bg.softuni.online_library_system.repository.PublisherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PublisherServiceImplTest {

    private PublisherServiceImpl serviceToTest;

    @Mock
    private PublisherRepository mockPublisherRepository;

    @Mock
    private ModelMapper mockModelMapper;

    @BeforeEach
    void setUp() {
        serviceToTest = new PublisherServiceImpl(mockPublisherRepository, mockModelMapper);
    }

    @Test
    void getAllPublishers() {
        PublisherEntity testPublisherEntity1 = createPublisherEntity();
        testPublisherEntity1.setId(1L);
        PublisherEntity testPublisherEntity2 = createPublisherEntity();
        testPublisherEntity2.setId(2L);
        testPublisherEntity2.setName("Bloomsbury Publishing");
        testPublisherEntity2.setCreated(LocalDateTime.of(2025, Month.MARCH, 12, 15, 10));

        PublisherDTO testPublisherDTO1 = createPublisherDTO();
        testPublisherDTO1.setId(1L);
        PublisherDTO testPublisherDTO2 = createPublisherDTO();
        testPublisherDTO2.setId(2L);
        testPublisherDTO2.setName("Bloomsbury Publishing");
        testPublisherDTO2.setCreated(LocalDateTime.of(2025, Month.MARCH, 12, 15, 10));

        when(mockPublisherRepository.findAll()).thenReturn(List.of(testPublisherEntity1, testPublisherEntity2));
        when(mockModelMapper.map(testPublisherEntity1, PublisherDTO.class)).thenReturn(testPublisherDTO1);
        when(mockModelMapper.map(testPublisherEntity2, PublisherDTO.class)).thenReturn(testPublisherDTO2);

        List<PublisherDTO> result = serviceToTest.getAllPublishers();

        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals("Crown Publishing", result.get(0).getName());
        assertEquals(testPublisherEntity1.getCreated(), testPublisherDTO1.getCreated());
        assertEquals(2L, result.get(1).getId());
        assertEquals("Bloomsbury Publishing", result.get(1).getName());
        assertEquals(testPublisherEntity2.getCreated(), testPublisherDTO2.getCreated());
    }

    private static PublisherEntity createPublisherEntity() {
        return new PublisherEntity()
                .setName("Crown Publishing")
                .setCreated(LocalDateTime.of(2025, Month.FEBRUARY, 25, 14, 33));
    }

    private static PublisherDTO createPublisherDTO() {
        return new PublisherDTO()
                .setName("Crown Publishing")
                .setCreated(LocalDateTime.of(2025, Month.FEBRUARY, 25, 14, 33));
    }
}