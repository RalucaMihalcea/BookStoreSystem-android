package com.example.raluca.storebooksystem.Activities.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.raluca.storebooksystem.R;

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
    private Resources resources;
    private CustomAdaptor customAdaptorr;
    private ImageView imageViewFavoriteBook;
    private ImageView m_trashImageView;
    private TextView titleText;
    private TextView authorText;
    private TextView categoryText;
    private List<FavoriteBook> favoriteBooks;
    private List<Book> favoriteBooksForActualUser = new ArrayList<>();
    private List<Book> booksList;
    private List<String> imageLinkList = new ArrayList<>();
    private ListView m_listViewFavoriteBooks;
    private Long idBookForDelete;
    private String auxiliarString;
    private static final String TAG = "FavoriteBooksActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_books);

        favoriteBooksActivity = this;
        resources = this.getResources();

        Intent intent = getIntent();
        userAfterLogin = (User) intent.getSerializableExtra("userAfterLogin");

        m_listViewFavoriteBooks = (ListView) findViewById(R.id.listViewFavoriteBooks);

        customAdaptorr = new CustomAdaptor(getApplicationContext(), favoriteBooksForActualUser, imageLinkList);

        SelectFavoriteBooksByUserTask selectFavoriteBooksByUserTask = new SelectFavoriteBooksByUserTask(userAfterLogin.getUsername());
        selectFavoriteBooksByUserTask.setSelectFavoriteBooksByUserDelegate(favoriteBooksActivity);


    }

    @Override
    public void onSelectFavoriteBooksByUserDone(String result) throws UnsupportedEncodingException {
        Log.d(TAG, "SelectFavoriteBooksByUser DONE DELEGATE " + result);

        if (!result.equals("[]\n")) {
            favoriteBooks = DataManager.getInstance().parseFavoriteBooks(result);
            DataManager.getInstance().setFavoriteBooksList(favoriteBooks);

            SelectBooksTask selectBooksTask = new SelectBooksTask();
            selectBooksTask.setSelectBooksDelegate(favoriteBooksActivity);

            // m_listViewFavoriteBooks.setAdapter(customAdaptorr);

        } else {

            customAdaptorr.notifyDataSetChanged();
            m_listViewFavoriteBooks.setAdapter(customAdaptorr);
        }
    }

    @Override
    public void onSelectBooksDone(String result) throws UnsupportedEncodingException {
        Log.d(TAG, "SelectBooks DONE DELEGATE " + result);
        if (!result.equals("[]\n")) {
            booksList = DataManager.getInstance().parseBooks(result);

            for (FavoriteBook favBook : favoriteBooks)
                for (Book book : booksList)
                    if (favBook.getIdBook() == book.getId()) {
                        favoriteBooksForActualUser.add(book);
                        // int resID = resources.getIdentifier(book.getNamePicture().toString(), "drawable", this.getPackageName());
                        imageLinkList.add(book.getImageLink());
                        break;
                    }
            customAdaptorr = new CustomAdaptor(getApplicationContext(), favoriteBooksForActualUser, imageLinkList);
            customAdaptorr.notifyDataSetChanged();
            m_listViewFavoriteBooks.setAdapter(customAdaptorr);

        } else {

            customAdaptorr.notifyDataSetChanged();
            m_listViewFavoriteBooks.setAdapter(customAdaptorr);
        }

    }

    @Override
    public void onDeleteFavoriteBookDone(String result) throws UnsupportedEncodingException {
        Log.d(TAG, "DeleteFavoriteBook DONE DELEGATE " + result);
        if (!result.equals("")) {
            Toast.makeText(getApplicationContext(), "The book has been removed from favorites!", Toast.LENGTH_SHORT).show();

            favoriteBooksForActualUser.clear();
            imageLinkList.clear();
            //customAdaptorr.notifyDataSetChanged();
            // m_listViewFavoriteBooks.setAdapter(customAdaptorr);

            SelectFavoriteBooksByUserTask selectFavoriteBooksByUserTask = new SelectFavoriteBooksByUserTask(userAfterLogin.getUsername());
            selectFavoriteBooksByUserTask.setSelectFavoriteBooksByUserDelegate(favoriteBooksActivity);

//            Intent intent = new Intent(this, FavoriteBooksActivity.class);
//            intent.putExtra("userAfterLogin", userAfterLogin);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            startActivity(intent);

        }

    }

    @Override
    public void onDeleteFavoriteBookError(String response) {

    }

//    public class ViewHolder {
//        private ImageView imageViewFavoriteBook;
//        private ImageView m_trashImageView;
//        private TextView titleText;
//        private TextView authorText;
//        private TextView categoryText;
//
//    }

    private class CustomAdaptor extends BaseAdapter {

        Context context;
        LayoutInflater inflater;
        private List<Book> favoriteBooksForActualUser = new ArrayList<>();
        private List<String> imageLinkList = new ArrayList<>();

        public CustomAdaptor(@NonNull Context context, List<Book> favoriteBooksForActualUser, List<String> imageLinkList) {
            this.context = context;
            this.favoriteBooksForActualUser = favoriteBooksForActualUser;
            this.imageLinkList = imageLinkList;
        }

        public class ViewHolder {

            private ImageView imageViewFavoriteBook;
            private ImageView m_trashImageView;
            private ImageView m_shareImageView;
            private TextView titleText;
            private TextView authorText;
            private TextView categoryText;
        }


        @Override
        public int getCount() {
            return favoriteBooksForActualUser.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }


        private void shareTextUrl(String url) {
            Intent share = new Intent(android.content.Intent.ACTION_SEND);
            share.setType("text/plain");
            share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

            // Add data to the intent, the receiving app will decide
            // what to do with it.
            share.putExtra(Intent.EXTRA_SUBJECT, "Title Of The Post");
            share.putExtra(Intent.EXTRA_TEXT, url);

            context.startActivity(Intent.createChooser(share, "Share link!"));
        }


        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {


            if (convertView == null) {
                inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.template_for_favorite_books, null);
            }

            final ViewHolder holder = new ViewHolder();

            holder.imageViewFavoriteBook = (ImageView) convertView.findViewById(R.id.imageViewFavoriteBook);
            holder.m_trashImageView = (ImageView) convertView.findViewById(R.id.trashImageView);
            holder.m_shareImageView = (ImageView) convertView.findViewById(R.id.shareImageView);
            holder.titleText = (TextView) convertView.findViewById(R.id.titleText);
            holder.authorText = (TextView) convertView.findViewById(R.id.authorText);
            holder.categoryText = (TextView) convertView.findViewById(R.id.categoryText);

            holder.m_shareImageView.setVisibility(View.INVISIBLE);

            //int resID = resources.getIdentifier(favoriteBooksForActualUser.get(position).getNamePicture().toString(), "drawable", getPackageName());
            auxiliarString = imageLinkList.get(position);
            Glide.with(context).load("https://docs.google.com/uc?export=download&id=" + auxiliarString).into(holder.imageViewFavoriteBook);
            //holder.imageViewFavoriteBook.setImageResource(imageLinkList.get(position));

            holder.titleText.setText(favoriteBooksForActualUser.get(position).getTitle());
            holder.authorText.setText(favoriteBooksForActualUser.get(position).getAuthor());
            holder.categoryText.setText(favoriteBooksForActualUser.get(position).getCategory());

            for (Book favBook : favoriteBooksForActualUser)
                if (favBook.getTitle().equals(holder.titleText.getText().toString()) && !favBook.getPdfLink().isEmpty()) {

                    holder.m_shareImageView.setVisibility(View.VISIBLE);
                }

            holder.m_trashImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // Toast.makeText(getApplicationContext(), "Click!" + position, Toast.LENGTH_SHORT).show();
                    for (Book favBook : favoriteBooksForActualUser)
                        if (favBook.getTitle().equals(holder.titleText.getText().toString())) {
                            idBookForDelete = favBook.getId();
                            DeleteFavoriteBookTask deleteFavoriteBookTask = new DeleteFavoriteBookTask(idBookForDelete, userAfterLogin.getUsername());
                            deleteFavoriteBookTask.setDeleteFavoriteBookDelegate(favoriteBooksActivity);

                        }
                }
            });

            holder.m_shareImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (Book favBook : favoriteBooksForActualUser)
                        if (favBook.getTitle().equals(holder.titleText.getText().toString())) {
                            shareTextUrl(favBook.getPdfLink());
                        }
                }
            });


            customAdaptorr.notifyDataSetChanged();
            // m_listViewFavoriteBooks.setAdapter(customAdaptorr);
            return convertView;
        }

    }
}