package controller;

import dto.BookDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import service.BookService;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<BookDTO> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    public BookDTO getBookByID(@PathVariable long id) {
        return bookService.getBookByID(id);
    }

    @GetMapping("/title")
    public List<BookDTO> findBookByTitle(@RequestParam String title) {
        return bookService.findBookByTitle(title);
    }

    @GetMapping("/author")
    public List<BookDTO> findBookByAuthor(@RequestParam String author) {
        return bookService.findBookByAuthor(author);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createBook(@Valid @RequestBody BookDTO bookDTO) {
        bookService.createBook(bookDTO);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateBookRating(@Valid @RequestBody BookDTO bookDTO) {
        bookService.updateBookRating(bookDTO);
    }

    @PutMapping("/access")
    public void updateBookAccess(@Valid @RequestBody BookDTO bookDTO) {
        bookService.updateBookAccess(bookDTO);
    }
}
