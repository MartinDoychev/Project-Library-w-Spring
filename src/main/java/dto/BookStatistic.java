package dto;

import enums.BookAccess;

public record BookStatistic(String title, String ISBN, String genre, String language, BookAccess access, double rating, int appearances) {
}
