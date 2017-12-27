package com.example.raluca.storebooksystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ImageView;

import model.Book;

public class BookActivity extends AppCompatActivity {
    private Book book;
    private ImageView imageView;
    private EditText m_editTextBook;
    private int imageNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_activity);
        imageView=(ImageView)findViewById(R.id.imageView);
        m_editTextBook=(EditText)findViewById(R.id.editTextBook);

        Intent intent = getIntent();
        book = (Book) intent.getSerializableExtra("book");

        Bundle bundle = intent.getExtras();

        if(bundle!=null)
        {
            imageNumber =(int) bundle.get("imageNumber");
        }

       // imageNumber=(int)intent.getSerializableExtra("imageNumber");

        m_editTextBook.setText(book.getTitle());

        int resID = getResources().getIdentifier(book.getNamePicture(), "drawable",  getPackageName());
        imageView.setImageResource(resID);

    }
}
