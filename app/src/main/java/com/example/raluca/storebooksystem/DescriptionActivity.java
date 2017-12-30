package com.example.raluca.storebooksystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import model.Book;

public class DescriptionActivity extends AppCompatActivity {
    private TextView m_descriptionText;
    private Book book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        Intent intent = getIntent();
        book = (Book) intent.getSerializableExtra("book");

        m_descriptionText = (TextView) findViewById(R.id.descriptionText);
        m_descriptionText.setText(book.getDescription());
    }
}
