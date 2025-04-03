package mk.ukim.ukim.finki.emt2025.service.application;

import mk.ukim.ukim.finki.emt2025.dto.DisplayBookCopyDto;
import mk.ukim.ukim.finki.emt2025.model.enumerations.Condition;

import java.util.List;
import java.util.Optional;

public interface BookCopyApplicationService {
    Optional<DisplayBookCopyDto> createCopy(Long id);
    Optional<DisplayBookCopyDto> findById(Long id);
    List<DisplayBookCopyDto> findAll();
    List<DisplayBookCopyDto> findByBook(Long id);
    Optional<DisplayBookCopyDto> rent(Long id);
    public Optional<DisplayBookCopyDto> returnBook(Long id);
    public Optional<DisplayBookCopyDto> changeCondition(Long id, Condition condition);
}
