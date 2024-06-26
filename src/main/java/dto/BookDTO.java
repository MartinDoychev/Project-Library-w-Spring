package dto;

import enums.BookAccess;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BookDTO {
    private long bookID;
    private String title;
    private String ISBN;
    private String author;
    private String genre;
    private String language;
    private double rating;
    private BookAccess access;

    public BookDTO(String title, String author, String ISBN, String genre, String language, BookAccess access, double rating) {
        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
        this.genre = genre;
        this.language = language;
        this.access = access;
        this.rating = rating;
    }
}
