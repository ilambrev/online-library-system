package bg.softuni.online_library_system.service.impl;

import bg.softuni.online_library_system.model.dto.AddAuthorDTO;
import bg.softuni.online_library_system.model.dto.AuthorDTO;
import bg.softuni.online_library_system.model.entity.AuthorEntity;
import bg.softuni.online_library_system.repository.AuthorRepository;
import bg.softuni.online_library_system.service.AuthorService;
import bg.softuni.online_library_system.service.CloudinaryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;
    private final CloudinaryService cloudinaryService;
    private final ModelMapper modelMapper;

    public AuthorServiceImpl(AuthorRepository authorRepository, CloudinaryService cloudinaryService, ModelMapper modelMapper) {
        this.authorRepository = authorRepository;
        this.cloudinaryService = cloudinaryService;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean addAuthor(AddAuthorDTO addAuthorDTO) throws IOException {
        String directory = "authors";
        String imageUrl = this.cloudinaryService.uploadFile(addAuthorDTO.getImageFile(), directory);

        AuthorEntity newAuthor = this.modelMapper.map(addAuthorDTO, AuthorEntity.class);
        newAuthor.setImageURL(imageUrl);

        this.authorRepository.save(newAuthor);

        return true;
    }

    @Override
    public List<AuthorDTO> getAllAuthorsOrderByFirstName() {
        List<AuthorEntity> authors = this.authorRepository.findAllByOrderByFirstNameAsc();
        return authors.stream()
                .map(a -> this.modelMapper.map(a, AuthorDTO.class))
                .toList();
    }

    @Override
    public List<AuthorDTO> getAllAuthors() {
        List<AuthorEntity> authors = this.authorRepository.findAll();
        return authors.stream()
                .map(a -> this.modelMapper.map(a, AuthorDTO.class))
                .toList();
    }

    @Override
    public AuthorDTO getAuthor(Long id) {
        Optional<AuthorEntity> authorOptional = this.authorRepository.findById(id);
        if (authorOptional.isEmpty()) {
            return null;
        }
        AuthorEntity author = authorOptional.get();

        return this.modelMapper.map(author, AuthorDTO.class);
    }
}