package bg.softuni.online_library_system.config;

import bg.softuni.online_library_system.model.enums.UserRoleEnum;
import bg.softuni.online_library_system.repository.UserRepository;
import bg.softuni.online_library_system.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration {
    private final AuthPropertiesConfiguration authPropertiesConfiguration;

    @Autowired
    public SecurityConfiguration(AuthPropertiesConfiguration authPropertiesConfiguration) {
        this.authPropertiesConfiguration = authPropertiesConfiguration;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests(
                        authorizeRequests -> authorizeRequests
                                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                                .requestMatchers(
                                        "/", "/about", "/contacts", "/error",
                                        "/authors/all", "/authors/*/about", "/api/authors/all",
                                        "/books/all", "/books/*/about",
                                        "/users/login", "/users/login-error", "/users/register").permitAll()
                                .requestMatchers("/authors/add").hasAnyRole(UserRoleEnum.ADMIN.name(), UserRoleEnum.STAFF.name())
                                .requestMatchers("/books/add").hasAnyRole(UserRoleEnum.ADMIN.name(), UserRoleEnum.STAFF.name())
                                .requestMatchers("/admin/*").hasRole(UserRoleEnum.ADMIN.name())
                                .anyRequest().authenticated()
                ).formLogin(
                        formLogin -> formLogin
                                .loginPage("/users/login")
                                .usernameParameter("username")
                                .passwordParameter("password")
                                .defaultSuccessUrl("/")
                                .failureForwardUrl("/users/login-error")
                ).logout(
                        logout -> logout
                                .logoutUrl("/users/logout")
                                .logoutSuccessUrl("/")
                                .invalidateHttpSession(true)
                                .deleteCookies("JSESSIONID")
                ).rememberMe(
                        rememberMe -> rememberMe
                                .key(this.authPropertiesConfiguration.getRememberMeKey())
                                .rememberMeParameter("rememberMe")
                                .rememberMeCookieName("rememberMe")
                );

        return httpSecurity.build();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return new UserDetailsServiceImpl(userRepository);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8();
    }

    @Bean
    public SecurityContextRepository securityContextRepository() {
        return new HttpSessionSecurityContextRepository();
    }
}