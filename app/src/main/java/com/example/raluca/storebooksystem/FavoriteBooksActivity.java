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
import java.util.ArrayList;
import java.util.List;

import manager.DataManager;
import model.Book;
import model.FavoriteBook;
import model.User;
import webservice.DeleteFavoriteBookDelegate;
import webservice.DeleteFavoriteBookTask;
import webservice.SelectBooksDelegate;
import webservice.SelectBooksTask;
import webservice.SelectFavoriteBooksByUserDelegate;
import webservice.SelectFavoriteBooksByUserTask;

public class FavoriteBooksActivity extends AppCompatActivity implements SelectFavoriteBooksByUserDelegate, SelectBooksDelegate, DeleteFavoriteBookDelegate {

    private FavoriteBooksActivity favoriteBooksActivity;
    private User userAfterLogin;

    private CustomAdaptor customAdaptorr = new CustomAdaptor();
    private ImageView imageViewFavoriteBook;
    private ImageView m_trashImageView;
    private TextView titleText;
    private TextView authorText;
    private TextView categoryText;
    private List<FavoriteBook> favoriteBooks;
    private List<Book> favoriteBooksForActualUser= new ArrayList<>();
    private List<Book> booksList;
    private ListView m_listViewFavoriteBooks;


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

            SelectBooksTask selectBooksTask = new SelectBooksTask();
            selectBooksTask.setSelectBooksDelegate(favoriteBooksActivity);

            // m_listViewFavoriteBooks.setAdapter(customAdaptorr);

        }
    }

    @Override
    public void onSelectBooksDone(String result) throws UnsupportedEncodingException {
        if (!result.equals("[]\n")) {
            booksList = DataManager.getInstance().parseBooks(result);

            for (FavoriteBook favBook : favoriteBooks)
                for (Book book : booksList)
                    if (favBook.getIdBook() == book.getId()) {
                        favoriteBooksForActualUser.add(book);
                        break;
                    }
            m_listViewFavoriteBooks.setAdapter(customAdaptorr);


        }

    }

    @Override
    public void onDeleteFavoriteBookDone(String result) throws UnsupportedEncodingException {
        if (!result.equals("")) {
            Toast.makeText(getApplicationContext(), "The book has been removed from favorites!", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, FavoriteBooksActivity.class);
            intent.putExtra("userAfterLogin", userAfterLogin);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

        }

    }

    @Override
    public void onDeleteFavoriteBookError(String response) {

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
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = getLayoutInflater().inflate(R.layout.template_for_favorite_books, null);

            imageViewFavoriteBook = (ImageView) view.findViewById(R.id.imageViewFavoriteBook);
            m_trashImageView=(ImageView)view.findViewById(R.id.trashImageView);
            titleText = (TextView) view.findViewById(R.id.titleText);
            authorText = (TextView) view.findViewById(R.id.authorText);
            categoryText = (TextView) view.findViewById(R.id.categoryText);

            int resID = getResources().getIdentifier(favoriteBooksForActualUser.get(position).getNamePicture(), "drawable", getPackageName());
            imageViewFavoriteBook.setImageResource(resID);

            titleText.setText(favoriteBooksForActualUser.get(position).getTitle());
            authorText.setText(favoriteBooksForActualUser.get(position).getAuthor());
            categoryText.setText(favoriteBooksForActualUser.get(position).getCategory());

            m_trashImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText(getApplicationContext(), "Click!"+position, Toast.LENGTH_SHORT).show();
                    DeleteFavoriteBookTask deleteFavoriteBookTask = new DeleteFavoriteBookTask(favoriteBooksForActualUser.get(position).getId(), userAfterLogin.getUsername());
                    deleteFavoriteBookTask.setDeleteFavoriteBookDelegate(favoriteBooksActivity);


                }
            });

            return view;
        }

    }
}