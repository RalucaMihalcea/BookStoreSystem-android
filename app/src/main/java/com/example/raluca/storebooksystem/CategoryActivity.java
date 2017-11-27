package com.example.raluca.storebooksystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.util.List;

import manager.DataManager;
import model.Book;
import webservice.SelectBookByCategoryDelegate;
import webservice.SelectBookByCategoryTask;
import webservice.SelectBookDelegate;

public class CategoryActivity extends AppCompatActivity implements SelectBookDelegate, SelectBookByCategoryDelegate {
    private CardView cardView1;
    private CardView cardView2;
    private CardView cardView3;
    private TextView textView;
    private String str;
    private List<Book> books;
    private List<Book> booksByCategory;
    private CategoryActivity categoryActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        categoryActivity=this;
        cardView1 = (CardView) findViewById(R.id.cardView1);
        cardView2 = (CardView) findViewById(R.id.cardView2);
        cardView3 = (CardView) findViewById(R.id.cardView3);
        textView = (TextView) findViewById(R.id.textView);

        SelectBookByCategoryTask selectBookByCategoryTask = new SelectBookByCategoryTask("SF/Fantasy");
        selectBookByCategoryTask.setSelectBookByCategoryDelegate(categoryActivity);


//        cardView1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(
// MainActivity.this, BookActivity.class);
//                startActivity(intent);
//
//                //Toast.makeText(getApplicationContext(), "Am intrat in actiune", Toast.LENGTH_SHORT).show();
//            }
//        });


    }
//    private View.OnClickListener onClickListener = new View.OnClickListener() {
//
//        @Override
//        public void onClick(final View v) {
//            switch(v.getId()){
//                case R.id.cardView1:
//                    //DO something
//                    Intent intent = new Intent(MainActivity.this, BookActivity.class);
//                    startActivity(intent);
//                    break;
//                case R.id.cardView2:
//                    //DO something
//                    Intent intent2 = new Intent(MainActivity.this, BookActivity.class);
//                    startActivity(intent2);
//                    break;
//            }
//
//        }
//    };


    public void cardClick(View v) {
        //textView.setText("NNNNN");
        switch (v.getId()) {
            case R.id.cardView1:
                //DO something
                str = "cardView1";
                break;
            case R.id.cardView2:
                //DO something
                str = "cardView2";
                break;
            case R.id.cardView3:
                //DO something
                str = "cardView3";
                break;
        }

        Intent intent = new Intent(CategoryActivity.this, BookActivity.class);
        intent.putExtra("str", str);
        startActivity(intent);
    }


    @Override
    public void onSelectBookDone(String result) throws UnsupportedEncodingException {
        if (!result.isEmpty()) {
            books = DataManager.getInstance().parseBooks(result);
            DataManager.getInstance().setBooksList(books);

//            String baseAuthStr = username + ":" + password;
//            String str = "Basic " + Base64.encodeToString(baseAuthStr.getBytes("UTF-8"), Base64.DEFAULT);
//            DataManager.getInstance().setBaseAuthStr(str);

            // putNameBooksInVector();
            Toast.makeText(getApplicationContext(), "Get all books from database", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onSelectBookByCategoryDone(String result) throws UnsupportedEncodingException {
        if (!result.isEmpty()) {
            booksByCategory = DataManager.getInstance().parseBooks(result);
            DataManager.getInstance().setBooksList(booksByCategory);


//            String baseAuthStr = username + ":" + password;
//            String str = "Basic " + Base64.encodeToString(baseAuthStr.getBytes("UTF-8"), Base64.DEFAULT);
//            DataManager.getInstance().setBaseAuthStr(str);

            // putNameBooksInVector();
            Toast.makeText(getApplicationContext(), "Get all books by category from database", Toast.LENGTH_SHORT).show();
        }

    }
}

