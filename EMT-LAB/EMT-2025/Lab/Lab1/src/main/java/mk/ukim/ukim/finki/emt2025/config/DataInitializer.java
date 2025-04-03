package mk.ukim.ukim.finki.emt2025.config;

import jakarta.annotation.PostConstruct;
import mk.ukim.ukim.finki.emt2025.model.domain.Author;
import mk.ukim.ukim.finki.emt2025.model.domain.Book;
import mk.ukim.ukim.finki.emt2025.model.domain.Country;
import mk.ukim.ukim.finki.emt2025.model.domain.User;
import mk.ukim.ukim.finki.emt2025.model.enumerations.Category;
import mk.ukim.ukim.finki.emt2025.model.enumerations.Role;
import mk.ukim.ukim.finki.emt2025.repository.AuthorRepository;
import mk.ukim.ukim.finki.emt2025.repository.BookRepository;
import mk.ukim.ukim.finki.emt2025.repository.CountryRepository;
import mk.ukim.ukim.finki.emt2025.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final CountryRepository countryRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public DataInitializer(AuthorRepository authorRepository, BookRepository bookRepository, CountryRepository countryRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.countryRepository = countryRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void init() {
        Country c1=countryRepository.save(new Country("Macedonia","Europe"));
        Country c2= countryRepository.save(new Country("Japan","Asia"));

        Author a1= authorRepository.save(new Author("Dimitar","Iliev",c1));
        Author a2=authorRepository.save(new Author("Mila","Ilieva",c2));

        bookRepository.save(new Book("Book 1", Category.CLASSICS,a1));
        bookRepository.save(new Book("Book 2", Category.BIOGRAPHY,a2));

        userRepository.save(new User(
                "di",
                passwordEncoder.encode("di"),
                "User",
                "User",
                Role.ROLE_USER
        ));

        userRepository.save(new User(
                "li",
                passwordEncoder.encode("li"),
                "librarian name",
                "librarian surname",
                Role.ROLE_LIBRARIAN
        ));
    }

}
