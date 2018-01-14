package model;

/**
 * Created by Raluca on 14.01.2018.
 */

public class FavoriteBook {

    private Long id;
    private Long idBook;
    private String title;
    private String author;
    private String category;
    private String namePicture;
    private String username;

    public FavoriteBook() {
    }

    public FavoriteBook(Long idBook, String title, String author, String category, String namePicture, String username) {
        this.idBook = idBook;
        this.title = title;
        this.author = author;
        this.category = category;
        this.namePicture = namePicture;
        this.username = username;
    }

    public FavoriteBook(Long id, Long idBook, String title, String author, String category, String namePicture, String username) {
        this.id = id;
        this.idBook = idBook;
        this.title = title;
        this.author = author;
        this.category = category;
        this.namePicture = namePicture;
        this.username = username;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getNamePicture() {
        return namePicture;
    }

    public void setNamePicture(String namePicture) {
        this.namePicture = namePicture;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
