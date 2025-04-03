package mk.ukim.ukim.finki.emt2025.service.domain.impl;

import mk.ukim.ukim.finki.emt2025.model.domain.Book;
import mk.ukim.ukim.finki.emt2025.model.enumerations.Category;
import mk.ukim.ukim.finki.emt2025.repository.BookRepository;
import mk.ukim.ukim.finki.emt2025.service.domain.AuthorService;
import mk.ukim.ukim.finki.emt2025.service.domain.BookService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final AuthorService authorService;

    public BookServiceImpl(BookRepository bookRepository, AuthorService authorService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Optional<Book> findById(Long id) {
        return bookRepository.findById(id);
    }

    @Override
    public Optional<Book> save(Book book) {
        if (book.getName()!= null &&
                authorService.findById(book.getAuthor().getId()).isPresent()
                && book.getCategory()!=null) {
            return Optional.of(
                    bookRepository.save(
                            new Book(
                                    book.getName(),
                                    book.getCategory(),
                                    authorService.findById(book.getAuthor().getId()).get()
                                    )));
        }
        return Optional.empty();

    }

    @Override
    public Optional<Book> update(Long id, Book book) {
        return bookRepository.findById(id)
                .map(existingBook -> {
                    if (book.getName() != null) {
                        existingBook.setName(book.getName());
                    }
                    if (book.getCategory() != null) {
                        existingBook.setCategory(book.getCategory());
                    }
                    if (book.getAuthor() != null && authorService.findById(book.getAuthor().getId()).isPresent()) {
                        existingBook.setAuthor(authorService.findById(book.getAuthor().getId()).get());
                    }
                    return bookRepository.save(existingBook);
                });
    }

    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

//    @Override
//    public Optional<Book> markAsRented(Long id,BookDto book) {
//        return bookRepository.findById(id)
//                .map(existingBook ->{
//                    if(book.getAvailableCopies()>0){
//                        existingBook.setAvailableCopies(existingBook.getAvailableCopies()-1);
//                    }
//                    return bookRepository.save(existingBook);
//                });
//    }

    @Override
    public List<Book> search(String name, Long authorId, Category category) {
//        if (name != null && authorId == null && category == null)
//            return bookRepository.findByNameContainingIgnoreCase(name);
//
//        if (name == null && authorId != null && category == null)
//            return bookRepository.findByAuthorId(authorId);
//
//        if (name == null && authorId == null && category != null)
//            return bookRepository.findByCategory(category);
        //TODO: make custom @Query for using those methods in repository

        List<Book> books = bookRepository.findAll();

        return books.stream()
                .filter(book -> name == null || book.getName().toLowerCase().contains(name.toLowerCase()))
                .filter(book -> authorId == null || book.getAuthor().getId().equals(authorId))
                .filter(book -> category == null || book.getCategory().equals(category))
                .collect(Collectors.toList());
    }

}
