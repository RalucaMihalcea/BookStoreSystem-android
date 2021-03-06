package com.example.raluca.storebooksystem.Activities.a;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
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
import webservice.SelectBookByCategoryDelegate;
import webservice.SelectBookByCategoryTask;
import webservice.SelectBooksDelegate;
import webservice.SelectBooksTask;

public class BooksByCategory extends AppCompatActivity implements SelectBookByCategoryDelegate, SelectBooksDelegate {

    private RecyclerView recyclerView;
    private BooksAdapter adapter;
    private List<Book> books = new ArrayList<>();
    private List<Book> booksListAll = new ArrayList<>();
    private List<Book> books2;
    private List<String> titleBooks = new ArrayList<>();
    private List<String> titleBooks2;
    private BooksByCategory booksByCategory;
    private User userAfterLogin;
    private List<String> covers = new ArrayList<>();
    private List<String> covers2;
    private Resources resources;
    private String category;
    private String nameOfCover;
    private TextView titleTextView;
    private String titleCover;
    private ImageView m_imageView;
    private AutoCompleteTextView m_autoCompleteTextView;
    private Book searchBook;
    private ImageView m_refresh;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayAdapter<String> adapterTitleBooks;
    private static final String TAG = "BooksByCategory";
    private List<FavoriteBook> favoriteBooks;
    private Book book;
    private List<Book> favoriteBookForUser = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books_by_category);
        booksByCategory = this;

        mLayoutManager = new GridLayoutManager(this, 2);

        titleTextView = (TextView) findViewById(R.id.love_music);
        m_autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        m_refresh = (ImageView) findViewById(R.id.refreshImage);

        m_autoCompleteTextView.setVisibility(View.INVISIBLE);
        m_refresh.setVisibility(View.INVISIBLE);

        Intent intent = getIntent();
        userAfterLogin = (User) intent.getSerializableExtra("userAfterLogin");

//        SelectFavoriteBooksByUserTask selectFavoriteBooksByUserTask = new SelectFavoriteBooksByUserTask(userAfterLogin.getUsername());
//        selectFavoriteBooksByUserTask.setSelectFavoriteBooksByUserDelegate(booksByCategory);

        m_imageView = (ImageView) findViewById(R.id.backdrop);
        resources = this.getResources();

        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            category = (String) bundle.get("category");
            titleCover = (String) bundle.get("titleCover");
            nameOfCover = (String) bundle.get("nameOfCover");
        }

        titleTextView.setText(titleCover);

        if (!category.equals("BestOf")) {
            Log.i(TAG, "Select book by category: " + category);
            SelectBookByCategoryTask selectBookByCategoryTask = new SelectBookByCategoryTask(category);
            selectBookByCategoryTask.setSelectBookByCategoryDelegate(booksByCategory);
        } else {
            Log.i(TAG, "Select books task: " + booksByCategory);
            SelectBooksTask selectBooksTask = new SelectBooksTask();
            selectBooksTask.setSelectBooksDelegate(booksByCategory);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initCollapsingToolbar();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        m_autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {

                String titleSearchBook = m_autoCompleteTextView.getText().toString();
                books2 = new ArrayList<>();
                for (Book book : books)
                    if (titleSearchBook.equals(book.getTitle())) {
                        searchBook = book;
                        books2.add(searchBook);
                    }

                preparePresentationBooks2();
                adapter = new BooksAdapter(getApplicationContext(), books2, covers2, userAfterLogin);

                recyclerView.setAdapter(adapter);
            }

        });


        m_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                m_autoCompleteTextView.setText("");


                adapter = new BooksAdapter(getApplication(), books, covers, userAfterLogin);
                adapter.notifyDataSetChanged();
                recyclerView.setAdapter(adapter);

            }
        });


    }

    /**
     * Initializing collapsing toolbar
     * Will show and hide the toolbar title on scroll
     */
    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(getString(R.string.app_name));
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }

    /**
     * Adding few albums for testing
     */
    private void preparePresentationBooks() {
        int idCover;

        for (Book book : books) {
            // idCover = resources.getIdentifier(book.getNamePicture(), "drawable", this.getPackageName());
            //aici trebuie sa fac book.getImageLink();

            covers.add(book.getImageLink());
            titleBooks.add(book.getTitle());

        }

        adapter.notifyDataSetChanged();
    }

    private void preparePresentationBooks2() {
        int idCover2;
        titleBooks2 = new ArrayList<>();
        covers2 = new ArrayList<>();
        for (Book book : books2) {
            //idCover2 = resources.getIdentifier(book.getNamePicture(), "drawable", this.getPackageName());
            covers2.add(book.getImageLink());
            titleBooks2.add(book.getTitle());

        }

        adapter.notifyDataSetChanged();
    }


    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

//    @Override
//    public void onSelectFavoriteBooksByUserDone(String result) throws UnsupportedEncodingException {
//        if (!result.equals("[]\n"))
//            favoriteBooks = DataManager.getInstance().parseFavoriteBooks(result);
//
//        for (FavoriteBook favBook : favoriteBooks) {
//            SelectBookByIdTask selectBookByIdTask = new SelectBookByIdTask(favBook.getIdBook());
//            selectBookByIdTask.setSelectBookByIdDelegate(booksByCategory);
//        }
//    }
//
//    @Override
//    public void onSelectBookByIdDone(String result) throws UnsupportedEncodingException {
//        if (!result.isEmpty())
//            book= DataManager.getInstance().parseBook(result);
//        favoriteBookForUser.add(book);
//
//    }


    @Override
    public void onSelectBookByCategoryDone(String result) throws UnsupportedEncodingException {

        Log.d(TAG, "SelectBookByCategory DONE DELEGATE " + result);

        if (!result.isEmpty() && !result.equals("[]\n")) {
            m_autoCompleteTextView.setVisibility(View.VISIBLE);
            m_refresh.setVisibility(View.VISIBLE);

            books = DataManager.getInstance().parseBooks(result);

            DataManager.getInstance().setBooksList(books);
            adapter = new BooksAdapter(this, books, covers, userAfterLogin);

            mLayoutManager = new GridLayoutManager(this, 2);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(adapter);

            preparePresentationBooks();

            adapterTitleBooks = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, titleBooks);
            m_autoCompleteTextView.setAdapter(adapterTitleBooks);

            try {

                switch (nameOfCover) {
                    case "nnonfiction":
                        //Glide.with(this).load(R.drawable.nnonfiction).into((ImageView) findViewById(R.id.backdrop));
                        Glide.with(this).load("https://docs.google.com/uc?export=download&id=10lywtsNVPMInGdZjNWAsUXL3-jrvfGmf").into((ImageView) findViewById(R.id.backdrop));
                        break;
                    case "fantasy":
                        //Glide.with(this).load(R.drawable.fiction).into((ImageView) findViewById(R.id.backdrop));
                        Glide.with(this).load("https://docs.google.com/uc?export=download&id=1mUJ7bhPEhr-MLjdI-icUmZOAeVny8D0j").into((ImageView) findViewById(R.id.backdrop));
                        break;
                    case "rromance":
                        // Glide.with(this).load(R.drawable.rromance).into((ImageView) findViewById(R.id.backdrop));
                        Glide.with(this).load("https://docs.google.com/uc?export=download&id=1O96NFm3D0472o-XzS6KE8iKFjha-2ho9").into((ImageView) findViewById(R.id.backdrop));
                        break;
                    case "literature":
                        // Glide.with(this).load(R.drawable.literature).into((ImageView) findViewById(R.id.backdrop));
                        Glide.with(this).load("https://docs.google.com/uc?export=download&id=1x7T81Uul6Ayj3q_-XW_TDLD3bOQxPyWf").into((ImageView) findViewById(R.id.backdrop));
                        break;
                    case "drama":
                        // Glide.with(this).load(R.drawable.drama).into((ImageView) findViewById(R.id.backdrop));
                        Glide.with(this).load("https://docs.google.com/uc?export=download&id=1D_eiQypT1bT9wihiF_ASI2bdDM0wCYg3").into((ImageView) findViewById(R.id.backdrop));
                        break;
                    case "psychology":
                        // Glide.with(this).load(R.drawable.psychology).into((ImageView) findViewById(R.id.backdrop));
                        Glide.with(this).load("https://docs.google.com/uc?export=download&id=10hDALI-Frk6PgocwZGC2S77rFBvX1K_r").into((ImageView) findViewById(R.id.backdrop));
                        break;
                    case "adventure":
                        // Glide.with(this).load(R.drawable.adventure).into((ImageView) findViewById(R.id.backdrop));
                        Glide.with(this).load("https://docs.google.com/uc?export=download&id=19EPmq8mNgHjHgXcIK-fyP-1hX51ZhbWt").into((ImageView) findViewById(R.id.backdrop));
                        break;
                    case "comedy":
                        // Glide.with(this).load(R.drawable.comedy).into((ImageView) findViewById(R.id.backdrop));
                        Glide.with(this).load("https://docs.google.com/uc?export=download&id=1gU8q72IX4gC7lZQIVgEN-6K6IpXM4aKf").into((ImageView) findViewById(R.id.backdrop));
                        break;
                    case "cchildren":
                        // Glide.with(this).load(R.drawable.cchildren).into((ImageView) findViewById(R.id.backdrop));
                        Glide.with(this).load("https://docs.google.com/uc?export=download&id=1q6pz6Da3kBnK2vqC6J0wmE2ikHDCVpdn").into((ImageView) findViewById(R.id.backdrop));
                        break;

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            Toast.makeText(getApplicationContext(), "Get all books from database", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onSelectBooksDone(String result) throws UnsupportedEncodingException {

        Log.d(TAG, "SelectBooks DONE DELEGATE " + result);

        if (!result.isEmpty() && !result.equals("[]\n")) {
            m_autoCompleteTextView.setVisibility(View.VISIBLE);
            m_refresh.setVisibility(View.VISIBLE);

            booksListAll = DataManager.getInstance().parseBooks(result);

            // DataManager.getInstance().setBooksList(booksListAll);

            for (Book bk : booksListAll)
                if (bk.getStars() == 5)
                    books.add(bk);


            adapter = new BooksAdapter(this, books, covers, userAfterLogin);

            mLayoutManager = new GridLayoutManager(this, 2);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(adapter);

            preparePresentationBooks();

            adapterTitleBooks = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, titleBooks);
            m_autoCompleteTextView.setAdapter(adapterTitleBooks);

            Glide.with(this).load("https://docs.google.com/uc?export=download&id=1QzVMHfUhMMnUPK--ZLiH-xEMzbAK6TiG").into((ImageView) findViewById(R.id.backdrop));

            Toast.makeText(getApplicationContext(), "Get all books from database", Toast.LENGTH_SHORT).show();
        }
    }
}
