package model;

import java.io.Serializable;

/**
 * Created by Raluca on 26.11.2017.
 */

public class Book implements Serializable {
    private Long id;

    private String title;

    private String author;

    private double price;

    private String namePicture;

    private String category;

    private int stars;

    private String description;




    public Book() {

    }

    public Book(String title, String author, double price) {
        this.title = title;
        this.author = author;
        this.price = price;
    }


    public Book(Long id, String title, String author, double price, String namePicture, int stars, String description) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.price = price;
        this.namePicture = namePicture;
        this.stars = stars;
        this.description = description;

    }

    public Book(String title, String author, double price, String namePicture) {
        this.title = title;
        this.author = author;
        this.price = price;
        this.namePicture = namePicture;

    }

    public Book(String title, String author, String category, double price, String namePicture, int stars, String description) {
        this.title = title;
        this.author = author;
        this.category = category;
        this.price = price;
        this.namePicture = namePicture;
        this.stars = stars;
        this.description = description;
    }

    public Book(Long id, String title, String author, String category, double price, String namePicture, int stars, String description) {
        this.id=id;
        this.title = title;
        this.author = author;
        this.category = category;
        this.price = price;
        this.namePicture = namePicture;
        this.stars = stars;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getNamePicture() {
        return namePicture;
    }

    public void setNamePicture(String namePicture) {
        this.namePicture = namePicture;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
