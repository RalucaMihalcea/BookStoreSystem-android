package com.example.raluca.storebooksystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;

import model.Book;
import model.User;
import webservice.AddReviewDelegate;
import webservice.AddReviewTask;

public class AddReviewActivity extends AppCompatActivity implements AddReviewDelegate {

    private AddReviewActivity addReviewActivity;
    private User userAfterLogin;
    private Book book;
    private TextView m_titleBookForReview;
    private TextView m_textReview;
    private TextView m_textViewNote;
    private Button addReviewButton;
    private RatingBar ratingBar;
    private ImageView stea1, stea2, stea3, stea4, stea5;
    private int starNumber;
    private EditText m_editText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review);

        //addListenerOnButton();

        m_titleBookForReview = (TextView) findViewById(R.id.titleBookForReview);
       // m_textReview = (TextView) findViewById(R.id.textReview);
        m_textViewNote = (TextView) findViewById(R.id.textViewNote);
        addReviewButton = (Button) findViewById(R.id.AddReview);
        m_editText=(EditText)findViewById(R.id.editText);
       // ratingBar = (RatingBar) findViewById(R.id.ra);

        stea1 = (ImageView) findViewById(R.id.stea1);
        stea2 = (ImageView) findViewById(R.id.stea2);
        stea3 = (ImageView) findViewById(R.id.stea3);
        stea4 = (ImageView) findViewById(R.id.stea4);
        stea5 = (ImageView) findViewById(R.id.stea5);

        Intent intent = getIntent();
        book = (Book) intent.getSerializableExtra("book");
        userAfterLogin = (User) intent.getSerializableExtra("userAfterLogin");

        m_titleBookForReview.setText(book.getTitle() + " - " + book.getAuthor());


//        m_textReview.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                m_textReview.setCursorVisible(true);
//                m_textReview.setFocusableInTouchMode(true);
//                m_textReview.setInputType(InputType.TYPE_CLASS_TEXT);
//                m_textReview.requestFocus();
//
//            }
//        });

        stea1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                m_textViewNote.setText("Not recommend");
                stea1.setImageResource(R.drawable.iconfull);
                stea2.setImageResource(R.drawable.iconstar);
                stea3.setImageResource(R.drawable.iconstar);
                stea4.setImageResource(R.drawable.iconstar);
                stea5.setImageResource(R.drawable.iconstar);
                starNumber = 1;
            }
        });

        stea2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                m_textViewNote.setText("Weak");
                stea1.setImageResource(R.drawable.iconfull);
                stea2.setImageResource(R.drawable.iconfull);
                stea3.setImageResource(R.drawable.iconstar);
                stea4.setImageResource(R.drawable.iconstar);
                stea5.setImageResource(R.drawable.iconstar);
                starNumber = 2;
            }
        });

        stea3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                m_textViewNote.setText("Acceptable");
                stea1.setImageResource(R.drawable.iconfull);
                stea2.setImageResource(R.drawable.iconfull);
                stea3.setImageResource(R.drawable.iconfull);
                stea4.setImageResource(R.drawable.iconstar);
                stea5.setImageResource(R.drawable.iconstar);
                starNumber = 3;
            }
        });

        stea4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                m_textViewNote.setText("Good");
                stea1.setImageResource(R.drawable.iconfull);
                stea2.setImageResource(R.drawable.iconfull);
                stea3.setImageResource(R.drawable.iconfull);
                stea4.setImageResource(R.drawable.iconfull);
                stea5.setImageResource(R.drawable.iconstar);
                starNumber = 4;
            }
        });

        stea5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                m_textViewNote.setText("Excellent");
                stea1.setImageResource(R.drawable.iconfull);
                stea2.setImageResource(R.drawable.iconfull);
                stea3.setImageResource(R.drawable.iconfull);
                stea4.setImageResource(R.drawable.iconfull);
                stea5.setImageResource(R.drawable.iconfull);
                starNumber = 5;
            }
        });

        addReviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              //  Toast.makeText(AddReviewActivity.this, m_editText.getText().toString(), Toast.LENGTH_SHORT).show();
                AddReviewTask addReviewTask = new AddReviewTask(book.getId(), userAfterLogin.getUsername(), m_editText.getText().toString(),starNumber);
                addReviewTask.setAddReviewDelegate(addReviewActivity);
            }
        });

//        ratingBar.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (event.getAction() == MotionEvent.ACTION_UP) {
//                    m_textViewNote.setText(ratingBar.getRating()+"hdsdsdh");
//                }
//                return true;
//            }});
    }


    @Override
    public void onAddReviewDone(String result) throws UnsupportedEncodingException {

    }

    @Override
    public void onAddReviewError(String response) {

    }
}
