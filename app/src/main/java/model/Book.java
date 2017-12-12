package model;

/**
 * Created by Raluca on 26.11.2017.
 */

public class Book {
    private Long id;

    private String title;

    private String author;

    private double price;

    private String namePicture;

    private String category;


    public Book() {

    }

    public Book(String title, String author, double price) {
        this.title = title;
        this.author = author;
        this.price = price;
    }

    public Book(Long id, String title, String author, double price, String namePicture) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.price = price;
        this.namePicture = namePicture;

    }

    public Book(String title, String author, double price, String namePicture) {
        this.title = title;
        this.author = author;
        this.price = price;
        this.namePicture = namePicture;

    }

    public Book(String title, String author, String category, double price, String namePicture) {
        this.title = title;
        this.author = author;
        this.category = category;
        this.price = price;
        this.namePicture = namePicture;
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
}
