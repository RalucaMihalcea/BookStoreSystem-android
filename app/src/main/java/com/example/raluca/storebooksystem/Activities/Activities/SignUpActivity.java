package com.example.raluca.storebooksystem.Activities.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.raluca.storebooksystem.R;

import webservice.RegisterDelegate;
import webservice.RegisterTask;
import webservice.SelectUserDelegate;
import webservice.SelectUserTask;

public class SignUpActivity extends AppCompatActivity implements RegisterDelegate, SelectUserDelegate {

    private EditText m_editTextUsername;
    private EditText m_editTextFirstName;
    private EditText m_editTextLastName;
    private EditText m_editTextPassword;
    private EditText m_editTextRetypePassword;
    private CardView m_buttonSubmit;
    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private String repeatPassword;
    private SignUpActivity signUpActivity;
    private EditText m_errorInfo;
    private static final String TAG = "SignUpActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signUpActivity = this;
        m_editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        m_editTextFirstName = (EditText) findViewById(R.id.editTextFirstName);
        m_editTextLastName = (EditText) findViewById(R.id.editTextLastName);
        m_editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        m_editTextRetypePassword = (EditText) findViewById(R.id.editTextRetypePassword);
        m_buttonSubmit = (CardView) findViewById(R.id.cardViewSubmit);
        m_errorInfo = (EditText) findViewById(R.id.errorInfo);
        m_errorInfo.setVisibility(View.INVISIBLE);
        m_errorInfo.setEnabled(false);

        m_buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                username = m_editTextUsername.getText().toString();
                firstName = m_editTextFirstName.getText().toString();
                lastName = m_editTextLastName.getText().toString();
                password = m_editTextPassword.getText().toString();
                repeatPassword = m_editTextRetypePassword.getText().toString();


                if (username.isEmpty() || password.isEmpty() || repeatPassword.isEmpty() || firstName.isEmpty() || lastName.isEmpty()) {
                    m_errorInfo.setText("Complete all fields!!");
                    m_errorInfo.setVisibility(View.VISIBLE);

                } else {

                    if (password.equals(repeatPassword)) {

                        m_errorInfo.setVisibility(View.INVISIBLE);
                        Log.i(TAG, "Select user by username: " + username);
                        SelectUserTask selectUserTask = new SelectUserTask(username);
                        selectUserTask.setSelectUserDelegate(signUpActivity);

                    } else {
                        m_errorInfo.setText("These password don't match!! Try again!!");
                        m_errorInfo.setVisibility(View.VISIBLE);
                        m_editTextPassword.setText("");
                        m_editTextRetypePassword.setText("");
                    }

                }
            }
        });
    }

    @Override
    public void onRegisterDone(String result) {
    }

    @Override
    public void onRegisterError(String errorMsg) {
        Toast.makeText(SignUpActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSelectUserDone(String result) {
        Log.d(TAG, "SELECT USER DONE DELEGATE " + result);
        if (!result.isEmpty())
            Toast.makeText(SignUpActivity.this, "This username already exists. Please try with another username!", Toast.LENGTH_SHORT).show();
        else {
            Log.i(TAG, "Register new user with username: " + username + ", firstName: " + firstName + " lastName: " + lastName + " password: " + password);

            Encryption sj = new Encryption();
            String hash = sj.MD5(password);
            System.out.println("The MD5 (hexadecimal encoded) hash is:" + hash);

           // RegisterTask registerTask = new RegisterTask(username, firstName, lastName, password, "", "", "");
            RegisterTask registerTask = new RegisterTask(username, firstName, lastName, hash, "", "", "");
            registerTask.setRegisterDelegate(signUpActivity);
            Toast.makeText(SignUpActivity.this, "Your have registered! ", Toast.LENGTH_SHORT).show();
        }
    }
}
