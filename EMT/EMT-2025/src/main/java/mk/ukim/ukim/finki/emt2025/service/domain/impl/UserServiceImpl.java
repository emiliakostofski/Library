package mk.ukim.ukim.finki.emt2025.service.domain.impl;

import mk.ukim.ukim.finki.emt2025.model.domain.Book;
import mk.ukim.ukim.finki.emt2025.model.domain.BookCopy;
import mk.ukim.ukim.finki.emt2025.model.domain.User;
import mk.ukim.ukim.finki.emt2025.model.enumerations.Role;
import mk.ukim.ukim.finki.emt2025.model.exceptions.*;
import mk.ukim.ukim.finki.emt2025.repository.UserRepository;
import mk.ukim.ukim.finki.emt2025.service.domain.BookCopyService;
import mk.ukim.ukim.finki.emt2025.service.domain.BookService;
import mk.ukim.ukim.finki.emt2025.service.domain.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final BookService bookService;
    private final BookCopyService bookCopyService;
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, BookService bookService, BookCopyService bookCopyService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.bookService = bookService;
        this.bookCopyService = bookCopyService;
    }

    @Override
    public User register(String username, String password, String repeatPassword, String name, String surname, Role role) {
        if (username == null || username.isEmpty() || password == null || password.isEmpty())
            throw new InvalidUsernameOrPasswordException();
        if (!password.equals(repeatPassword)) throw new PasswordsDoNotMatchException();
        if (userRepository.findByUsername(username).isPresent())
            throw new UsernameAlreadyExistsException(username);
        User user = new User(username, passwordEncoder.encode(password), name, surname, role);
        return userRepository.save(user);
    }

    @Override
    public User login(String username, String password) {
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            throw new InvalidArgumentsException();
        }
        return userRepository.findByUsernameAndPassword(username, password).orElseThrow(
                InvalidUserCredentialsException::new);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    @Override
    public List<Book> addBookToWishList(String username, Long bookId) {
        Book book=bookService.findById(bookId).get();
        List<BookCopy> bookCopies=bookCopyService.findByBook(bookId);
        User user=userRepository.findByUsername(username).get();
        if(!bookCopies.isEmpty()){
            user.getWishListBooks().add(book);
            userRepository.save(user);
            return user.getWishListBooks();
        }
        throw new RuntimeException("No available copies");
    }

    @Override
    public List<Book> getUserWishList(String username) {
        User user=userRepository.findByUsername(username).get();
        return user.getWishListBooks();
    }

    @Override
    public List<BookCopy> rentAllCopiesFromWishList(String username) {
        List<Book> books=userRepository.findByUsername(username).get().getWishListBooks();
        List<BookCopy> userBookCopies=new ArrayList<>();
        books.stream().forEach(b->{
            List<BookCopy> bookCopies=bookCopyService.findByBook(b.getId());
            if(!bookCopies.isEmpty()){
                userBookCopies.add(bookCopies.get(0));
                bookCopyService.rent(bookCopies.get(0).getId());
            }
        });
        return userBookCopies;
    }
}
