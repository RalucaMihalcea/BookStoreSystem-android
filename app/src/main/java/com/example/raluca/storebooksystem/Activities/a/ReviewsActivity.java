package com.example.raluca.storebooksystem.Activities.a;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.raluca.storebooksystem.R;

import java.util.List;

import model.Review;

public class ReviewsActivity extends AppCompatActivity {

    private ImageView starRev1, starRev2, starRev3, starRev4, starRev5;
    private TextView usernameTextView, textReviewTextView;
    private List<Review> reviews;
    private ListView m_listView;
    private CustomAdaptor customAdaptor = new CustomAdaptor();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);

        m_listView = (ListView) findViewById(R.id.listView);

        Intent intent = getIntent();
        reviews = (List<Review>) intent.getSerializableExtra("reviews");

        m_listView.setAdapter(customAdaptor);

    }

    class CustomAdaptor extends BaseAdapter {
        @Override
        public int getCount() {
            return reviews.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = getLayoutInflater().inflate(R.layout.customlayout, null);

//            Intent intent = getIntent();
//            reviews = (List<Review>) intent.getSerializableExtra("reviews");


            starRev1 = (ImageView) view.findViewById(R.id.starRev1);
            starRev2 = (ImageView) view.findViewById(R.id.starRev2);
            starRev3 = (ImageView) view.findViewById(R.id.starRev3);
            starRev4 = (ImageView) view.findViewById(R.id.starRev4);
            starRev5 = (ImageView) view.findViewById(R.id.starRev5);
            usernameTextView = (TextView) view.findViewById(R.id.usernameTextView);
            textReviewTextView = (TextView) view.findViewById(R.id.textReviewTextView);

            usernameTextView.setText(reviews.get(position).getUsername());
            textReviewTextView.setText(reviews.get(position).getTextReview());

            setStars(position);
            return view;
        }

    }

    public void setStars(int position) {
        switch (reviews.get(position).getStarReview()) {
            case 1:
                starRev1.setImageResource(R.drawable.iconstar);

                return;
            case 2:
                starRev1.setImageResource(R.drawable.iconstar);
                starRev2.setImageResource(R.drawable.iconstar);

                return;
            case 3:
                starRev1.setImageResource(R.drawable.iconstar);
                starRev2.setImageResource(R.drawable.iconstar);
                starRev3.setImageResource(R.drawable.iconstar);

                return;
            case 4:
                starRev1.setImageResource(R.drawable.iconstar);
                starRev2.setImageResource(R.drawable.iconstar);
                starRev3.setImageResource(R.drawable.iconstar);
                starRev4.setImageResource(R.drawable.iconstar);

                return;
            case 5:
                starRev1.setImageResource(R.drawable.iconstar);
                starRev2.setImageResource(R.drawable.iconstar);
                starRev3.setImageResource(R.drawable.iconstar);
                starRev4.setImageResource(R.drawable.iconstar);
                starRev5.setImageResource(R.drawable.iconstar);

                return;
            default:

                return;
        }
    }
}




