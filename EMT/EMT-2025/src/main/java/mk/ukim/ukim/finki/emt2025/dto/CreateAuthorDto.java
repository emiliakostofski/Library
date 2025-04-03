package mk.ukim.ukim.finki.emt2025.dto;

import mk.ukim.ukim.finki.emt2025.model.domain.Author;
import mk.ukim.ukim.finki.emt2025.model.domain.Country;

public record CreateAuthorDto(String name, String surname, Country country) {
    public Author toAuthor(){
        return new Author(name,surname,country);
    }
}
