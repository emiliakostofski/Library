package mk.ukim.ukim.finki.emt2025.dto;

import mk.ukim.ukim.finki.emt2025.model.domain.Author;
import mk.ukim.ukim.finki.emt2025.model.domain.Country;

import java.util.List;
import java.util.stream.Collectors;

public record DisplayAuthorDto(Long id,String name, String surname, Country country) {
    public static DisplayAuthorDto from(Author author){
        return new DisplayAuthorDto(author.getId(),author.getName(),author.getSurname(),author.getCountry());
    }
    public static List<DisplayAuthorDto> from(List<Author> authors){
        return authors.stream().map(DisplayAuthorDto::from).collect(Collectors.toList());
    }
}
