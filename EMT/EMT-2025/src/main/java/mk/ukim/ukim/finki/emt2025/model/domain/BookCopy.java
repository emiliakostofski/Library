package mk.ukim.ukim.finki.emt2025.model.domain;

import jakarta.persistence.*;
import lombok.Data;
import mk.ukim.ukim.finki.emt2025.model.enumerations.Condition;

@Data
@Entity
public class BookCopy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Boolean isRented;
    @Enumerated(EnumType.STRING)
    private Condition condition;
    @ManyToOne
    private Book book;

    public BookCopy() {
    }

    public BookCopy(Book book) {
        this.book = book;
        this.isRented=false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getRented() {
        return isRented;
    }

    public void setIsRented(Boolean rented) {
        isRented = rented;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
