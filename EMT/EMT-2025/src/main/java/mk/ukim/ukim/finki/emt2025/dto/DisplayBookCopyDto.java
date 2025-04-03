package mk.ukim.ukim.finki.emt2025.dto;

import mk.ukim.ukim.finki.emt2025.model.domain.Book;
import mk.ukim.ukim.finki.emt2025.model.domain.BookCopy;

import java.util.List;
import java.util.stream.Collectors;

public record DisplayBookCopyDto(Long id, Book book) {
    public static DisplayBookCopyDto from(BookCopy bookCopy){
        return new DisplayBookCopyDto(bookCopy.getId(),bookCopy.getBook());
    }
    public static List<DisplayBookCopyDto> from(List<BookCopy> bookCopies){
        return bookCopies.stream().map(DisplayBookCopyDto::from).collect(Collectors.toList());
    }
}
