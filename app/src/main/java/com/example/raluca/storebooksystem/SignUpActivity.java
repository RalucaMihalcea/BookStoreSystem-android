package com.example.raluca.storebooksystem;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import webservice.RegisterDelegate;
import webservice.RegisterTask;

public class SignUpActivity extends AppCompatActivity implements RegisterDelegate {

    private EditText m_editTextUsername;
    private EditText m_editTextFirstName;
    private EditText m_editTextLastName;
    private EditText m_editTextPassword;
    private EditText m_editTextRetypePassword;
    private Button m_buttonSubmit;
    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private SignUpActivity signUpActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        m_editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        m_editTextFirstName = (EditText) findViewById(R.id.editTextUsername);
        m_editTextLastName = (EditText) findViewById(R.id.editTextLastName);
        m_editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        m_editTextRetypePassword = (EditText) findViewById(R.id.editTextRetypePassword);
        m_buttonSubmit = (Button) findViewById(R.id.buttonSubmit);

        m_buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                username = m_editTextUsername.getText().toString();
                firstName = m_editTextFirstName.getText().toString();
                lastName = m_editTextLastName.getText().toString();
                password = m_editTextPassword.getText().toString();

                RegisterTask registerTask = new RegisterTask(username, firstName, lastName, password);
                registerTask.setRegisterDelegate(signUpActivity);

                Toast.makeText(SignUpActivity.this, "Your have registered! ", Toast.LENGTH_SHORT).show();

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

}
