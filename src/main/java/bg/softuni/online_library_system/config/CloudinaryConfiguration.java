package bg.softuni.online_library_system.config;

import com.cloudinary.Cloudinary;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfiguration {

    @Bean
    public Cloudinary cloudinary(@Value("${cloudinary.url}") String cloudinaryURL) {
        Cloudinary cloudinary = new Cloudinary(cloudinaryURL);
        cloudinary.config.secure = true;

        return cloudinary;
    }

    @Bean
    public Gson gson() {
        return new GsonBuilder().setPrettyPrinting().create();
    }
}