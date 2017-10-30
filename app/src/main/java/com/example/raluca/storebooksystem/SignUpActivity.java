package com.example.raluca.storebooksystem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class SignUpActivity extends AppCompatActivity {

    private EditText m_editTextUsername;
    private EditText m_editTextFirstName;
    private EditText m_editTextLastName;
    private EditText m_editTextPassword;
    private EditText m_editTextRetypePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        m_editTextUsername=(EditText)findViewById(R.id.editTextUsername);
        m_editTextFirstName=(EditText)findViewById(R.id.editTextUsername);
        m_editTextLastName=(EditText)findViewById(R.id.editTextLastName);
        m_editTextPassword=(EditText)findViewById(R.id.editTextPassword);
        m_editTextRetypePassword=(EditText)findViewById(R.id.editTextRetypePassword);


    }
}
