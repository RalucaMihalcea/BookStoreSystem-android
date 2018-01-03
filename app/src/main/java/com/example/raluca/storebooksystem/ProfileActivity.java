package com.example.raluca.storebooksystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;

import manager.DataManager;
import model.User;
import webservice.LoginDelegate;
import webservice.LoginTask;
import webservice.UpdateDelegate;
import webservice.UpdateTask;

public class ProfileActivity extends AppCompatActivity implements UpdateDelegate, LoginDelegate {
    private EditText m_editTextUsername;
    private EditText m_editTextFirstName;
    private EditText m_editTextLastName;
    private EditText m_editTextEmail;
    private EditText m_editTextContactNo;
    private CardView m_buttonUpdate;
    private User userAfterLogin;
    private EditText m_errorInfo;
    private String regexStr = "^[0-9]{10}$";
    private Boolean ok = false;

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

                if (m_editTextUsername.getText().toString().isEmpty() || m_editTextFirstName.getText().toString().toString().isEmpty() || m_editTextLastName.getText().toString().isEmpty() || m_editTextEmail.getText().toString().isEmpty() || m_editTextContactNo.getText().toString().isEmpty()) {
                    m_errorInfo.setText("Complete all fields!!");
                    m_errorInfo.setVisibility(View.VISIBLE);

                } else {

                    if (m_editTextContactNo.getText().toString().matches(regexStr))
                        ok = true;


                    if ((m_editTextEmail.getText().toString().endsWith("@yahoo.com") || m_editTextEmail.getText().toString().endsWith("@gmail.com")) && ok.equals(Boolean.TRUE)) {

                        m_errorInfo.setVisibility(View.INVISIBLE);
                        UpdateTask updateTask = new UpdateTask(m_editTextUsername.getText().toString(), m_editTextFirstName.getText().toString(), m_editTextLastName.getText().toString(), userAfterLogin.getPassword().toString(), m_editTextEmail.getText().toString(), m_editTextContactNo.getText().toString());
                        updateTask.setUpdateDelegate(profileActivity);
                        LoginTask loginTask = new LoginTask(userAfterLogin.getUsername(), userAfterLogin.getPassword());
                        loginTask.setLoginDelegate(profileActivity);
                        Toast.makeText(ProfileActivity.this, "Updated profile! ", Toast.LENGTH_SHORT).show();

                    } else {
                        if (!m_editTextEmail.getText().toString().endsWith("@yahoo.com") && !m_editTextEmail.getText().toString().endsWith("@gmail.com")) {
                            m_errorInfo.setText("Email is invalid!");
                            m_errorInfo.setVisibility(View.VISIBLE);
                            m_editTextEmail.setText("");
                        } else if (ok.equals(Boolean.FALSE)) {
                            m_errorInfo.setText("The phone number is invalid!");
                            m_errorInfo.setVisibility(View.VISIBLE);
                            m_editTextContactNo.setText("");
                        }

                    }


                }
            }
        });
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

    }

    @Override
    public void onUpdateError(String errorMsg) {
        Toast.makeText(ProfileActivity.this, errorMsg, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onLoginDone(String result) throws UnsupportedEncodingException {

        if (!result.isEmpty()) {
            User user = DataManager.getInstance().parseUser(result);
            userAfterLogin=user;
        }
    }
}
