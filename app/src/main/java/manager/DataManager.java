package manager;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import model.Book;
import model.FavoriteBook;
import model.Review;
import model.User;

/**
 * Created by Raluca on 30.10.2017.
 */

public class DataManager {

    private static DataManager instance = new DataManager();

    public static DataManager getInstance() {
        return instance;
    }

    private String baseAuthStr;

    private List<Book> books;

    private List<Book> booksList;

    private List<Review> reviewsList;

    private List<FavoriteBook> favoriteBooksList;

    private DataManager() {
        Log.d("TAG", "DataManager()");
    }

    public DataManager(String baseAuthStr) {
        this.baseAuthStr = baseAuthStr;
    }

    public static void setInstance(DataManager instance) {
        DataManager.instance = instance;
    }

    public String getBaseAuthStr() {
        return baseAuthStr;
    }

    public void setBaseAuthStr(String baseAuthStr) {
        this.baseAuthStr = baseAuthStr;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public List<Book> getBooksList() {
        return booksList;
    }

    public void setBooksList(List<Book> booksList) {
        this.booksList = booksList;
    }

    public List<Review> getReviewsList() {
        return reviewsList;
    }

    public void setReviewsList(List<Review> reviewsList) {
        this.reviewsList = reviewsList;
    }

    public List<FavoriteBook> getFavoriteBooksList() {
        return favoriteBooksList;
    }

    public void setFavoriteBooksList(List<FavoriteBook> favoriteBooksList) {
        this.favoriteBooksList = favoriteBooksList;
    }

    public User parseUser(String inputJSON) {

        User user = new User();

        Book book;
        try {
            JSONObject jsonObject = new JSONObject(inputJSON);
            Log.d("TAG", "jsonObject - " + String.valueOf(jsonObject));

            user = new User(jsonObject.getString("username"), jsonObject.getString("firstName"), jsonObject.getString("lastName"), jsonObject.getString("password"), jsonObject.getString("email"), jsonObject.getString("contactNo"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return user;
    }

    public List<Book> parseBooks(String inputJSON) {

        booksList = new ArrayList<Book>();

        try {
            JSONArray jsonArray = new JSONArray(inputJSON);
            Log.d("TAG", "JSONArray - " + String.valueOf(jsonArray));

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);

                Book book = new Book(jsonObject.getLong("id"), jsonObject.getString("title"), jsonObject.getString("author"), jsonObject.getString("category"), jsonObject.getDouble("price"), jsonObject.getString("namePicture"), jsonObject.getInt("stars"), jsonObject.getString("description"));

                booksList.add(book);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return booksList;
    }

    public List<Review> parseReviews(String inputJSON) {

        reviewsList = new ArrayList<Review>();


        try {
            JSONArray jsonArray = new JSONArray(inputJSON);
            Log.d("TAG", "JSONArray - " + String.valueOf(jsonArray));

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);

                Review review = new Review(jsonObject.getLong("idBook"), jsonObject.getString("username"), jsonObject.getString("textReview"), jsonObject.getInt("starReview"));

                reviewsList.add(review);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return reviewsList;
    }

    public List<FavoriteBook> parseFavoriteBooks(String inputJSON) {

        favoriteBooksList = new ArrayList<FavoriteBook>();

        try {
            JSONArray jsonArray = new JSONArray(inputJSON);
            Log.d("TAG", "JSONArray - " + String.valueOf(jsonArray));

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);

                FavoriteBook favoriteBook = new FavoriteBook(jsonObject.getLong("idBook"),jsonObject.getString("title"),jsonObject.getString("author"),jsonObject.getString("category"), jsonObject.getString("namePicture"), jsonObject.getString("username"));

                favoriteBooksList.add(favoriteBook);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return favoriteBooksList;
    }
}
