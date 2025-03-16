package bg.softuni.online_library_system.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CloudinaryService {

    String uploadFile(MultipartFile file, String directoryName) throws IOException;

    void deleteFile(String imageURL) throws IOException;
}