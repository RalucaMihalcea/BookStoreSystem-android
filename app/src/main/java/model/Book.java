package model;

/**
 * Created by Raluca on 26.11.2017.
 */

public class Book {
    private Long id;

    private String title;

    private String author;

    private double price;

    private long idPicture;

    private String category;

    private User user;

    public User getUser() {
        return user;
    }

    public Book() {

    }

    public Book(String title, String author, double price) {
        this.title = title;
        this.author = author;
        this.price = price;
        this.user = new User();
    }

    public Book(String title, String author, double price, User user) {
        this.title = title;
        this.author = author;
        this.price = price;
        this.user = user;
    }

    public Book(Long id, String title, String author, double price, long idPicture) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.price = price;
        this.idPicture = idPicture;
        this.user = new User();
    }

    public Book(String title, String author, double price, long idPicture) {
        this.title = title;
        this.author = author;
        this.price = price;
        this.idPicture = idPicture;
        this.user = new User();
    }

    public Book(String title, String author, double price, long idPicture, String category, User user) {
        this.title = title;
        this.author = author;
        this.price = price;
        this.idPicture = idPicture;
        this.category = category;
        this.user = user;
    }
    public Book(String title, String author, String category, double price, long idPicture) {
        this.title = title;
        this.author = author;
        this.category = category;
        this.price = price;
        this.idPicture = idPicture;
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

    public void setUser(User user) {
        this.user = user;
    }

    public long getIdPicture() {
        return idPicture;
    }

    public void setIdPicture(long idPicture) {
        this.idPicture = idPicture;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
