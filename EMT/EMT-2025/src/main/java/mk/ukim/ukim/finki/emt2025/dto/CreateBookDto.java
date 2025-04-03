package mk.ukim.ukim.finki.emt2025.dto;

import mk.ukim.ukim.finki.emt2025.model.domain.Author;
import mk.ukim.ukim.finki.emt2025.model.domain.Book;
import mk.ukim.ukim.finki.emt2025.model.enumerations.Category;

public record CreateBookDto(String name, Category category, Long author) {
    public Book toBook(Category category1, Author author1){
        return new Book(name,category1,author1);
    }
}
