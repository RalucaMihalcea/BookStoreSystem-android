package com.example.raluca.storebooksystem.Activities.a;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.raluca.storebooksystem.R;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import manager.DataManager;
import model.User;
import webservice.LoginDelegate;
import webservice.LoginTask;
import webservice.RegisterDelegate;
import webservice.RegisterTask;
import webservice.SelectUserDelegate;
import webservice.SelectUserTask;
import webservice.UpdateDelegate;
import webservice.UpdateTask;

public class ProfileActivity extends AppCompatActivity implements UpdateDelegate, LoginDelegate, RegisterDelegate, SelectUserDelegate {
    private EditText m_editTextUsername;
    private EditText m_editTextFirstName;
    private EditText m_editTextLastName;
    private EditText m_editTextEmail;
    private EditText m_editTextContactNo;
    private CardView m_buttonUpdate;
    private User userAfterLogin;
    private EditText m_errorInfo;
    private Boolean ok = false, okUsername = false;
    private static final String TAG = "ProfileActivity";


    private ProfileActivity profileActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        profileActivity = this;

        m_editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        m_editTextFirstName = (EditText) findViewById(R.id.editTextFirstName);
        m_editTextLastName = (EditText) findViewById(R.id.editTextLastName);
        m_editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        m_editTextContactNo = (EditText) findViewById(R.id.editTextContactNumber);
        m_buttonUpdate = (CardView) findViewById(R.id.cardViewUpdate);
        m_errorInfo = (EditText) findViewById(R.id.errorInfo2);

        Intent intent = getIntent();
        userAfterLogin = (User) intent.getSerializableExtra("userAfterLogin");

        m_editTextUsername.setText(userAfterLogin.getUsername());
        m_editTextFirstName.setText(userAfterLogin.getFirstName());
        m_editTextLastName.setText(userAfterLogin.getLastName());
        m_editTextEmail.setText(userAfterLogin.getEmail());
        m_editTextContactNo.setText(userAfterLogin.getContactNumber());
        m_errorInfo.setVisibility(View.INVISIBLE);
        m_errorInfo.setEnabled(false);


        m_buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                m_errorInfo.setText("");

                if (m_editTextUsername.getText().toString().isEmpty() || m_editTextFirstName.getText().toString().toString().isEmpty() || m_editTextLastName.getText().toString().isEmpty() || m_editTextEmail.getText().toString().isEmpty() || m_editTextContactNo.getText().toString().isEmpty()) {
                    m_errorInfo.setText("Complete all fields!!");
                    m_errorInfo.setVisibility(View.VISIBLE);

                } else {

//                    if (m_editTextContactNo.getText().toString().matches(regexStr))
//                        ok = true;

                    if (!contactNumberValid(m_editTextContactNo.getText().toString())) {
                        m_errorInfo.setText(m_errorInfo.getText().toString() + "Invalid phone number. ");
                        m_errorInfo.setVisibility(View.VISIBLE);
                    }


                    if ((m_editTextEmail.getText().toString().endsWith("@yahoo.com") || m_editTextEmail.getText().toString().endsWith("@gmail.com")) /*&& ok.equals(Boolean.TRUE)*/) {

                        m_errorInfo.setVisibility(View.INVISIBLE);
                        Log.i(TAG, "Select user by username: " + userAfterLogin.getUsername());
                        SelectUserTask selectUserTask = new SelectUserTask(m_editTextUsername.getText().toString());
                        selectUserTask.setSelectUserDelegate(profileActivity);

//                        Log.i(TAG, "Update user with new credentials: " + m_editTextUsername.getText().toString() + ", " + m_editTextFirstName.getText().toString() + ", " + m_editTextLastName.getText().toString() + ", " + userAfterLogin.getPassword().toString() + ", " + m_editTextEmail.getText().toString() + ", " + m_editTextContactNo.getText().toString());
//                        UpdateTask updateTask = new UpdateTask(userAfterLogin.getUsername(), m_editTextUsername.getText().toString(), m_editTextFirstName.getText().toString(), m_editTextLastName.getText().toString(), m_editTextEmail.getText().toString(), m_editTextContactNo.getText().toString());
//                        updateTask.setUpdateDelegate(profileActivity);

                        // if (okUsername == true) {

                        Log.i(TAG, "Check user data for authentication with username: " + userAfterLogin.getUsername() + " and password: " + userAfterLogin.getPassword());
                        LoginTask loginTask = null;

                        Encryption sj = new Encryption();
                        String hash = sj.MD5(userAfterLogin.getPassword());

                        try {
                            loginTask = new LoginTask(userAfterLogin.getUsername(), hash);
                        } catch (NoSuchAlgorithmException e) {
                            e.printStackTrace();
                        }
                        loginTask.setLoginDelegate(profileActivity);
                        // Toast.makeText(ProfileActivity.this, "Updated profile! ", Toast.LENGTH_SHORT).show();
                        //}

                    } else {
                        if (!m_editTextEmail.getText().toString().endsWith("@yahoo.com") && !m_editTextEmail.getText().toString().endsWith("@gmail.com")) {
                            m_errorInfo.setText("Email is invalid!");
                            m_errorInfo.setVisibility(View.VISIBLE);
                            m_editTextEmail.setText("");
                        }
//                         else if (ok.equals(Boolean.FALSE)) {
//                            m_errorInfo.setText("The phone number is invalid!");
//                            m_errorInfo.setVisibility(View.VISIBLE);
//                            m_editTextContactNo.setText("");
//                        }

                    }

                }
            }
        });
    }

    private Boolean contactNumberValid(String contactNo) {

        String expression = "^([0-9\\+]|\\(\\d{1,3}\\))[0-9\\-\\. ]{3,15}$";
        CharSequence inputString = contactNo;
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputString);
        if (matcher.matches()) {
            return true;
        } else {
            return false;
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("userAfterLogin", userAfterLogin);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void onUpdateDone(String result) {

        Log.d(TAG, "UPDATE DONE DELEGATE " + result);
//        m_errorInfo.setVisibility(View.INVISIBLE);
//        Log.i(TAG, "Select user by username: " + userAfterLogin.getUsername());
//        SelectUserTask selectUserTask = new SelectUserTask(userAfterLogin.getUsername());
//        selectUserTask.setSelectUserDelegate(profileActivity);

    }

    @Override
    public void onUpdateError(String errorMsg) {
        Toast.makeText(ProfileActivity.this, errorMsg, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onLoginDone(String result) throws UnsupportedEncodingException {

        //Toast.makeText(ProfileActivity.this, "Updated profile! ", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "LOGIN DONE DELEGATE " + result);

        if (!result.isEmpty()) {
            User user = DataManager.getInstance().parseUser(result);
            userAfterLogin = user;
        }
    }

    @Override
    public void onRegisterDone(String result) {

        Log.d(TAG, "SELECT USER DONE DELEGATE " + result);
        if (!result.isEmpty()) {
            okUsername = false;
            Toast.makeText(ProfileActivity.this, "This username already exists. Please try with another username!", Toast.LENGTH_SHORT).show();
        } else {

            okUsername = true;
            Log.i(TAG, "Register new user with username: " + userAfterLogin.getUsername() + ", firstName: " + userAfterLogin.getFirstName() + " lastName: " + userAfterLogin.getLastName() + " password: " + userAfterLogin.getPassword());

            RegisterTask registerTask = new RegisterTask(userAfterLogin.getUsername(), userAfterLogin.getFirstName(), userAfterLogin.getLastName(), userAfterLogin.getPassword(), "", "", "");
            registerTask.setRegisterDelegate(profileActivity);
            Toast.makeText(ProfileActivity.this, "Your have registered! ", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRegisterError(String response) {

    }

    @Override
    public void onSelectUserDone(String result) {

        Log.d(TAG, "SELECT USER DONE DELEGATE " + result);
        if (!result.isEmpty()) {
            m_errorInfo.setText("This username already exists!! Try again!");
            m_errorInfo.setVisibility(View.VISIBLE);
            Toast.makeText(ProfileActivity.this, "This username already exists. Please try with another username!", Toast.LENGTH_SHORT).show();
        } else {
            Log.i(TAG, "Update user with new credentials: " + m_editTextUsername.getText().toString() + ", " + m_editTextFirstName.getText().toString() + ", " + m_editTextLastName.getText().toString() + ", " + userAfterLogin.getPassword().toString() + ", " + m_editTextEmail.getText().toString() + ", " + m_editTextContactNo.getText().toString());


            UpdateTask updateTask = new UpdateTask(userAfterLogin.getUsername(), m_editTextUsername.getText().toString(), m_editTextFirstName.getText().toString(), m_editTextLastName.getText().toString(), m_editTextEmail.getText().toString(), m_editTextContactNo.getText().toString());
            updateTask.setUpdateDelegate(profileActivity);
            Toast.makeText(ProfileActivity.this, "Updated profile! ", Toast.LENGTH_SHORT).show();
        }

    }
}
