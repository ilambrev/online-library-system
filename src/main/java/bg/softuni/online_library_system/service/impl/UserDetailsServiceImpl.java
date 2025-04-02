package bg.softuni.online_library_system.service.impl;

import bg.softuni.online_library_system.model.entity.UserEntity;
import bg.softuni.online_library_system.model.enums.BookStatusEnum;
import bg.softuni.online_library_system.model.security.CustomUserDetails;
import bg.softuni.online_library_system.repository.BookStatusRepository;
import bg.softuni.online_library_system.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.time.LocalDateTime;
import java.util.List;

public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    private final BookStatusRepository bookStatusRepository;

    public UserDetailsServiceImpl(UserRepository userRepository, BookStatusRepository bookStatusRepository) {
        this.userRepository = userRepository;
        this.bookStatusRepository = bookStatusRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = this.userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found!"));

        List<GrantedAuthority> authorities =
                List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().getRole().name()));

        return new CustomUserDetails(
                user.getUsername(),
                user.getPassword(),
                user.getFirstName(),
                user.getLastName(),
                user.getImageURL(),
                user.getBorrowedBooks().size(),
                getNumOfReservedBooks(user.getId()),
                hasOverdueBooks(user),
                authorities);
    }

    private boolean hasOverdueBooks(UserEntity user) {
        return !user.getBorrowedBooks()
                .stream()
                .filter(b -> b.getBorrowDate().isBefore(LocalDateTime.now().plusDays(10)))
                .toList().isEmpty();
    }

    private int getNumOfReservedBooks(Long userId) {
        return this.bookStatusRepository.countByUserIdAndStatus(userId, BookStatusEnum.RESERVED);
    }
}