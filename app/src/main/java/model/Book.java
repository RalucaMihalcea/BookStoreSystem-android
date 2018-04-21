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

    private int notified;

    private String pdfLink;

    private String audioLink;

    private String imageLink;


    public Book() {

    }

    public Book(Long id, String title, String author, double price, String namePicture, String category, int stars, String description, int notified, String pdfLink, String audioLink, String imageLink) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.price = price;
        this.namePicture = namePicture;
        this.category = category;
        this.stars = stars;
        this.description = description;
        this.notified = notified;
        this.pdfLink = pdfLink;
        this.audioLink = audioLink;
        this.imageLink = imageLink;
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

    public Book(Long id, String title, String author, String category, double price, String namePicture, int stars, String description, int notified, String pdfLink, String audioLink, String imageLink) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.category = category;
        this.price = price;
        this.namePicture = namePicture;
        this.stars = stars;
        this.description = description;
        this.notified = notified;
        this.pdfLink = pdfLink;
        this.audioLink = audioLink;
        this.imageLink = imageLink;
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

    public int getNotified() {
        return notified;
    }

    public void setNotified(int notified) {
        notified = notified;
    }

    public String getPdfLink() {
        return pdfLink;
    }

    public void setPdfLink(String pdfLink) {
        this.pdfLink = pdfLink;
    }

    public String getAudioLink() {
        return audioLink;
    }

    public void setAudioLink(String audioLink) {
        this.audioLink = audioLink;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }
}
