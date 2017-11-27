package com.example.raluca.storebooksystem;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BookActivity extends AppCompatActivity {

    private TextView txt;
    private LinearLayout m_linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        txt=(TextView)findViewById(R.id.textView);
        m_linearLayout=(LinearLayout)findViewById(R.id.lineraLayoutBook);

        Bundle bundle = getIntent().getExtras();
        String b = bundle.getString("str");

        txt.setText("Buna "+b);

        int iddPicture=R.drawable.last_sirenn;
        int id1=R.drawable.big_fish;
        int id2=R.drawable.moon_crossedd;

        // m_linearLayout.setBackground(Drawable.createFromPath("drawable/last_sirenn.jpg"));
        if(b.equals("cardView2"))
            //m_linearLayout.setBackgroundResource(R.drawable.last_sirenn);
            m_linearLayout.setBackgroundResource(iddPicture);


    }
}
