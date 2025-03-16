package bg.softuni.online_library_system.service.impl;

import bg.softuni.online_library_system.service.CloudinaryService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class CloudinaryServiceImpl implements CloudinaryService {
    private final Cloudinary cloudinary;
    private final Gson gson;

    @Autowired
    public CloudinaryServiceImpl(Cloudinary cloudinary, Gson gson) {
        this.cloudinary = cloudinary;
        this.gson = gson;
    }

    @Override
    public String uploadFile(MultipartFile file, String directoryName) throws IOException {
        String response = this.gson.toJson(this.cloudinary
                .uploader()
                .upload(file.getBytes(), ObjectUtils.asMap("resource_type", "image", "asset_folder", directoryName)));

        JsonObject jsonObject = JsonParser.parseString(response).getAsJsonObject();

        return jsonObject.get("secure_url").getAsString();
    }

    @Override
    public void deleteFile(String imageURL) throws IOException {
        String publicID = getPublicIdFromURL(imageURL);
        this.cloudinary.uploader().destroy(publicID, ObjectUtils.asMap("invalidate", true));
    }

    private String getPublicIdFromURL(String url) {
        int startIndex = url.lastIndexOf("/");
        String publicID = url.substring(startIndex + 1);
        int endIndex = publicID.indexOf(".");
        if (endIndex != -1) {
            publicID = publicID.substring(0, endIndex);
        }

        return publicID;
    }
}