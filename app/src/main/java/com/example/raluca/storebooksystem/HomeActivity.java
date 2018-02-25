package com.example.raluca.storebooksystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

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

public class HomeActivity extends AppCompatActivity implements SelectBookViewsAndDateByUsernameDelegate, SelectBookViewsByUsernameDelegate, SelectBookByIdDelegate, SelectBookByCategoryDelegate {

    private HomeActivity homeActivity;
    private TextView textEdit, text1, text3, title1, title2, title3, title4, author1, author2, author3, author4, textViewDescriptionInterested;
    private CardView m_cardMyFavoriteBooks, m_cardCartBookStatistics;
    private User userAfterLogin;
    private CardView m_cardCartAllBooks;
    private CardView m_cardCategory;
    private List<BookViews> bookViewsList = new ArrayList<>();
    private List<Book> booksByCategory = new ArrayList<>();
    private List<Book> randomBookList = new ArrayList<>();
    private BookViews theMostViewedBook;
    private ImageView image_view1, image_view2, image_view3, image_view4;
    private Book book, randomBook;
    private View view1, view2, view3, view4;
    private List<BookViewsAndDate> bookViewsAndDateList = new ArrayList<>();
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        homeActivity = this;

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
        m_cardCartAllBooks = (CardView) findViewById(R.id.cardCartAllBooks);
        m_cardCategory = (CardView) findViewById(R.id.cardCategory);
        m_cardCartBookStatistics = (CardView) findViewById(R.id.cardCartBookStatistics);
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
        Bundle bundle = intent.getExtras();

        userAfterLogin = (User) intent.getSerializableExtra("userAfterLogin");

        SelectBookViewsByUsernameTask selectBookViewsByUsernameTask = new SelectBookViewsByUsernameTask(userAfterLogin.getUsername());
        selectBookViewsByUsernameTask.setSelectBookViewsByUsernameDelegate(homeActivity);


        image_view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, BookActivity.class);
                intent.putExtra("book", (Serializable) randomBookList.get(0));
                //intent.putExtra("imageNumber", imageNumber);
                intent.putExtra("userAfterLogin", userAfterLogin);
                startActivity(intent);

            }
        });

        image_view2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, BookActivity.class);
                intent.putExtra("book", (Serializable) randomBookList.get(1));
                //intent.putExtra("imageNumber", imageNumber);
                intent.putExtra("userAfterLogin", userAfterLogin);
                startActivity(intent);

            }
        });
        image_view3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, BookActivity.class);
                intent.putExtra("book", (Serializable) randomBookList.get(2));
                //intent.putExtra("imageNumber", imageNumber);
                intent.putExtra("userAfterLogin", userAfterLogin);
                startActivity(intent);

            }
        });

        image_view4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, BookActivity.class);
                intent.putExtra("book", (Serializable) randomBookList.get(3));
                //intent.putExtra("imageNumber", imageNumber);
                intent.putExtra("userAfterLogin", userAfterLogin);
                startActivity(intent);

            }
        });

        m_cardMyFavoriteBooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, FavoriteBooksActivity.class);
                intent.putExtra("userAfterLogin", userAfterLogin);
                startActivity(intent);
            }
        });

        m_cardCartAllBooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(HomeActivity.this, AllBooksActivity.class);
//                startActivity(intent);

                Intent intent = new Intent(HomeActivity.this, BooksByCategory.class);
                intent.putExtra("userAfterLogin", userAfterLogin);
                intent.putExtra("category", "All");
                intent.putExtra("nameOfCover", "AllBooks");
                intent.putExtra("titleCover", "ALL BOOKS");
                startActivity(intent);
            }
        });

        m_cardCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent4 = new Intent(HomeActivity.this, CategoryMenu.class);
                intent4.putExtra("userAfterLogin", userAfterLogin);
                startActivity(intent4);
            }
        });

        m_cardCartBookStatistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SelectBookViewsAndDateByUsernameTask selectBookViewsAndDateByUsernameTask = new SelectBookViewsAndDateByUsernameTask(userAfterLogin.getUsername());
                selectBookViewsAndDateByUsernameTask.setSelectBookViewsAndDateByUsernameDelegate(homeActivity);

            }
        });
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
                Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
                intent.putExtra("userAfterLogin", userAfterLogin);
                startActivity(intent);
                return true;

            case R.id.home_id:
                return true;

            case R.id.LogOut_id:
                Intent intent3 = new Intent(HomeActivity.this, LoginActivity.class);
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
            selectBookByIdTask.setSelectBookByIdDelegate(homeActivity);
        }
    }

    @Override
    public void onSelectBookByIdDone(String result) throws UnsupportedEncodingException {

        if (!result.equals("")) {
            book = DataManager.getInstance().parseBook(result);
            SelectBookByCategoryTask selectBookByCategoryTask = new SelectBookByCategoryTask(book.getCategory());
            selectBookByCategoryTask.setSelectBookByCategoryDelegate(homeActivity);
        }
    }

    @Override
    public void onSelectBookByCategoryDone(String result) throws UnsupportedEncodingException {

        if (!result.isEmpty() && !result.equals("[]\n")) {

            booksByCategory = DataManager.getInstance().parseBooks(result);

            HomeActivity obj = new HomeActivity();
            for (int i = 0; i < 4; i++) {
                //System.out.println(obj.getRandomList(booksByCategory));
                randomBook = obj.getRandomList(booksByCategory);

                if (isDifferentBook(book, randomBook) && isValid(randomBookList, randomBook)) {
                    //Toast.makeText(getApplicationContext(), "Random book is: " + randomBook, Toast.LENGTH_SHORT).show();
                    randomBookList.add(randomBook);
                } else i--;
            }

            int resID = getResources().getIdentifier(randomBookList.get(0).getNamePicture(), "drawable", this.getPackageName());
            image_view1.setImageResource(resID);
            title1.setText(randomBookList.get(0).getTitle());
            author1.setText(randomBookList.get(0).getAuthor());

            int resID2 = getResources().getIdentifier(randomBookList.get(1).getNamePicture(), "drawable", this.getPackageName());
            image_view2.setImageResource(resID2);
            title2.setText(randomBookList.get(1).getTitle());
            author2.setText(randomBookList.get(1).getAuthor());

            int resID3 = getResources().getIdentifier(randomBookList.get(2).getNamePicture(), "drawable", this.getPackageName());
            image_view3.setImageResource(resID3);
            title3.setText(randomBookList.get(2).getTitle());
            author3.setText(randomBookList.get(2).getAuthor());

            int resID4 = getResources().getIdentifier(randomBookList.get(3).getNamePicture(), "drawable", this.getPackageName());
            image_view4.setImageResource(resID4);
            title4.setText(randomBookList.get(3).getTitle());
            author4.setText(randomBookList.get(3).getAuthor());

            text3.setText(book.getTitle());
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
                int numberMonth = bookViewsAndDate.getDate().get(Calendar.MONTH) + 1;
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
                        } else  if (sizeFeb == 1)dayBookListForStatistics.add(feb.get(0));

                        break;

                    case 3:
                        int sizeMar = mar.size();
                        if (sizeMar >= 2) {
                            Collections.sort(mar, new CustomComparator());
                            dayBookListForStatistics.add(mar.get(0));
                            dayBookListForStatistics.add(mar.get(1));
                        } else  if (sizeMar == 1) dayBookListForStatistics.add(mar.get(0));

                        break;

                    case 4:
                        int sizeApr = apr.size();
                        if (sizeApr >= 2) {
                            Collections.sort(apr, new CustomComparator());
                            dayBookListForStatistics.add(apr.get(0));
                            dayBookListForStatistics.add(apr.get(1));
                        } else  if (sizeApr == 1) dayBookListForStatistics.add(apr.get(0));

                        break;

                    case 5:
                        int sizeMay = may.size();
                        if (sizeMay >= 2) {
                            Collections.sort(may, new CustomComparator());
                            dayBookListForStatistics.add(may.get(0));
                            dayBookListForStatistics.add(may.get(1));
                        } else  if (sizeMay == 1) dayBookListForStatistics.add(may.get(0));

                        break;

                    case 6:
                        int sizeJun = jun.size();
                        if (sizeJun >= 2) {
                            Collections.sort(jun, new CustomComparator());
                            dayBookListForStatistics.add(jun.get(0));
                            dayBookListForStatistics.add(jun.get(1));
                        } else  if (sizeJun == 1) dayBookListForStatistics.add(jun.get(0));

                        break;

                    case 7:
                        int sizeJul = jul.size();
                        if (sizeJul >= 2) {
                            Collections.sort(jul, new CustomComparator());
                            dayBookListForStatistics.add(jul.get(0));
                            dayBookListForStatistics.add(jul.get(1));
                        } else  if (sizeJul == 1) dayBookListForStatistics.add(jul.get(0));

                        break;

                    case 8:
                        int sizeAug = aug.size();
                        if (sizeAug >= 2) {
                            Collections.sort(aug, new CustomComparator());
                            dayBookListForStatistics.add(aug.get(0));
                            dayBookListForStatistics.add(aug.get(1));
                        } else  if (sizeAug == 1) dayBookListForStatistics.add(aug.get(0));

                        break;

                    case 9:
                        int sizeSep = sep.size();
                        if (sizeSep >= 2) {
                            Collections.sort(sep, new CustomComparator());
                            dayBookListForStatistics.add(sep.get(0));
                            dayBookListForStatistics.add(sep.get(1));
                        } else  if (sizeSep == 1) dayBookListForStatistics.add(sep.get(0));

                        break;

                    case 10:
                        int sizeOct = oct.size();
                        if (sizeOct >= 2) {
                            Collections.sort(oct, new CustomComparator());
                            dayBookListForStatistics.add(oct.get(0));
                            dayBookListForStatistics.add(oct.get(1));
                        } else  if (sizeOct == 1) dayBookListForStatistics.add(oct.get(0));

                        break;

                    case 11:
                        int sizeNov = nov.size();
                        if (sizeNov >= 2) {
                            Collections.sort(nov, new CustomComparator());
                            dayBookListForStatistics.add(nov.get(0));
                            dayBookListForStatistics.add(nov.get(1));
                        } else  if (sizeNov == 1) dayBookListForStatistics.add(nov.get(0));

                        break;

                    case 12:
                        int sizeDec = dec.size();
                        if (sizeDec >= 2) {
                            Collections.sort(dec, new CustomComparator());
                            dayBookListForStatistics.add(dec.get(0));
                            dayBookListForStatistics.add(dec.get(1));
                        } else  if (sizeDec == 1) dayBookListForStatistics.add(dec.get(0));

                        break;
                }
            }


            Intent intent = new Intent(HomeActivity.this, StatisticsActivity.class);
            intent.putExtra("dayBookListForStatistics", (Serializable) dayBookListForStatistics);
            //intent.putExtra("userAfterLogin", userAfterLogin);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }

    }
//
//    public BookViewsAndDate returnTheMostViewedBook(List<BookViewsAndDate> list) {
//        BookViewsAndDate finalBookViewsAndDate = new BookViewsAndDate();
//        double maxViews = 0;
//
//        for (BookViewsAndDate book : list) {
//            if (book.getViews() >= maxViews) {
//                maxViews = book.getViews();
//                finalBookViewsAndDate = book;
//            }
//        }
//
//        return finalBookViewsAndDate;
//    }
//
//    public BookViewsAndDate returnSecondViewedBook(List<BookViewsAndDate> list, BookViewsAndDate firstMaxObject) {
//        BookViewsAndDate finalBook = new BookViewsAndDate();
//        double maxViews = 0;
//
//        for (BookViewsAndDate book : list) {
//            if (book != firstMaxObject) {
//                if (book.getViews() >= maxViews) {
//                    maxViews = book.getViews();
//                    finalBook = book;
//                }
//            }
//        }
//        return finalBook;
//    }
}

