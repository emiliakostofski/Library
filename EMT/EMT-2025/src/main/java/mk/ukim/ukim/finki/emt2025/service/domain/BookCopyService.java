package mk.ukim.ukim.finki.emt2025.service.domain;

import mk.ukim.ukim.finki.emt2025.model.domain.Book;
import mk.ukim.ukim.finki.emt2025.model.domain.BookCopy;
import mk.ukim.ukim.finki.emt2025.model.enumerations.Condition;

import java.util.List;
import java.util.Optional;

public interface BookCopyService {
    Optional<BookCopy> createCopy(Long id);
    Optional<BookCopy> findById(Long id);
    List<BookCopy> findAll();
    List<BookCopy> findByBook(Long id);
    Optional<BookCopy> rent(Long id);
    public Optional<BookCopy> returnBook(Long id);
    public Optional<BookCopy> changeCondition(Long id, Condition condition);
}
