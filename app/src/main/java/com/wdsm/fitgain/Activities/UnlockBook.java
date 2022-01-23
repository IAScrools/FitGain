package com.wdsm.fitgain.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.wdsm.fitgain.R;

public class UnlockBook extends AppCompatActivity {
    private EditText bookContent;
    private Button bBack;
    private Button logOut;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unlock_book);

        bookContent = (EditText) findViewById(R.id.BookContent);
        bookContent.setKeyListener(null);
        Bundle b = getIntent().getExtras();
        String content = b.getString("Content");
        bookContent.setText(content);

        firebaseAuth = FirebaseAuth.getInstance();

        bBack = (Button) findViewById(R.id.bBack);
        logOut = (Button) findViewById(R.id.bLogOut);

        bBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UnlockBook.this, Products.class));
            }
        });

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                startActivity(new Intent(UnlockBook.this, Login.class));
            }
        });
    }
}