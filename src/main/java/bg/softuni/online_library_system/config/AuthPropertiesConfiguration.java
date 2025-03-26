package bg.softuni.online_library_system.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "online-library-system.auth")
public class AuthPropertiesConfiguration {

    private String rememberMeKey;

    public String getRememberMeKey() {
        return rememberMeKey;
    }

    public AuthPropertiesConfiguration setRememberMeKey(String rememberMeKey) {
        this.rememberMeKey = rememberMeKey;
        return this;
    }
}