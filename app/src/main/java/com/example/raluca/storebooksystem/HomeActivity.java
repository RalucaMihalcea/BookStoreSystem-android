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

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import manager.DataManager;
import model.Book;
import model.BookViews;
import model.User;
import webservice.SelectBookByIdDelegate;
import webservice.SelectBookByIdTask;
import webservice.SelectBookViewsByUsernameDelegate;
import webservice.SelectBookViewsByUsernameTask;

public class HomeActivity extends AppCompatActivity implements SelectBookViewsByUsernameDelegate, SelectBookByIdDelegate {

    private HomeActivity homeActivity;
    private TextView textEdit, text1, text3;
    private CardView m_cardMyFavoriteBooks;
    private User userAfterLogin;
    private CardView m_cardCartShopping;
    private CardView m_cardCategory;
    private List<BookViews>bookViewsList= new ArrayList<>();
    private BookViews theMostViewedBook;
    private ImageView m_imageViewBanner;
    private Book book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        homeActivity=this;

        m_cardMyFavoriteBooks = (CardView) findViewById(R.id.cardMyFavoriteBooks);
        m_cardCartShopping = (CardView) findViewById(R.id.cardCartShopping);
        m_cardCategory = (CardView) findViewById(R.id.cardCategory);
       // m_imageViewBanner=(ImageView)findViewById(R.id.imageViewBanner);
        //text1=(TextView)findViewById(R.id.text1);
        text3=(TextView)findViewById(R.id.text3);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        userAfterLogin = (User) intent.getSerializableExtra("userAfterLogin");

        SelectBookViewsByUsernameTask selectBookViewsByUsernameTask = new SelectBookViewsByUsernameTask(userAfterLogin.getUsername());
        selectBookViewsByUsernameTask.setSelectBookViewsByUsernameDelegate(homeActivity);

        m_cardMyFavoriteBooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, FavoriteBooksActivity.class);
                intent.putExtra("userAfterLogin", userAfterLogin);
                startActivity(intent);
            }
        });

        m_cardCartShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ShoppingActivity.class);
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

        int max=-1;

        if( !result.equals("[]\n"))
        {
            bookViewsList = DataManager.getInstance().parseBookViewsList(result);

            for (BookViews bookViews:bookViewsList)
                if(bookViews.getViews()>max)
                {
                    max=bookViews.getViews();
                    theMostViewedBook=bookViews;
                }

            SelectBookByIdTask selectBookByIdTask = new SelectBookByIdTask(theMostViewedBook.getIdBook());
            selectBookByIdTask.setSelectBookByIdDelegate(homeActivity);
        }
    }

    @Override
    public void onSelectBookByIdDone(String result) throws UnsupportedEncodingException {

        if( !result.equals(""))
        {
            book = DataManager.getInstance().parseBook(result);

            //int resID = getResources().getIdentifier(book.getNamePicture(), "drawable", this.getPackageName());
            //m_imageViewBanner.setImageResource(resID);

            text3.setText(book.getTitle());
        }
    }




}
