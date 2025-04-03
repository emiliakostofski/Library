package mk.ukim.ukim.finki.emt2025.service.application.impl;

import mk.ukim.ukim.finki.emt2025.dto.DisplayBookCopyDto;
import mk.ukim.ukim.finki.emt2025.model.enumerations.Condition;
import mk.ukim.ukim.finki.emt2025.service.application.BookCopyApplicationService;
import mk.ukim.ukim.finki.emt2025.service.domain.AuthorService;
import mk.ukim.ukim.finki.emt2025.service.domain.BookCopyService;
import mk.ukim.ukim.finki.emt2025.service.domain.BookService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookCopyApplicationServiceImpl implements BookCopyApplicationService {
    private final BookCopyService bookCopyService;
    private final AuthorService authorService;
    private final BookService bookService;

    public BookCopyApplicationServiceImpl(BookCopyService bookCopyService, AuthorService authorService, BookService bookService) {
        this.bookCopyService = bookCopyService;
        this.authorService = authorService;
        this.bookService = bookService;
    }


    @Override
    public Optional<DisplayBookCopyDto> createCopy(Long id) {
        return bookCopyService.createCopy(id).map(DisplayBookCopyDto::from);
    }

    @Override
    public Optional<DisplayBookCopyDto> findById(Long id) {
        return bookCopyService.findById(id).map(DisplayBookCopyDto::from);
    }

    @Override
    public List<DisplayBookCopyDto> findAll() {
        return bookCopyService.findAll()
                .stream()
                .map(DisplayBookCopyDto::from)
                .collect(Collectors.toList());
    }

    @Override
    public List<DisplayBookCopyDto> findByBook(Long id) {
        return bookCopyService.findByBook(id)
                .stream()
                .map(DisplayBookCopyDto::from)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<DisplayBookCopyDto> rent(Long id) {
//        BookCopy bookCopy=bookCopyService.findById(id).get();
//        bookCopy.setIsRented(true);
//        return bookCopyService.findById(id).map(DisplayBookCopyDto::from);
//        return bookCopyService.findById(id).map(DisplayBookCopyDto::from);
        return bookCopyService.rent(id).map(DisplayBookCopyDto::from);
    }

    @Override
    public Optional<DisplayBookCopyDto> returnBook(Long id) {
        return bookCopyService.returnBook(id).map(DisplayBookCopyDto::from);
    }

    @Override
    public Optional<DisplayBookCopyDto> changeCondition(Long id, Condition condition) {
        return bookCopyService.changeCondition(id,condition).map(DisplayBookCopyDto::from);
    }
}
