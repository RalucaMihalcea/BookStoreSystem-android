package com.example.raluca.storebooksystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import manager.DataManager;
import model.Book;
import model.BookViewsAndDate;
import model.Review;
import model.User;
import webservice.AddBookViewsAndDateDelegate;
import webservice.AddBookViewsAndDateTask;
import webservice.AddBookViewsDelegate;
import webservice.AddBookViewsTask;
import webservice.SelectBookViewsAndDateDelegate;
import webservice.SelectBookViewsAndDateTask;
import webservice.SelectBookViewsDelegate;
import webservice.SelectReviewsByIdBookDelegate;
import webservice.SelectReviewsByIdBookTask;
import webservice.UpdateBookViewsAndDateDelegate;
import webservice.UpdateBookViewsAndDateTask;
import webservice.UpdateBookViewsDelegate;
import webservice.UpdateBookViewsTask;

public class BookActivity extends AppCompatActivity implements SelectReviewsByIdBookDelegate, SelectBookViewsDelegate, SelectBookViewsAndDateDelegate, AddBookViewsDelegate, AddBookViewsAndDateDelegate, UpdateBookViewsDelegate, UpdateBookViewsAndDateDelegate {
    private BookActivity bookActivity;
    private Book book;
    private ImageView imageView;
    private TextView m_textViewTitle;
    private TextView m_textViewAuthor;
    private TextView m_textViewDescription;
    private TextView m_textViewShowMore;
    private TextView m_ViewAllReviewsTextView;
    private int numberOfStars;
    private Long idBook;
    private List<Review> reviews = new ArrayList<>();
    private List<String> reviewsWithNameOfUsers = new ArrayList<>();
    private ImageView star1, star2, star3, star4, star5, star1Review1, star2Review1, star3Review1, star4Review1, star5Review1;
    private ImageView star1Review2, star2Review2, star3Review2, star4Review2, star5Review2, star1Review3, star2Review3, star3Review3, star4Review3, star5Review3;
    private TextView textReview1, textReview2, textReview3, textReviewUser1, textReviewUser2, textReviewUser3;
    //ListView listView;
    private Button m_buttonAddReview, m_buttonRead;
    private User userAfterLogin;
    private Boolean reviewSent;
    private BookViewsAndDate bookViewsAndDate;

    private String calendarString;
    private int number;
    private int count;
    private int month;
    private int monthToday;
    private String auxString;
    private static final String TAG = "BookActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bookActivity = this;

        setContentView(R.layout.activity_book_activity);

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        calendarString = df.format(c.getTime());

        DateFormat df2 = new SimpleDateFormat("dd-MM-yyyy");
        Calendar calendarDate = Calendar.getInstance();
        try {
            calendarDate.setTime(df2.parse(calendarString));
            monthToday = calendarDate.get(Calendar.MONTH) + 1;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        imageView = (ImageView) findViewById(R.id.imageView);
        m_textViewTitle = (TextView) findViewById(R.id.textViewTitle);
        m_textViewAuthor = (TextView) findViewById(R.id.textViewAuthor);
        m_textViewDescription = (TextView) findViewById(R.id.textViewDescription);
        m_textViewShowMore = (TextView) findViewById(R.id.textViewShowMore);
        m_ViewAllReviewsTextView = (TextView) findViewById(R.id.ViewAllReviewsTextView);

        textReview1 = (TextView) findViewById(R.id.textReview1);
        textReview2 = (TextView) findViewById(R.id.textReview2);
        textReview3 = (TextView) findViewById(R.id.textReview3);

        textReviewUser1 = (TextView) findViewById(R.id.textReviewUser1);
        textReviewUser2 = (TextView) findViewById(R.id.textReviewUser2);
        textReviewUser3 = (TextView) findViewById(R.id.textReviewUser3);
        //listView = (ListView) findViewById(R.id.list);
        star1 = (ImageView) findViewById(R.id.star1);
        star2 = (ImageView) findViewById(R.id.star2);
        star3 = (ImageView) findViewById(R.id.star3);
        star4 = (ImageView) findViewById(R.id.star4);
        star5 = (ImageView) findViewById(R.id.star5);

        star1Review1 = (ImageView) findViewById(R.id.star1Review1);
        star2Review1 = (ImageView) findViewById(R.id.star2Review1);
        star3Review1 = (ImageView) findViewById(R.id.star3Review1);
        star4Review1 = (ImageView) findViewById(R.id.star4Review1);
        star5Review1 = (ImageView) findViewById(R.id.star5Review1);

        star1Review2 = (ImageView) findViewById(R.id.star1Review2);
        star2Review2 = (ImageView) findViewById(R.id.star2Review2);
        star3Review2 = (ImageView) findViewById(R.id.star3Review2);
        star4Review2 = (ImageView) findViewById(R.id.star4Review2);
        star5Review2 = (ImageView) findViewById(R.id.star5Review2);

        star1Review3 = (ImageView) findViewById(R.id.star1Review3);
        star2Review3 = (ImageView) findViewById(R.id.star2Review3);
        star3Review3 = (ImageView) findViewById(R.id.star3Review3);
        star4Review3 = (ImageView) findViewById(R.id.star4Review3);
        star5Review3 = (ImageView) findViewById(R.id.star5Review3);

        m_buttonAddReview = (Button) findViewById(R.id.buttonAddReview);
        m_buttonRead = (Button) findViewById(R.id.buttonRead);
        m_buttonRead.setVisibility(View.INVISIBLE);
        m_buttonAddReview.setVisibility(View.INVISIBLE);

        Intent intent = getIntent();
        reviewSent = (Boolean) intent.getSerializableExtra("reviewSent");
        book = (Book) intent.getSerializableExtra("book");
        numberOfStars = book.getStars();
        idBook = book.getId();
        userAfterLogin = (User) intent.getSerializableExtra("userAfterLogin");

        if (!book.getPdfLink().equals(""))
            m_buttonRead.setVisibility(View.VISIBLE);

        Log.i(TAG, "SelectBookViewsAndDate task by idBook: " + idBook + ", username" + userAfterLogin.getUsername());
        SelectBookViewsAndDateTask selectBookViewsAndDateTask = new SelectBookViewsAndDateTask(idBook, userAfterLogin.getUsername());
        selectBookViewsAndDateTask.setSelectBookViewsAndDateDelegate(bookActivity);

        if (reviewSent == null || !reviewSent.equals(Boolean.TRUE))
            m_buttonAddReview.setVisibility(View.VISIBLE);

        Bundle bundle = intent.getExtras();

//        if (bundle != null) {
//            imageNumber = (int) bundle.get("imageNumber");
//        }

        // imageNumber=(int)intent.getSerializableExtra("imageNumber");

        m_textViewTitle.setText(book.getTitle());
        m_textViewAuthor.setText(book.getAuthor());

        if (!book.getDescription().isEmpty())
            m_textViewDescription.setText(book.getDescription());
        //Toast.makeText(this, "Hellllo "+book.getStars(), Toast.LENGTH_SHORT).show();


        //int resID = this.getResources().getIdentifier(book.getNamePicture(), "drawable", this.getPackageName());
        //imageView.setImageResource(resID);


        //Glide.with(this).load("http://www.rounite.com/wp-content/uploads/2010/01/eminescu1.png").into(imageView);
        //https://drive.google.com/file/d/1tjZnUmkGOIVL9Vh4guvjvRnh8cxS0sa6/view?usp=sharing

        auxString = book.getImageLink();
        Glide.with(this).load("https://docs.google.com/uc?export=download&id=" + auxString).into(imageView);

        Log.i(TAG, "SelectReviewsByIdBook task by idBook: " + idBook);
        SelectReviewsByIdBookTask selectReviewsByIdBookTask = new SelectReviewsByIdBookTask(idBook);
        selectReviewsByIdBookTask.setSelectReviewsByIdBookDelegate(bookActivity);

        m_buttonAddReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookActivity.this, AddReviewActivity.class);
                intent.putExtra("book", book);
                intent.putExtra("userAfterLogin", userAfterLogin);
                startActivity(intent);

            }
        });

        m_buttonRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookActivity.this, PdfViewer.class);
                intent.putExtra("book", book);
                startActivity(intent);

            }
        });

        m_textViewShowMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookActivity.this, DescriptionActivity.class);
                intent.putExtra("book", book);
                startActivity(intent);

            }
        });

        m_ViewAllReviewsTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intenttt = new Intent(BookActivity.this, ReviewsActivity.class);
                intenttt.putExtra("reviews", (Serializable) reviews);
                startActivity(intenttt);
            }
        });


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

    @Override
    public void onSelectReviewsByIdBookDone(String result) throws UnsupportedEncodingException {

        if (!result.equals("[]\n")) {
            reviews = DataManager.getInstance().parseReviews(result);
            DataManager.getInstance().setReviewsList(reviews);

            //if (!reviews.isEmpty())
            for (Review review : reviews) {
                if (review.getUsername().equals(userAfterLogin.getUsername())) {
                    m_buttonAddReview.setVisibility(View.INVISIBLE);
                    break;
                }
            }

            if (reviews.get(0) != null) {

                textReviewUser1.setText(reviews.get(0).getUsername());
                textReview1.setText(reviews.get(0).getTextReview());

                switch (reviews.get(0).getStarReview()) {
                    case 1:
                        star1Review1.setImageResource(R.drawable.iconstar);

                        break;
                    case 2:
                        star1Review1.setImageResource(R.drawable.iconstar);
                        star2Review1.setImageResource(R.drawable.iconstar);

                        return;
                    case 3:
                        star1Review1.setImageResource(R.drawable.iconstar);
                        star2Review1.setImageResource(R.drawable.iconstar);
                        star3Review1.setImageResource(R.drawable.iconstar);

                        break;
                    case 4:
                        star1Review1.setImageResource(R.drawable.iconstar);
                        star2Review1.setImageResource(R.drawable.iconstar);
                        star3Review1.setImageResource(R.drawable.iconstar);
                        star4Review1.setImageResource(R.drawable.iconstar);

                        break;
                    case 5:
                        star1Review1.setImageResource(R.drawable.iconstar);
                        star2Review1.setImageResource(R.drawable.iconstar);
                        star3Review1.setImageResource(R.drawable.iconstar);
                        star4Review1.setImageResource(R.drawable.iconstar);
                        star5Review1.setImageResource(R.drawable.iconstar);

                        break;
                    default:

                        break;
                }
            }

            if (reviews.size() > 1 && reviews.get(1) != null) {

                textReviewUser2.setText(reviews.get(1).getUsername());
                textReview2.setText(reviews.get(1).getTextReview());

                switch (reviews.get(1).getStarReview()) {
                    case 1:
                        star1Review2.setImageResource(R.drawable.iconstar);

                        break;
                    case 2:
                        star1Review2.setImageResource(R.drawable.iconstar);
                        star2Review2.setImageResource(R.drawable.iconstar);

                        break;
                    case 3:
                        star1Review2.setImageResource(R.drawable.iconstar);
                        star2Review2.setImageResource(R.drawable.iconstar);
                        star3Review2.setImageResource(R.drawable.iconstar);

                        break;
                    case 4:
                        star1Review2.setImageResource(R.drawable.iconstar);
                        star2Review2.setImageResource(R.drawable.iconstar);
                        star3Review2.setImageResource(R.drawable.iconstar);
                        star4Review2.setImageResource(R.drawable.iconstar);

                        break;
                    case 5:
                        star1Review2.setImageResource(R.drawable.iconstar);
                        star2Review2.setImageResource(R.drawable.iconstar);
                        star3Review2.setImageResource(R.drawable.iconstar);
                        star4Review2.setImageResource(R.drawable.iconstar);
                        star5Review2.setImageResource(R.drawable.iconstar);

                        break;
                    default:

                        break;
                }
            }

            if (reviews.size() > 2 && reviews.get(2) != null) {

                textReviewUser3.setText(reviews.get(2).getUsername());
                textReview3.setText(reviews.get(2).getTextReview());

                switch (reviews.get(2).getStarReview()) {
                    case 1:
                        star1Review3.setImageResource(R.drawable.iconstar);

                        break;
                    case 2:
                        star1Review3.setImageResource(R.drawable.iconstar);
                        star2Review3.setImageResource(R.drawable.iconstar);

                        break;
                    case 3:
                        star1Review3.setImageResource(R.drawable.iconstar);
                        star2Review3.setImageResource(R.drawable.iconstar);
                        star3Review3.setImageResource(R.drawable.iconstar);

                        break;
                    case 4:
                        star1Review3.setImageResource(R.drawable.iconstar);
                        star2Review3.setImageResource(R.drawable.iconstar);
                        star3Review3.setImageResource(R.drawable.iconstar);
                        star4Review3.setImageResource(R.drawable.iconstar);

                        break;
                    case 5:
                        star1Review3.setImageResource(R.drawable.iconstar);
                        star2Review3.setImageResource(R.drawable.iconstar);
                        star3Review3.setImageResource(R.drawable.iconstar);
                        star4Review3.setImageResource(R.drawable.iconstar);
                        star5Review3.setImageResource(R.drawable.iconstar);

                        break;
                    default:

                        break;
                }
            }

//            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
//                    android.R.layout.simple_list_item_1, android.R.id.text1, reviewsWithNameOfUsers);
            // listView.setAdapter(adapter);
        }
    }

    @Override
    public void onSelectBookViewsDone(String result) throws UnsupportedEncodingException {

//        if (result.isEmpty()) {
//            AddBookViewsTask addBookViewsTask = new AddBookViewsTask(idBook, 1, userAfterLogin.getUsername());
//            addBookViewsTask.setAddBookViewsDelegate(bookActivity);
//            //Toast.makeText(getApplicationContext(),"Am inserat prima vizionare a cartii",Toast.LENGTH_SHORT).show();
//        } else {
//            bookViews = DataManager.getInstance().parseBookViews(result);
//            number = bookViews.getViews();
//            number++;
//            UpdateBookViewsTask updateBookViewsTask = new UpdateBookViewsTask(idBook, number, userAfterLogin.getUsername());
//            updateBookViewsTask.setUpdateBookViewsDelegate(bookActivity);
//            //Toast.makeText(getApplicationContext(),"Am crescut numarul de vizionari a cartii si s-a updatat in baza de date",Toast.LENGTH_SHORT).show();
//        }
    }

    @Override
    public void onSelectBookViewsAndDateDone(String result) throws UnsupportedEncodingException, ParseException {

        Log.d(TAG, "SelectBookViewsAndDate DONE DELEGATE " + result);
        if (result.isEmpty()) {
            Log.i(TAG, "AddBookViews task with idBook: " + idBook + ", 1, username:" + userAfterLogin.getUsername());
            AddBookViewsTask addBookViewsTask = new AddBookViewsTask(idBook, 1, userAfterLogin.getUsername());
            addBookViewsTask.setAddBookViewsDelegate(bookActivity);
            //Toast.makeText(getApplicationContext(),"Am inserat prima vizionare a cartii",Toast.LENGTH_SHORT).show();
        } else {
            bookViewsAndDate = DataManager.getInstance().parseBookViewsAndDate(result);
            number = bookViewsAndDate.getViews();
            number++;
            month = bookViewsAndDate.getMonth();

            Log.i(TAG, "UpdateBookViews task with idBook: " + idBook + ", number: " + number + ", username:" + userAfterLogin.getUsername());
            UpdateBookViewsTask updateBookViewsTask = new UpdateBookViewsTask(idBook, number, userAfterLogin.getUsername());
            updateBookViewsTask.setUpdateBookViewsDelegate(bookActivity);

        }
    }

    @Override
    public void onUpdateBookViewsDone(String result) {

        Log.d(TAG, "UpdateBookViews DONE DELEGATE " + result);
        if (monthToday != month) {

            Log.i(TAG, "AddBookViewsAndDate task with idBook: " + idBook + ", 1, monthToday:" + monthToday + ", username:" + userAfterLogin.getUsername());
            AddBookViewsAndDateTask addBookViewsAndDateTask = new AddBookViewsAndDateTask(idBook, 1, monthToday, userAfterLogin.getUsername());
            addBookViewsAndDateTask.setAddBookViewsAndDateDelegate(bookActivity);

        } else {
            Log.i(TAG, "UpdateBookViewsAndDate task with idBook: " + idBook + ", number:" + number + ", username:" + userAfterLogin.getUsername() + ", month:" + month);
            UpdateBookViewsAndDateTask updateBookViewsAndDateTask = new UpdateBookViewsAndDateTask(idBook, number, userAfterLogin.getUsername(), month);
            updateBookViewsAndDateTask.setUpdateBookViewsAndDateDelegate(bookActivity);
        }
    }

    @Override
    public void onUpdateBookViewsAndDateDone(String result) {
        Log.d(TAG, "UpdateBookViewsAndDate DONE DELEGATE " + result);

    }

    @Override
    public void onAddBookViewsDone(String result) throws UnsupportedEncodingException {
        Log.d(TAG, "AddBookViews DONE DELEGATE " + result);
        if (!result.isEmpty()) {
            Log.i(TAG, " AddBookViewsAndDate task with idBook: " + idBook + ", 1, monthToday:" + monthToday + ", username:" + userAfterLogin.getUsername());
            AddBookViewsAndDateTask addBookViewsAndDateTask = new AddBookViewsAndDateTask(idBook, 1, monthToday, userAfterLogin.getUsername());
            addBookViewsAndDateTask.setAddBookViewsAndDateDelegate(bookActivity);
        }
    }

    @Override
    public void onAddBookViewsAndDateDone(String result) throws UnsupportedEncodingException {
        Log.d(TAG, "AddBookViewsAndDate DONE DELEGATE " + result);
    }
}

