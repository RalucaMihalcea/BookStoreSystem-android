package com.example.raluca.storebooksystem.Activities.a;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.raluca.storebooksystem.R;

import de.hdodenhof.circleimageview.CircleImageView;
import model.User;

public class HomeActivity extends AppCompatActivity  {

    private HomeActivity homeActivity;

    private String authorURL;
    private CircleImageView circleImageWells, circleImageJVerne, circleImageWShakespeare;
    private User userAfterLogin;
    private CardView cardBestOf, cardDrama, cardFantasy, cardRomance, cardComedy, cardNonfiction, cardAction, cardLiterature, cardPsychology, cardChildren;
   // private RelativeLayout best_ofLayout,dramaLayout, fantasyLayout, romanceLayout, comedyLayout, nonfictionLayout, adventureLayout, literatureLayout, psychologyLayout, childrenLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        homeActivity = this;


        Intent intent = getIntent();
//        Bundle bundle = intent.getExtras();
        userAfterLogin = (User) intent.getSerializableExtra("userAfterLogin");

        cardBestOf=(CardView)findViewById(R.id.cardBestOf);
        cardDrama=(CardView)findViewById(R.id.cardDrama);
        cardFantasy=(CardView)findViewById(R.id.cardFantasy);
        cardRomance=(CardView)findViewById(R.id.cardRomance);
        cardComedy=(CardView)findViewById(R.id.cardComedy);
        cardNonfiction=(CardView)findViewById(R.id.cardNonfiction);
        cardAction=(CardView)findViewById(R.id.cardAction);
        cardLiterature=(CardView)findViewById(R.id.cardLiterature);
        cardPsychology=(CardView)findViewById(R.id.cardPsychology);
        cardChildren=(CardView)findViewById(R.id.cardChildren);
        circleImageWells=(CircleImageView)findViewById(R.id.circleImageWells);
        circleImageWShakespeare=(CircleImageView)findViewById(R.id.circleImageWShakespeare);
        circleImageJVerne=(CircleImageView)findViewById(R.id.circleImageJVerne);
       //best_ofLayout=(RelativeLayout)findViewById(R.id.best_ofLayout);
       // Glide.with(this).load("https://docs.google.com/uc?export=download&id=1QzVMHfUhMMnUPK--ZLiH-xEMzbAK6TiG").into(best_ofLayout);

        Glide.with(this).load("https://docs.google.com/uc?export=download&id=1tOGQjHUFQvBKR2GZsv67jo0boLHE1on_").into(circleImageWells);
        Glide.with(this).load("https://docs.google.com/uc?export=download&id=1VwvnRRP9oxagUPdPNxtUN_zEm85hqLYA").into(circleImageWShakespeare);
        Glide.with(this).load("https://docs.google.com/uc?export=download&id=1BtevaABnvaRwYwDd0RFrBkIotybE6ky2").into(circleImageJVerne);

        circleImageWells.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                authorURL="https://ro.wikipedia.org/wiki/H._G._Wells";
                Intent intent = new Intent(HomeActivity.this, AuthorActivity.class);
                intent.putExtra("authorURL", authorURL);

                startActivity(intent);

            }
        });
        circleImageWShakespeare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                authorURL="https://ro.wikipedia.org/wiki/William_Shakespeare";
                Intent intent = new Intent(HomeActivity.this, AuthorActivity.class);
                intent.putExtra("authorURL", authorURL);

                startActivity(intent);

            }
        });
        circleImageJVerne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                authorURL="https://ro.wikipedia.org/wiki/Jules_Verne";
                Intent intent = new Intent(HomeActivity.this, AuthorActivity.class);
                intent.putExtra("authorURL", authorURL);

                startActivity(intent);

            }
        });

        cardBestOf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(HomeActivity.this, BooksByCategory.class);
                intent.putExtra("userAfterLogin", userAfterLogin);
                intent.putExtra("category", "BestOf");
                intent.putExtra("nameOfCover", "allBooks");
                startActivity(intent);

            }
        });
        cardDrama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(HomeActivity.this, BooksByCategory.class);
                intent.putExtra("userAfterLogin", userAfterLogin);
                intent.putExtra("category", "Drama");
                intent.putExtra("nameOfCover", "drama");
                startActivity(intent);

            }
        });
        cardFantasy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(HomeActivity.this, BooksByCategory.class);
                intent.putExtra("userAfterLogin", userAfterLogin);
                intent.putExtra("category", "SF/Fantasy");
                intent.putExtra("nameOfCover", "fantasy");
                startActivity(intent);

            }
        });
        cardRomance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(HomeActivity.this, BooksByCategory.class);
                intent.putExtra("userAfterLogin", userAfterLogin);
                intent.putExtra("category", "Love");
                intent.putExtra("nameOfCover", "rromance");
                startActivity(intent);

            }
        });
        cardComedy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(HomeActivity.this, BooksByCategory.class);
                intent.putExtra("userAfterLogin", userAfterLogin);
                intent.putExtra("category", "Comedy");
                intent.putExtra("nameOfCover", "comedy");
                startActivity(intent);

            }
        });
        cardNonfiction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(HomeActivity.this, BooksByCategory.class);
                intent.putExtra("userAfterLogin", userAfterLogin);
                intent.putExtra("category", "Nonfiction");
                intent.putExtra("nameOfCover", "nnonfiction");
                startActivity(intent);

            }
        });
        cardAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(HomeActivity.this, BooksByCategory.class);
                intent.putExtra("userAfterLogin", userAfterLogin);
                intent.putExtra("category", "Action");
                intent.putExtra("nameOfCover", "adventure");
                startActivity(intent);

            }
        });
        cardLiterature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(HomeActivity.this, BooksByCategory.class);
                intent.putExtra("userAfterLogin", userAfterLogin);
                intent.putExtra("category", "Literature");
                intent.putExtra("nameOfCover", "literature");
                startActivity(intent);

            }
        });
        cardPsychology.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(HomeActivity.this, BooksByCategory.class);
                intent.putExtra("userAfterLogin", userAfterLogin);
                intent.putExtra("category", "Psychology");
                intent.putExtra("nameOfCover", "psychology");
                startActivity(intent);

            }
        });
        cardChildren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(HomeActivity.this, BooksByCategory.class);
                intent.putExtra("userAfterLogin", userAfterLogin);
                intent.putExtra("category", "Children");
                intent.putExtra("nameOfCover", "cchildren");
                startActivity(intent);

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

            case R.id.myLibrary_id:
                Intent intent2 = new Intent(HomeActivity.this, MyLibraryActivity.class);
                intent2.putExtra("userAfterLogin", userAfterLogin);
                startActivity(intent2);
                return true;

            case R.id.LogOut_id:
                Intent intent3 = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(intent3);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

