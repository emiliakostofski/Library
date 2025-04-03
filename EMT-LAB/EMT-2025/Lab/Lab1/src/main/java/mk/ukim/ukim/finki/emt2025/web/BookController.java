package mk.ukim.ukim.finki.emt2025.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import mk.ukim.ukim.finki.emt2025.model.enumerations.Category;
import mk.ukim.ukim.finki.emt2025.model.enumerations.Condition;
import mk.ukim.ukim.finki.emt2025.dto.CreateBookDto;
import mk.ukim.ukim.finki.emt2025.dto.DisplayBookCopyDto;
import mk.ukim.ukim.finki.emt2025.dto.DisplayBookDto;
import mk.ukim.ukim.finki.emt2025.service.application.BookApplicationService;
import mk.ukim.ukim.finki.emt2025.service.application.BookCopyApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
@Tag(name = "Books API", description = "Endpoints for managing books")
public class BookController {
    private final BookApplicationService bookApplicationService;
    private final BookCopyApplicationService bookCopyApplicationService;

    public BookController(BookApplicationService bookApplicationService, BookCopyApplicationService bookCopyApplicationService) {
        this.bookApplicationService = bookApplicationService;
        this.bookCopyApplicationService = bookCopyApplicationService;
    }

    @Operation(summary = "Get all books", description = "Retrieves a list of all books.")
    @GetMapping
    public List<DisplayBookDto> findAll() {
        return bookApplicationService.findAll();
    }

    @Operation(summary = "Get book by ID", description = "Finds a book by its ID.")
    @GetMapping("{id}")
    public ResponseEntity<DisplayBookDto> findById(@PathVariable Long id) {
        return bookApplicationService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @Operation(
            summary = "Add a new book",
            description = "Creates a new book based on the given BookDto."
    )
    @PostMapping("/add")
    @PreAuthorize("hasRole('LIBRARIAN')")
    public ResponseEntity<DisplayBookDto> save(@RequestBody CreateBookDto createBookDto) {
        return bookApplicationService.save(createBookDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @Operation(
            summary = "Update an existing book", description = "Updates a product by ID using BookDto."
    )
    @PutMapping("/edit/{id}")
    @PreAuthorize("hasRole('LIBRARIAN')")
    public ResponseEntity<DisplayBookDto> update(@PathVariable Long id, @RequestBody CreateBookDto createBookDto) {
        return bookApplicationService.update(id, createBookDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete a book", description = "Deletes a book by its ID.")
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('LIBRARIAN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (bookApplicationService.findById(id).isPresent()) {
            bookApplicationService.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Rent a book", description = "Rents a book by its ID.")
    @PutMapping("/rent/{id}")
    public ResponseEntity<DisplayBookCopyDto> rent(@PathVariable Long id){
        return bookCopyApplicationService.rent(id)
                .map(ResponseEntity::ok)
                .orElseGet(()->ResponseEntity.notFound().build());
    }

    @Operation(summary = "Creates a copy", description = "Creates a copy by its ID.")
    @PostMapping("/createCopy/{id}")
    @PreAuthorize("hasRole('LIBRARIAN')")
    public ResponseEntity<DisplayBookCopyDto> createCopy(@PathVariable Long id) {
        return bookCopyApplicationService.createCopy(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get book copy by ID", description = "Finds a book copy by its ID.")
    @GetMapping("/bookCopies/{id}")
    public List<DisplayBookCopyDto> findAllCopies(@PathVariable Long id) {
        return this.bookCopyApplicationService.findByBook(id);
    }
    @Operation(summary = "Change condition on book copy by id", description = "Change condition on book copy")
    @PatchMapping ("/bookCopies/changeCondition/{id}")
    @PreAuthorize("hasRole('LIBRARIAN')")
    public ResponseEntity<DisplayBookCopyDto> changeCondition(@PathVariable Long id,@RequestParam Condition condition) {
        return this.bookCopyApplicationService.changeCondition(id,condition)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @Operation(summary = "Searches a book", description = "Searches a book")
    @GetMapping("/search")
    public List<DisplayBookDto> searchBooks(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long authorId,
            @RequestParam(required = false) Category category
    ) {
        return bookApplicationService.search(name, authorId, category);
    }

}

