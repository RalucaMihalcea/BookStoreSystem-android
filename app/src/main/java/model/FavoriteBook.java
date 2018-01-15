package model;

/**
 * Created by Raluca on 14.01.2018.
 */

public class FavoriteBook {

    private Long id;
    private Long idBook;
    private Long idUser;

    public FavoriteBook() {
    }

    public FavoriteBook(Long id, Long idBook, Long idUser) {
        this.id = id;
        this.idBook = idBook;
        this.idUser = idUser;
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

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }
}
