package com.example.raluca.storebooksystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import model.User;

public class HomeActivity extends AppCompatActivity {
    private TextView textEdit;
    private User userAfterLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        textEdit = (TextView) findViewById(R.id.textViewH);

        Intent intent = getIntent();
        //Bundle bundle = intent.getExtras();

        userAfterLogin = (User) intent.getSerializableExtra("userAfterLogin");
        textEdit.setText("Hello " + userAfterLogin.getUsername() + "!");

//        if (bundle != null) {
//            String username = (String) bundle.get("username");
//            textEdit.setText("Hello " + username + "!");
//        }
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
                //Toast.makeText(getApplicationContext(), "Profile icon is selected", Toast.LENGTH_SHORT).show();
                //Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
                // intent = new Intent(HomeActivity.this, ProfileActivity.class);
                //intent.putExtra("userAfterLogin", userAfterLogin);

                Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
                intent.putExtra("userAfterLogin", userAfterLogin);
                startActivity(intent);
                return true;

            case R.id.logOut_id:
                //Toast.makeText(getApplicationContext(), "Log Out icon is selected", Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(intent2);
                return true;

            case R.id.shopping_id:
                Toast.makeText(getApplicationContext(), "Shopping icon is selected", Toast.LENGTH_SHORT).show();
//                Intent intent3 = new Intent(HomeActivity.this, LoginActivity.class);
//                startActivity(intent3);
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }

    }
}
