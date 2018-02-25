package model;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by Raluca on 21.02.2018.
 */

public class BookViewsAndDate implements Serializable {

    private Long id;
    private Long idBook;
    private int views;
    private Calendar date;
    private String username;

    public BookViewsAndDate(Long id, Long idBook, int views, Calendar date, String username) {
        this.id = id;
        this.idBook = idBook;
        this.views = views;
        this.date = date;
        this.username = username;
    }

    public BookViewsAndDate(Long idBook, int views, Calendar date, String username) {
        this.idBook = idBook;
        this.views = views;
        this.date = date;
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

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
