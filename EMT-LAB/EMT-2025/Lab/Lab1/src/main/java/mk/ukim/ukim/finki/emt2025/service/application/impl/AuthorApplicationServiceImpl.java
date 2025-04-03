package mk.ukim.ukim.finki.emt2025.service.application.impl;

import mk.ukim.ukim.finki.emt2025.dto.CreateAuthorDto;
import mk.ukim.ukim.finki.emt2025.dto.DisplayAuthorDto;
import mk.ukim.ukim.finki.emt2025.service.application.AuthorApplicationService;
import mk.ukim.ukim.finki.emt2025.service.domain.AuthorService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthorApplicationServiceImpl implements AuthorApplicationService {
    private final AuthorService authorService;

    public AuthorApplicationServiceImpl(AuthorService authorService) {
        this.authorService = authorService;
    }

    @Override
    public Optional<DisplayAuthorDto> findById(Long id) {
        return authorService.findById(id).map(DisplayAuthorDto::from);
    }

    @Override
    public Optional<DisplayAuthorDto> save(CreateAuthorDto author) {
        return authorService.save(author.toAuthor())
                    .map(DisplayAuthorDto::from);
    }

    @Override
    public Optional<DisplayAuthorDto> update(Long id, CreateAuthorDto author) {
        return authorService.update(id,author.toAuthor())
                .map(DisplayAuthorDto::from);
    }

    @Override
    public void deleteById(Long id) {
        authorService.deleteById(id);
    }

    @Override
    public List<DisplayAuthorDto> findAll() {
            return authorService.findAll().stream()
                    .map(DisplayAuthorDto::from)
                    .collect(Collectors.toList());
    }
}
