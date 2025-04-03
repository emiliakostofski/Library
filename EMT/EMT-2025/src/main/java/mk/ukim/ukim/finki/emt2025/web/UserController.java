package mk.ukim.ukim.finki.emt2025.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import mk.ukim.ukim.finki.emt2025.dto.*;
import mk.ukim.ukim.finki.emt2025.model.exceptions.InvalidArgumentsException;
import mk.ukim.ukim.finki.emt2025.model.exceptions.InvalidUserCredentialsException;
import mk.ukim.ukim.finki.emt2025.model.exceptions.PasswordsDoNotMatchException;
import mk.ukim.ukim.finki.emt2025.service.application.UserApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@Tag(name = "User API", description = "Endpoints for user authentication and registration") // Swagger tag
public class UserController {

    private final UserApplicationService userApplicationService;

    public UserController(UserApplicationService userApplicationService) {
        this.userApplicationService = userApplicationService;
    }

    @Operation(summary = "Register a new user", description = "Creates a new user account")
    @ApiResponses(
            value = {@ApiResponse(
                    responseCode = "200",
                    description = "User registered successfully"
            ), @ApiResponse(
                    responseCode = "400", description = "Invalid input or passwords do not match"
            )}
    )
    @PostMapping("/register")
    public ResponseEntity<DisplayUserDto> register(@RequestBody CreateUserDto createUserDto) {
        try {
            return userApplicationService.register(createUserDto)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (InvalidArgumentsException | PasswordsDoNotMatchException exception) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "User login", description = "Authenticates a user and starts a session")
    @ApiResponses(
            value = {@ApiResponse(
                    responseCode = "200",
                    description = "User authenticated successfully"
            ), @ApiResponse(responseCode = "404", description = "Invalid username or password")}
    )
    @PostMapping("/login")
    public ResponseEntity<DisplayUserDto> login(HttpServletRequest request) {
        try {
            DisplayUserDto displayUserDto = userApplicationService.login(
                    new LoginUserDto(request.getParameter("username"), request.getParameter("password"))
            ).orElseThrow(InvalidUserCredentialsException::new);

            request.getSession().setAttribute("user", displayUserDto.toUser());
            return ResponseEntity.ok(displayUserDto);
        } catch (InvalidUserCredentialsException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "User logout", description = "Ends the user's session")
    @ApiResponse(responseCode = "200", description = "User logged out successfully")
    @GetMapping("/logout")
    public void logout(HttpServletRequest request) {
        request.getSession().invalidate();
    }

    @Operation(summary = "My wishlist", description = "Shows the list of books in wish list")
    @GetMapping("/my_wishlist/{username}")
    public List<DisplayBookDto> getUserWishList(@PathVariable String username){
        return userApplicationService.getUserWishList(username);
    }
    @Operation(summary = "Add book to wishlist", description = "Adds a book in in wish list")
    @PostMapping("/add_to_wishlist/{username}")
    public List<DisplayBookDto> addBookToWishList(@PathVariable String username,@RequestBody Long bookId){
        return userApplicationService.addBookToWishlist(username,bookId);
    }

    @Operation(summary = "My wishlist", description = "Shows the list of books in wish list")
    @GetMapping("/rent_wishlist/{username}")
    public List<DisplayBookCopyDto> rentAllWishList(@PathVariable String username){
        return userApplicationService.rentAllCopiesFromWishList(username);
    }

}