package mk.ukim.ukim.finki.emt2025.service.domain.impl;

import mk.ukim.ukim.finki.emt2025.model.domain.Book;
import mk.ukim.ukim.finki.emt2025.model.domain.BookCopy;
import mk.ukim.ukim.finki.emt2025.model.enumerations.Condition;
import mk.ukim.ukim.finki.emt2025.repository.BookCopyRepository;
import mk.ukim.ukim.finki.emt2025.service.domain.BookCopyService;
import mk.ukim.ukim.finki.emt2025.service.domain.BookService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookCopyServiceImpl implements BookCopyService {
    private final BookCopyRepository bookCopyRepository;
    private final BookService bookService;

    public BookCopyServiceImpl(BookCopyRepository bookCopyRepository, BookService bookService) {
        this.bookCopyRepository = bookCopyRepository;
        this.bookService = bookService;
    }

    @Override
    public Optional<BookCopy> createCopy(Long id) {
        Book book=bookService.findById(id).get();
        BookCopy bookCopy=new BookCopy(book);
        bookCopyRepository.save(bookCopy);
        return Optional.of(bookCopy);
    }

    @Override
    public Optional<BookCopy> findById(Long id) {
        return Optional.of(bookCopyRepository.findById(id).get());
    }

    @Override
    public List<BookCopy> findAll() {
        return bookCopyRepository.findAll();
    }

    @Override
    public List<BookCopy> findByBook(Long id) {
        Book book=bookService.findById(id).get();
        return bookCopyRepository.findAll()
                .stream()
                .filter(bookCopy->bookCopy.getBook().equals(book))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<BookCopy> rent(Long id) {
        BookCopy bookCopy=bookCopyRepository.findById(id).get();
        bookCopy.setIsRented(true);
        bookCopyRepository.save(bookCopy);
        return Optional.of(bookCopy);
    }

    @Override
    public Optional<BookCopy> returnBook(Long id) {
        BookCopy bookCopy=bookCopyRepository.findById(id).get();
        bookCopy.setIsRented(false);
        return Optional.of(bookCopy);
    }

    @Override
    public Optional<BookCopy> changeCondition(Long id, Condition condition) {
        return bookCopyRepository.findById(id)
                .map(bookCopy -> {
                    bookCopy.setCondition(condition);
                    return bookCopyRepository.save(bookCopy);
                });
    }
}
