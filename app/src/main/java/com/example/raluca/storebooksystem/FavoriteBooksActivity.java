package com.example.raluca.storebooksystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.util.List;

import manager.DataManager;
import model.FavoriteBook;
import model.User;
import webservice.SelectFavoriteBooksByUserDelegate;
import webservice.SelectFavoriteBooksByUserTask;

public class FavoriteBooksActivity extends AppCompatActivity implements SelectFavoriteBooksByUserDelegate {

    private FavoriteBooksActivity favoriteBooksActivity;
    private User userAfterLogin;
    private ListView m_listViewFavoriteBooks;
    private CustomAdaptor customAdaptorr = new CustomAdaptor();
    private ImageView imageViewFavoriteBook;
    private TextView titleText;
    private TextView authorText;
    private TextView categoryText;
    private List<FavoriteBook> favoriteBooks;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_books);

        favoriteBooksActivity = this;

        Intent intent = getIntent();
        userAfterLogin = (User) intent.getSerializableExtra("userAfterLogin");

        m_listViewFavoriteBooks = (ListView) findViewById(R.id.listViewFavoriteBooks);

        SelectFavoriteBooksByUserTask selectFavoriteBooksByUserTask = new SelectFavoriteBooksByUserTask(userAfterLogin.getUsername());
        selectFavoriteBooksByUserTask.setSelectFavoriteBooksByUserDelegate(favoriteBooksActivity);


    }

    @Override
    public void onSelectFavoriteBooksByUserDone(String result) throws UnsupportedEncodingException {
        if (!result.equals("[]\n")) {
            favoriteBooks = DataManager.getInstance().parseFavoriteBooks(result);
            DataManager.getInstance().setFavoriteBooksList(favoriteBooks);

            int a=favoriteBooks.size();
            Toast.makeText(this, "eeeeeeeeet"+a, Toast.LENGTH_SHORT).show();


            m_listViewFavoriteBooks.setAdapter(customAdaptorr);

        }
    }

    private class CustomAdaptor extends BaseAdapter {
        @Override
        public int getCount() {
            return favoriteBooks.size();
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
            View view = getLayoutInflater().inflate(R.layout.template_for_favorite_books, null);

            imageViewFavoriteBook = (ImageView) view.findViewById(R.id.imageViewFavoriteBook);
            titleText = (TextView) view.findViewById(R.id.titleText);
            authorText = (TextView) view.findViewById(R.id.authorText);
            categoryText = (TextView) view.findViewById(R.id.categoryText);

            int resID = getResources().getIdentifier(favoriteBooks.get(position).getNamePicture(), "drawable", getPackageName());
            imageViewFavoriteBook.setImageResource(resID);

            titleText.setText(favoriteBooks.get(position).getTitle());
            authorText.setText(favoriteBooks.get(position).getAuthor());
            categoryText.setText(favoriteBooks.get(position).getCategory());
            return view;
        }
    }
}
