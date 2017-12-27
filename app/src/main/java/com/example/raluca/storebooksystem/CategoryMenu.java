package com.example.raluca.storebooksystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;

import model.User;

public class CategoryMenu extends AppCompatActivity {

    private CardView m_cardViewNonfiction;
    private CardView m_cardViewFiction;
    private CardView m_cardViewLove;
    private CardView m_cardViewLiterature;
    private CardView m_cardViewDrama;
    private CardView m_cardViewPsychology;
    private CardView m_cardViewAction;
    private CardView m_cardViewComedy;
    private CardView m_cardViewChildren;
    private User userAfterLogin;
    //private List<String> nameOfCovers=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_menu);

        //nameOfCovers.add("before_i_fall");
        //nameOfCovers.add("allegiant");
        Intent intent = getIntent();
        userAfterLogin = (User) intent.getSerializableExtra("userAfterLogin");


        m_cardViewNonfiction=(CardView)findViewById(R.id.cardViewNonfiction);
        m_cardViewFiction=(CardView)findViewById(R.id.cardViewFiction);
        m_cardViewLove=(CardView)findViewById(R.id.cardViewLove);
        m_cardViewLiterature=(CardView)findViewById(R.id.cardViewLiterature);
        m_cardViewDrama=(CardView)findViewById(R.id.cardViewDrama);
        m_cardViewPsychology=(CardView)findViewById(R.id.cardViewPsychology);
        m_cardViewAction=(CardView)findViewById(R.id.cardViewAction);
        m_cardViewComedy=(CardView)findViewById(R.id.cardViewComedy);
        m_cardViewChildren=(CardView)findViewById(R.id.cardViewChildren);


        m_cardViewNonfiction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryMenu.this, BooksByCategory.class);
                intent.putExtra("userAfterLogin", userAfterLogin);
                intent.putExtra("category", "Nonfiction");
                intent.putExtra("nameOfCover", "nonfiction");
                intent.putExtra("titleCover", "NONFICTION");
                startActivity(intent);
            }
        });
        m_cardViewFiction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryMenu.this, BooksByCategory.class);
                intent.putExtra("userAfterLogin", userAfterLogin);
                intent.putExtra("category", "Fiction");
                intent.putExtra("nameOfCover", "fiction");
                intent.putExtra("titleCover", "FICTION");
                startActivity(intent);
            }
        });
        m_cardViewLove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryMenu.this, BooksByCategory.class);
                intent.putExtra("userAfterLogin", userAfterLogin);
                intent.putExtra("category", "Love");
                intent.putExtra("nameOfCover", "love");
                intent.putExtra("titleCover", "LOVE & ROMANCE");
                startActivity(intent);
            }
        });
        m_cardViewLiterature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryMenu.this, BooksByCategory.class);
                intent.putExtra("userAfterLogin", userAfterLogin);
                intent.putExtra("category", "Literature");
                intent.putExtra("nameOfCover", "literature");
                intent.putExtra("titleCover", "LITERATURE");
                startActivity(intent);
            }
        });
        m_cardViewDrama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryMenu.this, BooksByCategory.class);
                intent.putExtra("userAfterLogin", userAfterLogin);
                intent.putExtra("category", "Drama");
                intent.putExtra("nameOfCover", "drama");
                intent.putExtra("titleCover", "DRAMA");
                startActivity(intent);
            }
        });
        m_cardViewPsychology.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryMenu.this, BooksByCategory.class);
                intent.putExtra("userAfterLogin", userAfterLogin);
                intent.putExtra("category", "Psychology");
                intent.putExtra("nameOfCover", "psychology");
                intent.putExtra("titleCover", "PSYCHOLOGY");
                startActivity(intent);
            }
        });
        m_cardViewAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryMenu.this, BooksByCategory.class);
                intent.putExtra("userAfterLogin", userAfterLogin);
                intent.putExtra("category", "Action");
                intent.putExtra("nameOfCover", "action");
                intent.putExtra("titleCover", "ACTION & ADVENTURE");
                startActivity(intent);
            }
        });
        m_cardViewComedy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryMenu.this, BooksByCategory.class);
                intent.putExtra("userAfterLogin", userAfterLogin);
                intent.putExtra("category", "Comedy");
                intent.putExtra("nameOfCover", "comedy");
                intent.putExtra("titleCover", "COMEDY");
                startActivity(intent);
            }
        });
        m_cardViewChildren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryMenu.this, BooksByCategory.class);
                intent.putExtra("userAfterLogin", userAfterLogin);
                intent.putExtra("category", "Children");
                intent.putExtra("nameOfCover", "children");
                intent.putExtra("titleCover", "CHILDREN");
                startActivity(intent);
            }
        });



    }
}