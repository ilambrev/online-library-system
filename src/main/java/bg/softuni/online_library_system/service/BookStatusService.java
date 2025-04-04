package bg.softuni.online_library_system.service;

import bg.softuni.online_library_system.model.dto.BookDTO;
import bg.softuni.online_library_system.model.dto.BookStatusDTO;
import bg.softuni.online_library_system.model.enums.BookStatusEnum;

import java.util.List;

public interface BookStatusService {

    List<BookStatusDTO> getAllByUserIdAndStatus(String username, BookStatusEnum status);

    void cancelReservation(Long id);

    List<BookDTO> getUserOverdueBooks(String username);

    void confirmBookReservations(List<Long> ids);
}