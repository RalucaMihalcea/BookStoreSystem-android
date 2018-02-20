package com.example.raluca.storebooksystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import manager.DataManager;
import model.Book;
import model.BookViews;
import model.User;
import webservice.SelectBookByCategoryDelegate;
import webservice.SelectBookByCategoryTask;
import webservice.SelectBookByIdDelegate;
import webservice.SelectBookByIdTask;
import webservice.SelectBookViewsByUsernameDelegate;
import webservice.SelectBookViewsByUsernameTask;

public class HomeActivity extends AppCompatActivity implements SelectBookViewsByUsernameDelegate, SelectBookByIdDelegate, SelectBookByCategoryDelegate {

    private HomeActivity homeActivity;
    private TextView textEdit, text1, text3, title1, title2, title3, title4, author1, author2, author3, author4, textViewDescriptionInterested;
    private CardView m_cardMyFavoriteBooks;
    private User userAfterLogin;
    private CardView m_cardCartAllBooks;
    private CardView m_cardCategory;
    private List<BookViews> bookViewsList = new ArrayList<>();
    private List<Book> booksByCategory = new ArrayList<>();
    private List<Book> randomBookList = new ArrayList<>();
    private BookViews theMostViewedBook;
    private ImageView image_view1, image_view2, image_view3, image_view4;
    private Book book, randomBook;
    private View view1, view2, view3, view4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        homeActivity = this;

        m_cardMyFavoriteBooks = (CardView) findViewById(R.id.cardMyFavoriteBooks);
        m_cardCartAllBooks = (CardView) findViewById(R.id.cardCartAllBooks);
        m_cardCategory = (CardView) findViewById(R.id.cardCategory);
        image_view1 = (ImageView) findViewById(R.id.image_view1);
        image_view2 = (ImageView) findViewById(R.id.image_view2);
        image_view3 = (ImageView) findViewById(R.id.image_view3);
        image_view4 = (ImageView) findViewById(R.id.image_view4);
        text3 = (TextView) findViewById(R.id.text3);
        title1 = (TextView) findViewById(R.id.title1);
        title2 = (TextView) findViewById(R.id.title2);
        title3 = (TextView) findViewById(R.id.title3);
        title4 = (TextView) findViewById(R.id.title4);
        author1 = (TextView) findViewById(R.id.author1);
        author2 = (TextView) findViewById(R.id.author2);
        author3 = (TextView) findViewById(R.id.author3);
        author4 = (TextView) findViewById(R.id.author4);
        textViewDescriptionInterested = (TextView) findViewById(R.id.textViewDescriptionInterested);

        view1 = (View) findViewById(R.id.view1);
        view1 = (View) findViewById(R.id.view1);
        view2 = (View) findViewById(R.id.view2);
        view3 = (View) findViewById(R.id.view3);
        view4 = (View) findViewById(R.id.view4);

        textViewDescriptionInterested.setVisibility(View.INVISIBLE);

        view1.setVisibility(View.INVISIBLE);
        view2.setVisibility(View.INVISIBLE);
        view3.setVisibility(View.INVISIBLE);
        view4.setVisibility(View.INVISIBLE);

        title1.setVisibility(View.INVISIBLE);
        title2.setVisibility(View.INVISIBLE);
        title3.setVisibility(View.INVISIBLE);
        title4.setVisibility(View.INVISIBLE);

        author1.setVisibility(View.INVISIBLE);
        author2.setVisibility(View.INVISIBLE);
        author3.setVisibility(View.INVISIBLE);
        author4.setVisibility(View.INVISIBLE);

        image_view1.setVisibility(View.INVISIBLE);
        image_view2.setVisibility(View.INVISIBLE);
        image_view3.setVisibility(View.INVISIBLE);
        image_view4.setVisibility(View.INVISIBLE);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        userAfterLogin = (User) intent.getSerializableExtra("userAfterLogin");

        SelectBookViewsByUsernameTask selectBookViewsByUsernameTask = new SelectBookViewsByUsernameTask(userAfterLogin.getUsername());
        selectBookViewsByUsernameTask.setSelectBookViewsByUsernameDelegate(homeActivity);


        image_view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, BookActivity.class);
                intent.putExtra("book", (Serializable) randomBookList.get(0));
                //intent.putExtra("imageNumber", imageNumber);
                intent.putExtra("userAfterLogin", userAfterLogin);
                startActivity(intent);

            }
        });

        image_view2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, BookActivity.class);
                intent.putExtra("book", (Serializable) randomBookList.get(1));
                //intent.putExtra("imageNumber", imageNumber);
                intent.putExtra("userAfterLogin", userAfterLogin);
                startActivity(intent);

            }
        });
        image_view3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, BookActivity.class);
                intent.putExtra("book", (Serializable) randomBookList.get(2));
                //intent.putExtra("imageNumber", imageNumber);
                intent.putExtra("userAfterLogin", userAfterLogin);
                startActivity(intent);

            }
        });

        image_view4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, BookActivity.class);
                intent.putExtra("book", (Serializable) randomBookList.get(3));
                //intent.putExtra("imageNumber", imageNumber);
                intent.putExtra("userAfterLogin", userAfterLogin);
                startActivity(intent);

            }
        });

        m_cardMyFavoriteBooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, FavoriteBooksActivity.class);
                intent.putExtra("userAfterLogin", userAfterLogin);
                startActivity(intent);
            }
        });

        m_cardCartAllBooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(HomeActivity.this, AllBooksActivity.class);
//                startActivity(intent);

                Intent intent = new Intent(HomeActivity.this, BooksByCategory.class);
                intent.putExtra("userAfterLogin", userAfterLogin);
                intent.putExtra("category", "All");
                intent.putExtra("nameOfCover", "AllBooks");
                intent.putExtra("titleCover", "ALL BOOKS");
                startActivity(intent);
            }
        });

        m_cardCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent4 = new Intent(HomeActivity.this, CategoryMenu.class);
                intent4.putExtra("userAfterLogin", userAfterLogin);
                startActivity(intent4);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_activity_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.profile_id:
                Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
                intent.putExtra("userAfterLogin", userAfterLogin);
                startActivity(intent);
                return true;

            case R.id.home_id:
                return true;

            case R.id.LogOut_id:
                Intent intent3 = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(intent3);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onSelectBookViewsByUsernameDone(String result) throws UnsupportedEncodingException {

        int max = -1;

        if (!result.equals("[]\n")) {

            textViewDescriptionInterested.setVisibility(View.VISIBLE);

            view1.setVisibility(View.VISIBLE);
            view2.setVisibility(View.VISIBLE);
            view3.setVisibility(View.VISIBLE);
            view4.setVisibility(View.VISIBLE);

            title1.setVisibility(View.VISIBLE);
            title2.setVisibility(View.VISIBLE);
            title3.setVisibility(View.VISIBLE);
            title4.setVisibility(View.VISIBLE);

            author1.setVisibility(View.VISIBLE);
            author2.setVisibility(View.VISIBLE);
            author3.setVisibility(View.VISIBLE);
            author4.setVisibility(View.VISIBLE);

            image_view1.setVisibility(View.VISIBLE);
            image_view2.setVisibility(View.VISIBLE);
            image_view3.setVisibility(View.VISIBLE);
            image_view4.setVisibility(View.VISIBLE);

            bookViewsList = DataManager.getInstance().parseBookViewsList(result);

            for (BookViews bookViews : bookViewsList)
                if (bookViews.getViews() > max) {
                    max = bookViews.getViews();
                    theMostViewedBook = bookViews;
                }

            SelectBookByIdTask selectBookByIdTask = new SelectBookByIdTask(theMostViewedBook.getIdBook());
            selectBookByIdTask.setSelectBookByIdDelegate(homeActivity);
        }
    }

    @Override
    public void onSelectBookByIdDone(String result) throws UnsupportedEncodingException {

        if (!result.equals("")) {
            book = DataManager.getInstance().parseBook(result);
            SelectBookByCategoryTask selectBookByCategoryTask = new SelectBookByCategoryTask(book.getCategory());
            selectBookByCategoryTask.setSelectBookByCategoryDelegate(homeActivity);
        }
    }


    @Override
    public void onSelectBookByCategoryDone(String result) throws UnsupportedEncodingException {

        if (!result.isEmpty() && !result.equals("[]\n")) {

            booksByCategory = DataManager.getInstance().parseBooks(result);

            HomeActivity obj = new HomeActivity();
            for (int i = 0; i < 4; i++) {
                //System.out.println(obj.getRandomList(booksByCategory));
                randomBook = obj.getRandomList(booksByCategory);

                if (isDifferentBook(book, randomBook) && isValid(randomBookList, randomBook)) {
                    //Toast.makeText(getApplicationContext(), "Random book is: " + randomBook, Toast.LENGTH_SHORT).show();
                    randomBookList.add(randomBook);
                } else i--;
            }

            int resID = getResources().getIdentifier(randomBookList.get(0).getNamePicture(), "drawable", this.getPackageName());
            image_view1.setImageResource(resID);
            title1.setText(randomBookList.get(0).getTitle());
            author1.setText(randomBookList.get(0).getAuthor());

            int resID2 = getResources().getIdentifier(randomBookList.get(1).getNamePicture(), "drawable", this.getPackageName());
            image_view2.setImageResource(resID2);
            title2.setText(randomBookList.get(1).getTitle());
            author2.setText(randomBookList.get(1).getAuthor());

            int resID3 = getResources().getIdentifier(randomBookList.get(2).getNamePicture(), "drawable", this.getPackageName());
            image_view3.setImageResource(resID3);
            title3.setText(randomBookList.get(2).getTitle());
            author3.setText(randomBookList.get(2).getAuthor());

            int resID4 = getResources().getIdentifier(randomBookList.get(3).getNamePicture(), "drawable", this.getPackageName());
            image_view4.setImageResource(resID4);
            title4.setText(randomBookList.get(3).getTitle());
            author4.setText(randomBookList.get(3).getAuthor());

            text3.setText(book.getTitle());
        }
    }

    public Book getRandomList(List<Book> list) {

        int index = ThreadLocalRandom.current().nextInt(list.size());
        return list.get(index);
    }

    public Boolean isValid(List<Book>randomBookList, Book randomBook)
    {
        if(!randomBookList.contains(randomBook))
            return true;
        return false;
    }

    public Boolean isDifferentBook(Book book, Book randomBook)
    {
        if(book.getTitle().toString()!=randomBook.getTitle().toString() && book.getAuthor().toString()!=randomBook.getAuthor().toString())
            return true;
        return false;
    }
}
