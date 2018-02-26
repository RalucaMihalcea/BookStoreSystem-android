package model;

import java.io.Serializable;

/**
 * Created by Raluca on 21.02.2018.
 */

public class BookViewsAndDate implements Serializable {

    private Long id;
    private Long idBook;
    private int views;
    private int month;
    private String username;

    public BookViewsAndDate(Long id, Long idBook, int views, int month, String username) {
        this.id = id;
        this.idBook = idBook;
        this.views = views;
        this.month = month;
        this.username = username;
    }

    public BookViewsAndDate(Long idBook, int views, int month, String username) {
        this.idBook = idBook;
        this.views = views;
        this.month = month;
        this.username = username;
    }

    public BookViewsAndDate() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdBook() {
        return idBook;
    }

    public void setIdBook(Long idBook) {
        this.idBook = idBook;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
