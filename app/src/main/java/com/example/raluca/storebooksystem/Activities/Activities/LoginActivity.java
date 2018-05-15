package com.example.raluca.storebooksystem.Activities.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.raluca.storebooksystem.R;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import manager.DataManager;
import model.Book;
import model.User;
import webservice.LoginDelegate;
import webservice.LoginTask;

public class LoginActivity extends AppCompatActivity implements LoginDelegate {

    private ProgressBar progressBarSpinner;
    private CheckBox checkBox_RememberMe;
    private CardView btnSignIn;
    private EditText editTextUsername;
    private EditText editTextPassword;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private Boolean saveLogin;
    private LoginActivity loginActivity;
    private TextView m_textViewSignUp;
    private String username;
    private String password;
    private User userAfterLogin;
    private List<Book> books = new ArrayList<>();
    private ImageView imageView;
    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        loginActivity = this;

        getLoginPreferences();
        imageView = (ImageView) findViewById(R.id.imageView);
        progressBarSpinner = (ProgressBar) findViewById(R.id.progressBar);
        btnSignIn = (CardView) findViewById(R.id.cardViewLogin);
        progressBarSpinner.setVisibility(View.GONE);
        m_textViewSignUp = (TextView) findViewById(R.id.textViewSignUp);

        //imageView.setImageDrawable("https://openclipart.org/download/98143/Mihai-Eminescu-b-w.svg");

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isNetworkConnected()) {
                    Toast.makeText(LoginActivity.this, "Please check your internet connection!", Toast.LENGTH_SHORT).show();
                } else {

                    username = editTextUsername.getText().toString();
                    password = editTextPassword.getText().toString();
                    progressBarSpinner.setVisibility(View.VISIBLE);

                    Encryption sj = new Encryption();
                    String hash = sj.MD5(password);

//                    if (checkBox_RememberMe.isChecked()) {
//                        // remember username and password
//                        loginPrefsEditor.putBoolean("saveLogin", true);
//                        loginPrefsEditor.putString("username", username);
//                        loginPrefsEditor.putString("password", password);
//                        loginPrefsEditor.commit();
//
//                    } else {
//                        loginPrefsEditor.clear();
//                        loginPrefsEditor.commit();
//                    }

                    Log.i(TAG, "Check user data for authentication with username: " + username + " and password: " + hash);
                    LoginTask loginTask = null;
                    try {
                        loginTask = new LoginTask(username, hash);
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }
                    loginTask.setLoginDelegate(loginActivity);
//
//                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
//                    intent.putExtra("Username", username);
//                    startActivity(intent);

                }
            }
        });


        m_textViewSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);

                //Toast.makeText(getApplicationContext(), "Am intrat in actiune", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void startNewActivity(User user) {
        Intent myIntent;

        if (!user.getUsername().isEmpty() && !user.getFirstName().isEmpty()) {
//            myIntent = new Intent(LoginActivity.this, HomeActivity.class);
//            myIntent.putExtra("username", user.getUsername());
//            myIntent.putExtra("password", user.getPassword());

            userAfterLogin = user;
            myIntent = new Intent(LoginActivity.this, HomeActivity.class);
            myIntent.putExtra("userAfterLogin", userAfterLogin);

            startActivity(myIntent);
        }
    }
//
//    public void serve(View v)
//    {
//        m_textViewSignUp = (TextView) findViewById(R.id.textViewSignUp);
//        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
//        startActivity(intent);
//    }

    public void getLoginPreferences() {

        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        checkBox_RememberMe = (CheckBox) findViewById(R.id.checkBoxRememberMe);

        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();

        saveLogin = loginPreferences.getBoolean("saveLogin", false);
        if (saveLogin == true) {
            editTextUsername.setText(loginPreferences.getString("username", ""));
            editTextPassword.setText(loginPreferences.getString("password", ""));
            checkBox_RememberMe.setChecked(true);
        }
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    @Override
    public void onLoginDone(String result) throws UnsupportedEncodingException {

        progressBarSpinner.setVisibility(View.INVISIBLE);
        Log.d(TAG, "LOGIN DONE DELEGATE " + result);

        if (!result.isEmpty()) {
            User user = DataManager.getInstance().parseUser(result);

            String baseAuthStr = username + ":" + password;
            String str = "Basic " + Base64.encodeToString(baseAuthStr.getBytes("UTF-8"), Base64.DEFAULT);
            DataManager.getInstance().setBaseAuthStr(str);

            if (checkBox_RememberMe.isChecked()) {
                // remember username and password
                loginPrefsEditor.putBoolean("saveLogin", true);
                loginPrefsEditor.putString("username", username);
                loginPrefsEditor.putString("password", password);
                loginPrefsEditor.commit();

            } else {
                loginPrefsEditor.clear();
                loginPrefsEditor.commit();
            }
            startNewActivity(user);

            Toast.makeText(getApplicationContext(), "Success login", Toast.LENGTH_SHORT).show();

//            SelectBooksTask selectBooksTask = new SelectBooksTask();
//            selectBooksTask.setSelectBooksDelegate(loginActivity);
        } else {
            Toast.makeText(getApplicationContext(), "Fail login", Toast.LENGTH_SHORT).show();
        }
    }
//
//    @Override
//    public void onSelectBooksDone(String result) throws UnsupportedEncodingException {
//
//        if (!result.isEmpty() && !result.equals("[]\n")) {
//
//            books = DataManager.getInstance().parseBooks(result);
//            DataManager.getInstance().setBooksList(books);
//
//            for (Book book : books)
//                if (book.getNotified() == 0) {
//                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//                    StrictMode.setThreadPolicy(policy);
//                    Properties properties = new Properties();
//                    //Properties pr= System.getProperties();
//                    properties.put("mail.smtp.host", "smtp.live.com");
//                    properties.put("mail.smtp.auth", "true");
//                    properties.put("mail.smtp.starttls.enable", "true");
//
//                    try {
//                        Session session = Session.getDefaultInstance(properties, new Authenticator() {
//                            @Override
//                            protected PasswordAuthentication getPasswordAuthentication() {
//                                return new PasswordAuthentication(DataManager.getInstance().getEmail(), DataManager.getInstance().getPassword());
//                            }
//                        });
//
//                        if (session != null) {
//                            Message message = new MimeMessage(session);
//                            message.setFrom(new InternetAddress(DataManager.getInstance().getEmail()));
//                            message.setSubject("Testare email trimis din android");
//                            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("andreea.mihalcea.n@gmail.com"));
//                            message.setContent("Hello world from android studio!", "text/html; charset=utf-8");
//                            Transport transport = session.getTransport("smtp");
//                            transport.connect("smtp.live.com", 587, DataManager.getInstance().getEmail(), DataManager.getInstance().getPassword());
//                            transport.sendMessage(message, message.getAllRecipients());
//                            transport.close();
//                        }
//
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//
//                    UpdateBookTask updateBookTask = new UpdateBookTask(book.getTitle(), book.getAuthor(), 1);
//                    updateBookTask.setUpdateBookDelegate(loginActivity);
//                }
//
//        }
//    }
//
//    @Override
//    public void onUpdateBookDone(String result) {
//
//    }
}
