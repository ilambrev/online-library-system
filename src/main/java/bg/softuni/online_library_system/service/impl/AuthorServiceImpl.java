package bg.softuni.online_library_system.service.impl;

import bg.softuni.online_library_system.model.dto.AddAuthorDTO;
import bg.softuni.online_library_system.model.dto.AuthorDTO;
import bg.softuni.online_library_system.model.entity.AuthorEntity;
import bg.softuni.online_library_system.repository.AuthorRepository;
import bg.softuni.online_library_system.service.AuthorService;
import bg.softuni.online_library_system.service.CloudinaryService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static bg.softuni.online_library_system.common.constant.CloudinaryConstants.AUTHORS_IMAGES_DIRECTORY;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;
    private final CloudinaryService cloudinaryService;
    private final ModelMapper modelMapper;

    public AuthorServiceImpl(AuthorRepository authorRepository, CloudinaryService cloudinaryService,
                             ModelMapper modelMapper) {
        this.authorRepository = authorRepository;
        this.cloudinaryService = cloudinaryService;
        this.modelMapper = modelMapper;
    }

    @Override
    public Long addAuthor(AddAuthorDTO addAuthorDTO) throws IOException {
        String imageUrl = this.cloudinaryService.uploadFile(addAuthorDTO.getImageFile(), AUTHORS_IMAGES_DIRECTORY);

        AuthorEntity newAuthor = this.modelMapper.map(addAuthorDTO, AuthorEntity.class);
        newAuthor.setImageURL(imageUrl);

        return this.authorRepository.save(newAuthor).getId();
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

    @Override
    public AuthorEntity getAuthorById(Long id) {
        return this.authorRepository.findById(id).orElse(null);
    }

    @Override
    public Page<AuthorDTO> getAllAuthorsOrderByFirstName(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("firstName").ascending());

        return this.authorRepository.findAll(pageable)
                .map(entity -> this.modelMapper.map(entity, AuthorDTO.class));
    }

    @Override
    public List<AuthorDTO> getAllAuthors() {
        return mapAuthors(authorRepository.findAll());
    }

    @Override
    public List<AuthorDTO> getAuthorsByFirstNameStartingWith(String letter) {
        return mapAuthors(this.authorRepository.findByFirstNameStartingWith(letter));
    }

    @Override
    public List<AuthorDTO> getAuthorsByLastNameStartingWith(String letter) {
        return mapAuthors(this.authorRepository.findByLastNameStartingWith(letter));
    }

    private List<AuthorDTO> mapAuthors(List<AuthorEntity> authors) {
        return authors.stream()
                .map(a -> this.modelMapper.map(a, AuthorDTO.class))
                .toList();
    }
}