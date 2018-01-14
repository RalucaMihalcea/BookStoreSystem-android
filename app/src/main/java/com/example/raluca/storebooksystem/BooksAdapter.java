package com.example.raluca.storebooksystem;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.List;

import model.Book;
import model.User;
import webservice.AddFavoriteBookDelegate;
import webservice.AddFavoriteBookTask;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.MyViewHolder> implements AddFavoriteBookDelegate {

    private BooksAdapter booksAdapter;
    private Context mContext;
    private List<Book> booksList;
    private Resources resources;
    private int idCover;
    private List<Integer> covers;
    private int imageNumber;
    private User userAfterLogin;
    private Book bookk;


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView title, price;
        public ImageView thumbnail, overflow;


        public MyViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            title = (TextView) view.findViewById(R.id.title);
            price = (TextView) view.findViewById(R.id.price);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            overflow = (ImageView) view.findViewById(R.id.overflow);
            thumbnail.setOnClickListener(this);
            title.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            // Toast.makeText(mContext, "Nutrition", Toast.LENGTH_SHORT).show();


            for (Book book : booksList) {
                String titleAndAuthor = book.getTitle() + " - " + book.getAuthor();
                if (titleAndAuthor.equals(title.getText())) {
                    //Toast.makeText(mContext, "Nutrition: "+imageNumber, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(mContext, BookActivity.class);
                    intent.putExtra("book", (Serializable) book);
                    intent.putExtra("imageNumber", imageNumber);
                    intent.putExtra("userAfterLogin", userAfterLogin);
                    // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    mContext.startActivity(intent);
                }
            }
        }


    }

    public BooksAdapter(Context mContext, List<Book> booksList, List<Integer> covers, User userAfterLogin) {

        this.mContext = mContext;
        this.booksList = booksList;
        this.covers = covers;
        this.userAfterLogin = userAfterLogin;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.album_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        bookk = booksList.get(position);
        holder.title.setText(bookk.getTitle() + " - " + bookk.getAuthor());
        holder.price.setText(bookk.getPrice() + " RON");
        imageNumber = covers.get(position);

        // loading album cover using Glide library
        Glide.with(mContext).load(covers.get(position)).into(holder.thumbnail);

        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(holder.overflow);
            }
        });
    }

    /**
     * Showing popup menu when tapping on 3 dots
     */
    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_album, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
    }

    /**
     * Click listener for popup menu items
     */
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_add_favourite:
                    Toast.makeText(mContext, "Add to favourite", Toast.LENGTH_SHORT).show();

                    String author = bookk.getAuthor();
                    if (author.contains(" "))
                        author = author.replaceAll(" ", "+");

                    String title = bookk.getTitle();
                    if (title.contains(" "))
                        title = title.replaceAll(" ", "+");

                    String category = bookk.getCategory();
                    if (category.contains(" "))
                        category = category.replaceAll(" ", "+");

                    String namePicture = bookk.getNamePicture();
                    if (namePicture.contains(" "))
                        namePicture = namePicture.replaceAll(" ", "+");


                    AddFavoriteBookTask addFavoriteBookTask = new AddFavoriteBookTask(bookk.getId(), title, author, category, namePicture, userAfterLogin.getUsername());
                    addFavoriteBookTask.setAddFavoriteBookDelegate(booksAdapter);
                    return true;

                case R.id.action_play_next:
                    Toast.makeText(mContext, "Play next", Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }
    }

    @Override
    public int getItemCount() {
        return booksList.size();
    }

    @Override
    public void onAddFavoriteBookDone(String result) throws UnsupportedEncodingException {

    }

    @Override
    public void onAddFavoriteBookError(String response) {

    }
}
