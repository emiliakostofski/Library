package mk.ukim.ukim.finki.emt2025.repository;

import mk.ukim.ukim.finki.emt2025.model.domain.BookCopy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookCopyRepository extends JpaRepository<BookCopy, Long> {

}
