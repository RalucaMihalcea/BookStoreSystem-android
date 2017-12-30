package model;

import java.io.Serializable;

/**
 * Created by Raluca on 28.12.2017.
 */

public class Review implements Serializable {

    private Long id;
    private Long idBook;
    private String username;
    private String textReview;
    private int starReview;

    public Review() {
    }

    public Review(Long id, Long idBook, String username, String textReview, int starReview) {
        this.id = id;
        this.idBook = idBook;
        this.username = username;
        this.textReview = textReview;
        this.starReview = starReview;
    }

    public Review(Long idBook, String username, String textReview, int starReview) {
        this.idBook = idBook;
        this.username = username;
        this.textReview = textReview;
        this.starReview = starReview;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTextReview() {
        return textReview;
    }

    public void setTextReview(String textReview) {
        this.textReview = textReview;
    }

    public int getStarReview() {
        return starReview;
    }

    public void setStarReview(int starReview) {
        this.starReview = starReview;
    }
}
