package service;

import library.Book;
import dto.BookDTO;
import repository.BookRepository;
import repository.UserRepository;
import enums.BookAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    @Autowired
    public BookService(BookRepository bookRepository, UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    public List<BookDTO> getAllBooks() {
        return bookRepository.getAllBooks().stream()
                .map(this::convertToBookDTO)
                .collect(Collectors.toList());
    }

    public BookDTO getBookByID(long bookID) {
        Book book = bookRepository.findBookById(bookID);
        if (book == null) {
            throw new RuntimeException("Book with ID: " + bookID + " does not exist!");
        }
        return convertToBookDTO(book);
    }

    public List<BookDTO> findBookByTitle(String title) {
        return bookRepository.findBookByTitle(title).stream()
                .map(this::convertToBookDTO)
                .collect(Collectors.toList());
    }

    public List<BookDTO> findBookByAuthor(String authorName) {
        long authorID = userRepository.getAuthorID(authorName);
        return bookRepository.findBookByAuthor(authorID).stream()
                .map(this::convertToBookDTO)
                .collect(Collectors.toList());
    }

    public void createBook(BookDTO bookDTO) {
        Book book = convertToBook(bookDTO);
        bookRepository.createBook(book.getTitle(), book.getISBN(), book.getAuthorID(), book.getGenreID(), book.getRating(), book.getAccessID(), book.getLanguageID());
    }

    public void updateBookRating(BookDTO bookDTO) {
        long authorID = userRepository.getAuthorID(bookDTO.getAuthor());
        long bookID = bookRepository.findBookByNameAndAuthor(bookDTO.getTitle(), authorID);
        bookRepository.updateBookRating(bookID, bookDTO.getRating());
    }

    public void updateBookAccess(BookDTO bookDTO) {
        long authorID = userRepository.getAuthorID(bookDTO.getAuthor());
        long bookID = bookRepository.findBookByNameAndAuthor(bookDTO.getTitle(), authorID);
        bookRepository.updateBookAccess(bookID, BookAccess.getBookAccessID(bookDTO.getAccess()));
    }

    private BookDTO convertToBookDTO(Book book) {
        return new BookDTO(
                book.getTitle(),
                userRepository.getAuthorByID(book.getAuthorID()).getFirstName() + " " + userRepository.getAuthorByID(book.getAuthorID()).getLastName(),
                book.getISBN(),
                bookRepository.getGenreByID(book.getGenreID()),
                bookRepository.getLanguageById(book.getLanguageID()),
                BookAccess.getBookAccess(book.getAccessID()),
                book.getRating()
        );
    }

    private Book convertToBook(BookDTO bookDTO) {
        Book book = new Book();
        book.setBookID(bookDTO.getBookID());
        book.setTitle(bookDTO.getTitle());
        book.setISBN(bookDTO.getISBN());
        book.setAuthorID(userRepository.getAuthorID(bookDTO.getAuthor()));
        book.setGenreID(bookRepository.getGenreID(bookDTO.getGenre()));
        book.setLanguageID(bookRepository.getLanguageID(bookDTO.getLanguage()));
        book.setRating(bookDTO.getRating());
        book.setAccessID(BookAccess.getBookAccessID(bookDTO.getAccess()));
        return book;
    }
}
