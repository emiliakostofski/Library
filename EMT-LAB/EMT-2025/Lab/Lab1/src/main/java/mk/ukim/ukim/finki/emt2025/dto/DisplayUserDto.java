package mk.ukim.ukim.finki.emt2025.dto;

import mk.ukim.ukim.finki.emt2025.model.domain.Book;
import mk.ukim.ukim.finki.emt2025.model.domain.User;
import mk.ukim.ukim.finki.emt2025.model.enumerations.Role;

import java.util.List;
import java.util.stream.Collectors;

public record DisplayUserDto (String username, String name, String surname, Role role){
    public static DisplayUserDto from(User user) {
        return new DisplayUserDto(
                user.getUsername(),
                user.getName(),
                user.getSurname(),
                user.getRole()
        );
    }
    public User toUser() {
        return new User(username, name, surname, role.name());
    }

}
