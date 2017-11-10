package com.example.raluca.storebooksystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import model.User;
import webservice.UpdateDelegate;
import webservice.UpdateTask;

public class ProfileActivity extends AppCompatActivity implements UpdateDelegate {
    private EditText m_editTextUsername;
    private EditText m_editTextFirstName;
    private EditText m_editTextLastName;
    private EditText m_editTextEmail;
    private EditText m_editTextContactNo;
    private EditText m_editTextAddress;
    private Button m_buttonUpdate;
    private User userAfterLogin;

    private ProfileActivity profileActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        m_editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        m_editTextFirstName = (EditText) findViewById(R.id.editTextFirstName);
        m_editTextLastName = (EditText) findViewById(R.id.editTextLastName);
        m_editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        m_editTextContactNo = (EditText) findViewById(R.id.editTextContactNumber);
        m_editTextAddress = (EditText) findViewById(R.id.editTextAddress);
        m_buttonUpdate = (Button) findViewById(R.id.updateButton);

        Intent intent = getIntent();
        userAfterLogin = (User) intent.getSerializableExtra("userAfterLogin");

        m_editTextUsername.setText(userAfterLogin.getUsername());
        m_editTextFirstName.setText(userAfterLogin.getFirstName());
        m_editTextLastName.setText(userAfterLogin.getLastName());
        m_editTextEmail.setText(userAfterLogin.getEmail());
        m_editTextContactNo.setText(userAfterLogin.getContactNumber());
        m_editTextAddress.setText(userAfterLogin.getAddress());

        m_buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UpdateTask updateTask = new UpdateTask(userAfterLogin.getUsername(), userAfterLogin.getPassword(), m_editTextFirstName.getText().toString(), m_editTextLastName.getText().toString(), m_editTextEmail.getText().toString(), m_editTextContactNo.getText().toString(), m_editTextAddress.getText().toString());
                updateTask.setUpdateDelegate(profileActivity);
                Toast.makeText(ProfileActivity.this, "Updated profile! ", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onUpdateDone(String result) {

    }

    @Override
    public void onUpdateError(String errorMsg) {
        Toast.makeText(ProfileActivity.this, errorMsg, Toast.LENGTH_SHORT).show();

    }
}
