package mk.ukim.ukim.finki.emt2025.service.application;


import mk.ukim.ukim.finki.emt2025.dto.*;
import mk.ukim.ukim.finki.emt2025.model.domain.BookCopy;

import java.util.List;
import java.util.Optional;

public interface UserApplicationService {
    Optional<DisplayUserDto> register(CreateUserDto createUserDto);

    Optional<DisplayUserDto> login(LoginUserDto loginUserDto);

    Optional<DisplayUserDto> findByUsername(String username);
    List<DisplayBookDto> addBookToWishlist(String username, Long bookId);
    List<DisplayBookDto> getUserWishList(String username);
    List<DisplayBookCopyDto> rentAllCopiesFromWishList(String username);
}
