package com.example.raluca.storebooksystem;

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
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import manager.DataManager;
import model.Book;
import model.User;
import webservice.SelectBookByCategoryDelegate;
import webservice.SelectBookByCategoryTask;

public class BooksByCategory extends AppCompatActivity implements SelectBookByCategoryDelegate {

    private RecyclerView recyclerView;
    private BooksAdapter adapter;
    private List<Book> books = new ArrayList<>();
    private BooksByCategory booksByCategory;
    private User userAfterLogin;
    private List<Integer> covers = new ArrayList<>();
    private Resources resources;
    private String category;
    private String nameOfCover;
    private TextView titleTextView;
    private String titleCover;
    private ImageView m_imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books_by_category);
        booksByCategory = this;

        titleTextView=(TextView)findViewById(R.id.love_music);
        
        Intent intent = getIntent();
        userAfterLogin = (User) intent.getSerializableExtra("userAfterLogin");
        //nameOfCover = (String) intent.getSerializableExtra("nameOfCover");
        m_imageView=(ImageView)findViewById(R.id.backdrop);
        resources = this.getResources();
        
        Bundle bundle = intent.getExtras();
        
        if(bundle!=null)
        {
            category =(String) bundle.get("category");
            titleCover=(String) bundle.get("titleCover");
            nameOfCover=(String)bundle.get("nameOfCover");
        }

        titleTextView.setText(titleCover);
//        int resID = getResources().getIdentifier(nameOfCover.toString(), "drawable",  getPackageName());
//        m_imageView.setImageResource(resID);
       // m_imageView.setBackgroundResource(R.drawable.);

//        SelectBookTask selectBookTask = new SelectBookTask();
//        selectBookTask.setSelectBookDelegate(mainCategory);
        
        SelectBookByCategoryTask selectBookByCategoryTask = new SelectBookByCategoryTask(category);
        selectBookByCategoryTask.setSelectBookByCategoryDelegate(booksByCategory);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        initCollapsingToolbar();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

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
            idCover = resources.getIdentifier(book.getNamePicture(), "drawable", this.getPackageName());
            covers.add(idCover);

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

    @Override
    public void onSelectBookByCategoryDone(String result) throws UnsupportedEncodingException {
        if (!result.isEmpty()) {
            books = DataManager.getInstance().parseBooks(result);
            DataManager.getInstance().setBooksList(books);
            adapter = new BooksAdapter(this, books, covers);

            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(adapter);

            preparePresentationBooks();

            try {
                //Glide.with(this).load(R.drawable.cover).into((ImageView) findViewById(R.id.backdrop));

                switch (nameOfCover)
                {
                    case "nonfiction": Glide.with(this).load(R.drawable.nonfiction).into((ImageView) findViewById(R.id.backdrop));
                        break;
                    case "fiction": Glide.with(this).load(R.drawable.fiction).into((ImageView) findViewById(R.id.backdrop));
                        break;
                    case "love": Glide.with(this).load(R.drawable.romance).into((ImageView) findViewById(R.id.backdrop));
                        break;
                    case "literature": Glide.with(this).load(R.drawable.literature).into((ImageView) findViewById(R.id.backdrop));
                        break;
                    case "drama": Glide.with(this).load(R.drawable.drama).into((ImageView) findViewById(R.id.backdrop));
                        break;
                    case "psychology": Glide.with(this).load(R.drawable.psychology).into((ImageView) findViewById(R.id.backdrop));
                        break;
                    case "action": Glide.with(this).load(R.drawable.adventure).into((ImageView) findViewById(R.id.backdrop));
                        break;
                    case "comedy": Glide.with(this).load(R.drawable.comedy).into((ImageView) findViewById(R.id.backdrop));
                        break;
                    case "children": Glide.with(this).load(R.drawable.children).into((ImageView) findViewById(R.id.backdrop));
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            Toast.makeText(getApplicationContext(), "Get all books from database", Toast.LENGTH_SHORT).show();
        }
    }
}
