package com.pluralsight;

public class Film {
    private int filmId;
    private String title;
    private String description;
    private int releaseYear;
    private int length;

    public Film(int filmId, String title, String description, int releaseYear, int length) {
        this.filmId = filmId;
        this.title = title;
        this.description = description;
        this.releaseYear = releaseYear;
        this.length = length;
    }

    public Film(String title) {
    }

    @Override
    public String toString() {
        return String.format("%d: %s (%d) - %d min\n  %s",
                filmId, title, releaseYear, length, description);
    }
}
