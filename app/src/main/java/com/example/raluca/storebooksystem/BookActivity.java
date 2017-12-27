package com.example.raluca.storebooksystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import model.Book;

public class BookActivity extends AppCompatActivity {
    private Book book;
    private ImageView imageView;
    private TextView m_textViewTitle;
    private TextView m_textViewAuthor;
    private int imageNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_activity);
        imageView = (ImageView) findViewById(R.id.imageView);
        m_textViewTitle = (TextView) findViewById(R.id.textViewTitle);
        m_textViewAuthor = (TextView) findViewById(R.id.textViewAuthor);

        Intent intent = getIntent();
        book = (Book) intent.getSerializableExtra("book");

        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            imageNumber = (int) bundle.get("imageNumber");
        }

        // imageNumber=(int)intent.getSerializableExtra("imageNumber");

         m_textViewTitle.setText(book.getTitle());
        m_textViewAuthor.setText(book.getAuthor());

        int resID = getResources().getIdentifier(book.getNamePicture(), "drawable", getPackageName());
        imageView.setImageResource(resID);

    }
}
