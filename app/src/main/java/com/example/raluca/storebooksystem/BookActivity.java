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
    private int numberOfStars;
    private ImageView star1, star2, star3, star4, star5;
    //private Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_activity);
        imageView = (ImageView) findViewById(R.id.imageView);
        m_textViewTitle = (TextView) findViewById(R.id.textViewTitle);
        m_textViewAuthor = (TextView) findViewById(R.id.textViewAuthor);
        star1 = (ImageView) findViewById(R.id.star1);
        star2 = (ImageView) findViewById(R.id.star2);
        star3 = (ImageView) findViewById(R.id.star3);
        star4 = (ImageView) findViewById(R.id.star4);
        star5 = (ImageView) findViewById(R.id.star5);

        // button=(Button)findViewById(R.id.buttonOrange);

        Intent intent = getIntent();
        book = (Book) intent.getSerializableExtra("book");
        numberOfStars = book.getStars();

        Bundle bundle = intent.getExtras();

//        if (bundle != null) {
//            imageNumber = (int) bundle.get("imageNumber");
//        }

        // imageNumber=(int)intent.getSerializableExtra("imageNumber");

        m_textViewTitle.setText(book.getTitle());
        m_textViewAuthor.setText(book.getAuthor());
        //Toast.makeText(this, "Hellllo "+book.getStars(), Toast.LENGTH_SHORT).show();


        int resID = getResources().getIdentifier(book.getNamePicture(), "drawable", getPackageName());
        imageView.setImageResource(resID);

        switch (numberOfStars) {
            case 1:
                star1.setImageResource(R.drawable.iconstar);

                return;
            case 2:
                star1.setImageResource(R.drawable.iconstar);
                star2.setImageResource(R.drawable.iconstar);

                return;
            case 3:
                star1.setImageResource(R.drawable.iconstar);
                star2.setImageResource(R.drawable.iconstar);
                star3.setImageResource(R.drawable.iconstar);

                return;
            case 4:
                star1.setImageResource(R.drawable.iconstar);
                star2.setImageResource(R.drawable.iconstar);
                star3.setImageResource(R.drawable.iconstar);
                star4.setImageResource(R.drawable.iconstar);

                return;
            case 5:
                star1.setImageResource(R.drawable.iconstar);
                star2.setImageResource(R.drawable.iconstar);
                star3.setImageResource(R.drawable.iconstar);
                star4.setImageResource(R.drawable.iconstar);
                star5.setImageResource(R.drawable.iconstar);

                return;
            default:

                return;

        }

    }
}
