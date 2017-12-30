package com.example.raluca.storebooksystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import manager.DataManager;
import model.Book;
import model.Review;
import webservice.SelectReviewsByIdBookDelegate;
import webservice.SelectReviewsByIdBookTask;

public class BookActivity extends AppCompatActivity implements SelectReviewsByIdBookDelegate {
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
    //private Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bookActivity = this;

        setContentView(R.layout.activity_book_activity);
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

        // button=(Button)findViewById(R.id.buttonOrange);

        Intent intent = getIntent();
        book = (Book) intent.getSerializableExtra("book");
        numberOfStars = book.getStars();
        idBook = book.getId();

        Bundle bundle = intent.getExtras();

//        if (bundle != null) {
//            imageNumber = (int) bundle.get("imageNumber");
//        }

        // imageNumber=(int)intent.getSerializableExtra("imageNumber");

        m_textViewTitle.setText(book.getTitle());
        m_textViewAuthor.setText(book.getAuthor());
        m_textViewDescription.setText(book.getDescription());
        //Toast.makeText(this, "Hellllo "+book.getStars(), Toast.LENGTH_SHORT).show();


        int resID = getResources().getIdentifier(book.getNamePicture(), "drawable", getPackageName());
        imageView.setImageResource(resID);

        SelectReviewsByIdBookTask selectReviewsByIdBookTask = new SelectReviewsByIdBookTask(idBook);
        selectReviewsByIdBookTask.setSelectReviewsByIdBookDelegate(bookActivity);

        m_textViewShowMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentt = new Intent(BookActivity.this, DescriptionActivity.class);
                intentt.putExtra("book", book);
                startActivity(intentt);

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

        if (!result.isEmpty()) {
            reviews = DataManager.getInstance().parseReviews(result);
            DataManager.getInstance().setReviewsList(reviews);

//            for (Review review : reviews) {
//                reviewsWithNameOfUsers.add(review.getUsername() + "\n" + review.getTextReview());
//
//            }

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

            if (reviews.get(1) != null) {

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

            if (reviews.get(2) != null) {

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
}

