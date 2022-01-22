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

public class ProductDetails extends AppCompatActivity {

    private EditText bookFullInfo;
    private Button unlockBook;
    private Button bBack;
    private TextView logOut;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        Bundle b = getIntent().getExtras();
        String book = b.getString("Book");

        firebaseAuth = FirebaseAuth.getInstance();

        bookFullInfo = (EditText) findViewById(R.id.BookFullInfo);
        unlockBook = (Button) findViewById(R.id.UnlockBook);
        bBack = (Button) findViewById(R.id.bBack);
        logOut = (TextView) findViewById(R.id.tvLogOut);
        bookFullInfo.setKeyListener(null);
        bookFullInfo.setText(book);

        bBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProductDetails.this, Products.class));
            }
        });

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                startActivity(new Intent(ProductDetails.this, Login.class));
            }
        });
    }
}