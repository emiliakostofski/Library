package mk.ukim.ukim.finki.emt2025.service.application.impl;

import mk.ukim.ukim.finki.emt2025.dto.CreateBookDto;
import mk.ukim.ukim.finki.emt2025.dto.DisplayBookDto;
import mk.ukim.ukim.finki.emt2025.model.domain.Book;
import mk.ukim.ukim.finki.emt2025.model.enumerations.Category;
import mk.ukim.ukim.finki.emt2025.service.application.BookApplicationService;
import mk.ukim.ukim.finki.emt2025.service.domain.AuthorService;
import mk.ukim.ukim.finki.emt2025.service.domain.BookService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookApplicationServiceImpl implements BookApplicationService {
    private final BookService bookService;
    private final AuthorService authorService;

    public BookApplicationServiceImpl(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }

    @Override
    public List<DisplayBookDto> findAll() {
        return bookService.findAll().stream()
                .map(DisplayBookDto::from).collect(Collectors.toList());
    }

    @Override
    public Optional<DisplayBookDto> findById(Long id) {
        return bookService.findById(id).map(DisplayBookDto::from);
    }

    @Override
    public Optional<DisplayBookDto> save(CreateBookDto book) {
        if (book.name()!= null &&
                authorService.findById(book.author()).isPresent()
                && book.category()!=null) {
            return bookService.save(
                            book.toBook(
                                    Category.valueOf(book.category().name()),
                                    authorService.findById(book.author()).get()
                            )).map(DisplayBookDto::from);
        }
        return Optional.empty();
    }

    @Override
    public Optional<DisplayBookDto> update(Long id, CreateBookDto book) {
//        return bookService.findById(id)
//                .map(existingBook -> {
//                    if (book.name() != null) {
//                        existingBook.setName(book.name());
//                    }
//                    if (book.category() != null) {
//                        existingBook.setCategory(book.category());
//                    }
//                    if (book.author() != null && authorService.findById(book.author()).isPresent()) {
//                        existingBook.setAuthor(authorService.findById(book.author()).get());
//                    }
//                    return bookService.save(book.toBook(existingBook.getCategory(),existingBook.getAuthor())).map(DisplayBookDto::from);
//                });
        return bookService.update(id,book.toBook(book.category(),authorService.findById(book.author()).get()))
                .map(DisplayBookDto::from);
    }

    @Override
    public void deleteById(Long id) {
        bookService.deleteById(id);
    }

    @Override
    public List<DisplayBookDto> search(String name, Long authorId, Category category) {
        List<Book> books = bookService.findAll();

        return books.stream()
                .filter(book -> name == null || book.getName().toLowerCase().contains(name.toLowerCase()))
                .filter(book -> authorId == null || book.getAuthor().getId().equals(authorId))
                .filter(book -> category == null || book.getCategory().equals(category))
                .map(DisplayBookDto::from)
                .collect(Collectors.toList());
    }
}
