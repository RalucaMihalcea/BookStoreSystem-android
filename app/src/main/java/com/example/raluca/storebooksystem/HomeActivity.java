package com.example.raluca.storebooksystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import model.User;

public class HomeActivity extends AppCompatActivity {
    private TextView textEdit;
    //private Button m_buttonFoodDiary;
//    private Button m_buttonFoodSuggestion;
//    private Button m_buttonConsulting;
//    private Button m_buttonStartWalking;
    private CardView m_cardMyFavoriteBooks;
    //    private Button m_buttonML;
    private User userAfterLogin;
    private CardView m_cardCartShopping;
    private CardView m_cardCategory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        m_cardMyFavoriteBooks = (CardView) findViewById(R.id.cardMyFavoriteBooks);
        m_cardCartShopping = (CardView) findViewById(R.id.cardCartShopping);
        m_cardCategory = (CardView) findViewById(R.id.cardCategory);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        //if (bundle != null) {
        //String username = (String) bundle.get("username");
        //User userAfterLogin= (User) bundle.get("userAfterLogin");
        userAfterLogin = (User) intent.getSerializableExtra("userAfterLogin");

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
                //Toast.makeText(getApplicationContext(), "Am intrat in actiune", Toast.LENGTH_SHORT).show();
            }
        });

        m_cardCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Intent intent4 = new Intent(HomeActivity.this, BooksByCategory.class);
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
                //Toast.makeText(getApplicationContext(), "Profile icon is selected", Toast.LENGTH_SHORT).show();
                //Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
                Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
                intent.putExtra("userAfterLogin", userAfterLogin);
                startActivity(intent);
                //startActivity(intent);
                return true;
            case R.id.home_id:
                //Toast.makeText(getApplicationContext(), "Profile icon is selected", Toast.LENGTH_SHORT).show();
                //Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
//                Intent intent2 = new Intent(HomeActivity.this, ProfileActivity.class);
//                intent2.putExtra("userAfterLogin", userAfterLogin);
//                startActivity(intent2);
                //startActivity(intent);
                return true;

            case R.id.LogOut_id:
                //Toast.makeText(getApplicationContext(), "Log Out icon is selected", Toast.LENGTH_SHORT).show();
                Intent intent3 = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(intent3);
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }

    }
}
