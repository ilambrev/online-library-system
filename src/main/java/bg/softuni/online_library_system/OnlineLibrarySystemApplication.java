package bg.softuni.online_library_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class OnlineLibrarySystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnlineLibrarySystemApplication.class, args);
	}

}