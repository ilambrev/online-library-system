package bg.softuni.online_library_system.config;

import bg.softuni.online_library_system.util.BookBorrowInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {
    private final BookBorrowInterceptor bookBorrowInterceptor;

    @Autowired
    public WebConfiguration(BookBorrowInterceptor bookBorrowInterceptor) {
        this.bookBorrowInterceptor = bookBorrowInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(bookBorrowInterceptor)
                .addPathPatterns("/books/reservations/confirm");
    }
}