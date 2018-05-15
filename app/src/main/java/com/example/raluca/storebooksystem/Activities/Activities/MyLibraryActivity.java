package com.example.raluca.storebooksystem.Activities.Activities;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.raluca.storebooksystem.Activities.Activities.BookActivity;
import com.example.raluca.storebooksystem.Activities.Activities.CustomComparator;
import com.example.raluca.storebooksystem.Activities.Activities.FavoriteBooksActivity;
import com.example.raluca.storebooksystem.Activities.Activities.LoginActivity;
import com.example.raluca.storebooksystem.Activities.Activities.ProfileActivity;
import com.example.raluca.storebooksystem.Activities.Activities.StatisticsActivity;
import com.example.raluca.storebooksystem.R;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import javax.mail.Quota;

import manager.DataManager;
import model.Book;
import model.BookViews;
import model.BookViewsAndDate;
import model.User;
import webservice.SelectBookByCategoryDelegate;
import webservice.SelectBookByCategoryTask;
import webservice.SelectBookByIdDelegate;
import webservice.SelectBookByIdTask;
import webservice.SelectBookViewsAndDateByUsernameDelegate;
import webservice.SelectBookViewsAndDateByUsernameTask;
import webservice.SelectBookViewsByUsernameDelegate;
import webservice.SelectBookViewsByUsernameTask;
import webservice.SelectBooksDelegate;
import webservice.SelectBooksTask;
import webservice.UpdateBookDelegate;
import webservice.UpdateBookTask;

public class MyLibraryActivity extends AppCompatActivity implements /*UpdateBookDelegate, SelectBooksDelegate,*/ SelectBookViewsAndDateByUsernameDelegate, SelectBookViewsByUsernameDelegate, SelectBookByIdDelegate, SelectBookByCategoryDelegate {

    private MyLibraryActivity myLibraryActivity;
    private TextView textEdit, text1, text3, title1, title2, title3, title4, author1, author2, author3, author4, textViewDescriptionInterested;
    private CardView m_cardMyFavoriteBooks, m_cardCartBookStatistics;
    private User userAfterLogin;
    private CardView m_cardScan;
    private CardView m_cardAudioBooks;
    private List<BookViews> bookViewsList = new ArrayList<>();
    private List<Book> booksByCategory = new ArrayList<>();
    private List<Book> randomBookList = new ArrayList<>();
    private BookViews theMostViewedBook;
    private ImageView image_view1, image_view2, image_view3, image_view4;
    private Book book, randomBook;
    private View view1, view2, view3, view4;
    private List<BookViewsAndDate> bookViewsAndDateList = new ArrayList<>();
    List<Book> allBooks = new ArrayList<>();
    List<BookViewsAndDate> ian;
    List<BookViewsAndDate> feb;
    List<BookViewsAndDate> mar;
    List<BookViewsAndDate> apr;
    List<BookViewsAndDate> may;
    List<BookViewsAndDate> jun;
    List<BookViewsAndDate> jul;
    List<BookViewsAndDate> aug;
    List<BookViewsAndDate> sep;
    List<BookViewsAndDate> oct;
    List<BookViewsAndDate> nov;
    List<BookViewsAndDate> dec;
    private String calendarString;
    List<BookViewsAndDate> dayBookListForStatistics = new ArrayList<BookViewsAndDate>();
    private Resources resources;
    private String auxiliarString1, auxiliarString2, auxiliarString3, auxiliarString4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_library);

        myLibraryActivity = this;
        resources=this.getResources();

        ian = new ArrayList<>();
        feb = new ArrayList<>();
        mar = new ArrayList<>();
        apr = new ArrayList<>();
        may = new ArrayList<>();
        jun = new ArrayList<>();
        jul = new ArrayList<>();
        aug = new ArrayList<>();
        sep = new ArrayList<>();
        oct = new ArrayList<>();
        nov = new ArrayList<>();
        dec = new ArrayList<>();

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        calendarString = df.format(c.getTime());

        m_cardMyFavoriteBooks = (CardView) findViewById(R.id.cardMyFavoriteBooks);

        m_cardCartBookStatistics = (CardView) findViewById(R.id.cardCartBookStatistics);
        m_cardScan = (CardView) findViewById(R.id.cardScan);
        //m_cardAudioBooks = (CardView) findViewById(R.id.cardAudioBooks);
        image_view1 = (ImageView) findViewById(R.id.image_view1);
        image_view2 = (ImageView) findViewById(R.id.image_view2);
        image_view3 = (ImageView) findViewById(R.id.image_view3);
        image_view4 = (ImageView) findViewById(R.id.image_view4);
        text3 = (TextView) findViewById(R.id.text3);
        title1 = (TextView) findViewById(R.id.title1);
        title2 = (TextView) findViewById(R.id.title2);
        title3 = (TextView) findViewById(R.id.title3);
        title4 = (TextView) findViewById(R.id.title4);
        author1 = (TextView) findViewById(R.id.author1);
        author2 = (TextView) findViewById(R.id.author2);
        author3 = (TextView) findViewById(R.id.author3);
        author4 = (TextView) findViewById(R.id.author4);
        textViewDescriptionInterested = (TextView) findViewById(R.id.textViewDescriptionInterested);

        view1 = (View) findViewById(R.id.view1);
        view2 = (View) findViewById(R.id.view2);
        view3 = (View) findViewById(R.id.view3);
        view4 = (View) findViewById(R.id.view4);

        textViewDescriptionInterested.setVisibility(View.INVISIBLE);

        view1.setVisibility(View.INVISIBLE);
        view2.setVisibility(View.INVISIBLE);
        view3.setVisibility(View.INVISIBLE);
        view4.setVisibility(View.INVISIBLE);

        title1.setVisibility(View.INVISIBLE);
        title2.setVisibility(View.INVISIBLE);
        title3.setVisibility(View.INVISIBLE);
        title4.setVisibility(View.INVISIBLE);

        author1.setVisibility(View.INVISIBLE);
        author2.setVisibility(View.INVISIBLE);
        author3.setVisibility(View.INVISIBLE);
        author4.setVisibility(View.INVISIBLE);

        image_view1.setVisibility(View.INVISIBLE);
        image_view2.setVisibility(View.INVISIBLE);
        image_view3.setVisibility(View.INVISIBLE);
        image_view4.setVisibility(View.INVISIBLE);

        Intent intent = getIntent();
//        Bundle bundle = intent.getExtras();

        userAfterLogin = (User) intent.getSerializableExtra("userAfterLogin");

        SelectBookViewsByUsernameTask selectBookViewsByUsernameTask = new SelectBookViewsByUsernameTask(userAfterLogin.getUsername());
        selectBookViewsByUsernameTask.setSelectBookViewsByUsernameDelegate(myLibraryActivity);


        image_view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyLibraryActivity.this, BookActivity.class);
                intent.putExtra("book", (Serializable) randomBookList.get(0));
                //intent.putExtra("imageNumber", imageNumber);
                intent.putExtra("userAfterLogin", userAfterLogin);
                startActivity(intent);

            }
        });

        image_view2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyLibraryActivity.this, BookActivity.class);
                intent.putExtra("book", (Serializable) randomBookList.get(1));
                //intent.putExtra("imageNumber", imageNumber);
                intent.putExtra("userAfterLogin", userAfterLogin);
                startActivity(intent);

            }
        });
        image_view3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyLibraryActivity.this, BookActivity.class);
                intent.putExtra("book", (Serializable) randomBookList.get(2));
                //intent.putExtra("imageNumber", imageNumber);
                intent.putExtra("userAfterLogin", userAfterLogin);
                startActivity(intent);

            }
        });

        image_view4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyLibraryActivity.this, BookActivity.class);
                intent.putExtra("book", (Serializable) randomBookList.get(3));
                //intent.putExtra("imageNumber", imageNumber);
                intent.putExtra("userAfterLogin", userAfterLogin);
                startActivity(intent);

            }
        });

        m_cardMyFavoriteBooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyLibraryActivity.this, FavoriteBooksActivity.class);
                intent.putExtra("userAfterLogin", userAfterLogin);
                startActivity(intent);
            }
        });


        m_cardCartBookStatistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SelectBookViewsAndDateByUsernameTask selectBookViewsAndDateByUsernameTask = new SelectBookViewsAndDateByUsernameTask(userAfterLogin.getUsername());
                selectBookViewsAndDateByUsernameTask.setSelectBookViewsAndDateByUsernameDelegate(myLibraryActivity);

            }
        });

        m_cardScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent4 = new Intent(MyLibraryActivity.this, ScanActivity.class);
                intent4.putExtra("userAfterLogin", userAfterLogin);
                startActivity(intent4);

            }
        });

//        m_cardAudioBooks.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent4 = new Intent(MyLibraryActivity.this, AudioBooksActivity.class);
//                intent4.putExtra("userAfterLogin", userAfterLogin);
//                startActivity(intent4);
//
//            }
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_activity_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.profile_id:
                Intent intent = new Intent(MyLibraryActivity.this, ProfileActivity.class);
                intent.putExtra("userAfterLogin", userAfterLogin);
                startActivity(intent);
                return true;

            case R.id.myLibrary_id:
                Intent intent2 = new Intent(MyLibraryActivity.this, MyLibraryActivity.class);
                intent2.putExtra("userAfterLogin", userAfterLogin);
                startActivity(intent2);
                return true;

            case R.id.LogOut_id:
                Intent intent3 = new Intent(MyLibraryActivity.this, LoginActivity.class);
                startActivity(intent3);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onSelectBookViewsByUsernameDone(String result) throws UnsupportedEncodingException {

        int max = -1;

        if (!result.equals("[]\n")) {

            textViewDescriptionInterested.setVisibility(View.VISIBLE);

            view1.setVisibility(View.VISIBLE);
            view2.setVisibility(View.VISIBLE);
            view3.setVisibility(View.VISIBLE);
            view4.setVisibility(View.VISIBLE);

            title1.setVisibility(View.VISIBLE);
            title2.setVisibility(View.VISIBLE);
            title3.setVisibility(View.VISIBLE);
            title4.setVisibility(View.VISIBLE);

            author1.setVisibility(View.VISIBLE);
            author2.setVisibility(View.VISIBLE);
            author3.setVisibility(View.VISIBLE);
            author4.setVisibility(View.VISIBLE);

            image_view1.setVisibility(View.VISIBLE);
            image_view2.setVisibility(View.VISIBLE);
            image_view3.setVisibility(View.VISIBLE);
            image_view4.setVisibility(View.VISIBLE);

            bookViewsList = DataManager.getInstance().parseBookViewsList(result);

            for (BookViews bookViews : bookViewsList)
                if (bookViews.getViews() > max) {
                    max = bookViews.getViews();
                    theMostViewedBook = bookViews;
                }

            SelectBookByIdTask selectBookByIdTask = new SelectBookByIdTask(theMostViewedBook.getIdBook());
            selectBookByIdTask.setSelectBookByIdDelegate(myLibraryActivity);
        }
    }

    @Override
    public void onSelectBookByIdDone(String result) throws UnsupportedEncodingException {

        if (!result.equals("")) {
            book = DataManager.getInstance().parseBook(result);

            SelectBookByCategoryTask selectBookByCategoryTask = new SelectBookByCategoryTask(book.getCategory());
            selectBookByCategoryTask.setSelectBookByCategoryDelegate(myLibraryActivity);
        }
    }

    @Override
    public void onSelectBookByCategoryDone(String result) throws UnsupportedEncodingException {

        if (!result.isEmpty() && !result.equals("[]\n")) {

            booksByCategory = DataManager.getInstance().parseBooks(result);

            //MyLibraryActivity obj = new MyLibraryActivity();
            for (int i = 0; i < 4; i++) {
                //System.out.println(obj.getRandomList(booksByCategory));
                randomBook = getRandomList(booksByCategory);

                if (isDifferentBook(book, randomBook) && isValid(randomBookList, randomBook)) {
                    //Toast.makeText(getApplicationContext(), "Random book is: " + randomBook, Toast.LENGTH_SHORT).show();
                    randomBookList.add(randomBook);
                } else i--;
            }

            //int resID = resources.getIdentifier(randomBookList.get(0).getNamePicture().toString(), "drawable", this.getPackageName());
//            image_view1.setImageResource(resID);
            auxiliarString1=randomBookList.get(0).getImageLink();
            Glide.with(this).load("https://docs.google.com/uc?export=download&id="+auxiliarString1).into(image_view1);
            title1.setText(randomBookList.get(0).getTitle());
            author1.setText(randomBookList.get(0).getAuthor());

            // int resID2 = resources.getIdentifier(randomBookList.get(1).getNamePicture().toString(), "drawable", this.getPackageName());
//            image_view2.setImageResource(resID2);
            auxiliarString2=randomBookList.get(1).getImageLink();
            Glide.with(this).load("https://docs.google.com/uc?export=download&id="+auxiliarString2).into(image_view2);
            title2.setText(randomBookList.get(1).getTitle());
            author2.setText(randomBookList.get(1).getAuthor());

            // int resID3 = resources.getIdentifier(randomBookList.get(2).getNamePicture().toString(), "drawable", this.getPackageName());
//            image_view3.setImageResource(resID3);
            auxiliarString3=randomBookList.get(2).getImageLink();
            Glide.with(this).load("https://docs.google.com/uc?export=download&id="+auxiliarString3).into(image_view3);
            title3.setText(randomBookList.get(2).getTitle());
            author3.setText(randomBookList.get(2).getAuthor());

            //int resID4 = resources.getIdentifier(randomBookList.get(3).getNamePicture().toString(), "drawable", this.getPackageName());
            //image_view4.setImageResourceresID4);
            auxiliarString4=randomBookList.get(3).getImageLink();
            Glide.with(this).load("https://docs.google.com/uc?export=download&id="+auxiliarString4).into(image_view4);
            title4.setText(randomBookList.get(3).getTitle());
            author4.setText(randomBookList.get(3).getAuthor());

            text3.setText(book.getTitle());

//            SelectBooksTask selectBooksTask = new SelectBooksTask();
//            selectBooksTask.setSelectBooksDelegate(myLibraryActivity);


        }
    }

    public Book getRandomList(List<Book> list) {

        int index = ThreadLocalRandom.current().nextInt(list.size());
        return list.get(index);
    }

    public Boolean isValid(List<Book> randomBookList, Book randomBook) {
        if (!randomBookList.contains(randomBook))
            return true;
        return false;
    }

    public Boolean isDifferentBook(Book book, Book randomBook) {
        if (!book.getTitle().toString().equals(randomBook.getTitle().toString()) && !book.getAuthor().toString().equals(randomBook.getAuthor().toString()))
            return true;
        return false;
    }

    @Override
    public void onSelectBookViewsAndDateByUsernameDone(String result) throws UnsupportedEncodingException {

        if (!result.isEmpty() && !result.equals("[]\n")) {

            bookViewsAndDateList = DataManager.getInstance().parseBookViewsAndDateList(result);

            for (BookViewsAndDate bookViewsAndDate : bookViewsAndDateList) {
                int numberMonth = bookViewsAndDate.getMonth();
                switch (numberMonth) {
                    case 1:
                        ian.add(bookViewsAndDate);
                        break;
                    case 2:
                        feb.add(bookViewsAndDate);
                        break;
                    case 3:
                        mar.add(bookViewsAndDate);
                        break;
                    case 4:
                        apr.add(bookViewsAndDate);
                        break;
                    case 5:
                        may.add(bookViewsAndDate);
                        break;
                    case 6:
                        jun.add(bookViewsAndDate);
                        break;
                    case 7:
                        jul.add(bookViewsAndDate);
                        break;
                    case 8:
                        aug.add(bookViewsAndDate);
                        break;
                    case 9:
                        sep.add(bookViewsAndDate);
                        break;
                    case 10:
                        oct.add(bookViewsAndDate);
                        break;
                    case 11:
                        nov.add(bookViewsAndDate);
                        break;
                    case 12:
                        dec.add(bookViewsAndDate);
                        break;
                }
            }

            for (int i = 1; i <= 12; i++) {
                switch (i) {
                    case 1:
                        int sizeIan = ian.size();
                        if (sizeIan >= 2) {

                            Collections.sort(ian, new CustomComparator());
                            dayBookListForStatistics.add(ian.get(0));
                            dayBookListForStatistics.add(ian.get(1));
                        } else if (sizeIan == 1) dayBookListForStatistics.add(ian.get(0));
                        break;


                    case 2:
                        int sizeFeb = feb.size();
                        if (sizeFeb >= 2) {

                            Collections.sort(feb, new CustomComparator());
                            dayBookListForStatistics.add(feb.get(0));
                            dayBookListForStatistics.add(feb.get(1));
                        } else if (sizeFeb == 1) dayBookListForStatistics.add(feb.get(0));

                        break;

                    case 3:
                        int sizeMar = mar.size();
                        if (sizeMar >= 2) {
                            Collections.sort(mar, new CustomComparator());
                            dayBookListForStatistics.add(mar.get(0));
                            dayBookListForStatistics.add(mar.get(1));
                        } else if (sizeMar == 1) dayBookListForStatistics.add(mar.get(0));

                        break;

                    case 4:
                        int sizeApr = apr.size();
                        if (sizeApr >= 2) {
                            Collections.sort(apr, new CustomComparator());
                            dayBookListForStatistics.add(apr.get(0));
                            dayBookListForStatistics.add(apr.get(1));
                        } else if (sizeApr == 1) dayBookListForStatistics.add(apr.get(0));

                        break;

                    case 5:
                        int sizeMay = may.size();
                        if (sizeMay >= 2) {
                            Collections.sort(may, new CustomComparator());
                            dayBookListForStatistics.add(may.get(0));
                            dayBookListForStatistics.add(may.get(1));
                        } else if (sizeMay == 1) dayBookListForStatistics.add(may.get(0));

                        break;

                    case 6:
                        int sizeJun = jun.size();
                        if (sizeJun >= 2) {
                            Collections.sort(jun, new CustomComparator());
                            dayBookListForStatistics.add(jun.get(0));
                            dayBookListForStatistics.add(jun.get(1));
                        } else if (sizeJun == 1) dayBookListForStatistics.add(jun.get(0));

                        break;

                    case 7:
                        int sizeJul = jul.size();
                        if (sizeJul >= 2) {
                            Collections.sort(jul, new CustomComparator());
                            dayBookListForStatistics.add(jul.get(0));
                            dayBookListForStatistics.add(jul.get(1));
                        } else if (sizeJul == 1) dayBookListForStatistics.add(jul.get(0));

                        break;

                    case 8:
                        int sizeAug = aug.size();
                        if (sizeAug >= 2) {
                            Collections.sort(aug, new CustomComparator());
                            dayBookListForStatistics.add(aug.get(0));
                            dayBookListForStatistics.add(aug.get(1));
                        } else if (sizeAug == 1) dayBookListForStatistics.add(aug.get(0));

                        break;

                    case 9:
                        int sizeSep = sep.size();
                        if (sizeSep >= 2) {
                            Collections.sort(sep, new CustomComparator());
                            dayBookListForStatistics.add(sep.get(0));
                            dayBookListForStatistics.add(sep.get(1));
                        } else if (sizeSep == 1) dayBookListForStatistics.add(sep.get(0));

                        break;

                    case 10:
                        int sizeOct = oct.size();
                        if (sizeOct >= 2) {
                            Collections.sort(oct, new CustomComparator());
                            dayBookListForStatistics.add(oct.get(0));
                            dayBookListForStatistics.add(oct.get(1));
                        } else if (sizeOct == 1) dayBookListForStatistics.add(oct.get(0));

                        break;

                    case 11:
                        int sizeNov = nov.size();
                        if (sizeNov >= 2) {
                            Collections.sort(nov, new CustomComparator());
                            dayBookListForStatistics.add(nov.get(0));
                            dayBookListForStatistics.add(nov.get(1));
                        } else if (sizeNov == 1) dayBookListForStatistics.add(nov.get(0));

                        break;

                    case 12:
                        int sizeDec = dec.size();
                        if (sizeDec >= 2) {
                            Collections.sort(dec, new CustomComparator());
                            dayBookListForStatistics.add(dec.get(0));
                            dayBookListForStatistics.add(dec.get(1));
                        } else if (sizeDec == 1) dayBookListForStatistics.add(dec.get(0));

                        break;
                }
            }


            Intent intent = new Intent(MyLibraryActivity.this, StatisticsActivity.class);
            intent.putExtra("dayBookListForStatistics", (Serializable) dayBookListForStatistics);
            //intent.putExtra("userAfterLogin", userAfterLogin);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }

    }

//    @Override
//    public void onSelectBooksDone(String result) throws UnsupportedEncodingException {
//        if (!result.equals("[]\n")) {
//            allBooks = DataManager.getInstance().parseBooks(result);
//            for (Book book : allBooks)
//                if (book.getNotified() == 0) {
//                    NotificationGenerator.openActivityNotification(getApplicationContext(), book);
//                    UpdateBookTask updateBookTask = new UpdateBookTask(book.getTitle(), book.getAuthor(), 1);
//                    updateBookTask.setUpdateBookDelegate(myLibraryActivity);
//                }
//        }
//    }
//
//    @Override
//    public void onUpdateBookDone(String result) {
//
//    }
}