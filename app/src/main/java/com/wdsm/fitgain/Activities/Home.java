package com.wdsm.fitgain.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.wdsm.fitgain.R;

public class Home extends AppCompatActivity {

    private Button bBack;
    private TextView logOut;
    private FirebaseAuth firebaseAuth;
    private Button toProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        firebaseAuth = FirebaseAuth.getInstance();
        bBack = (Button) findViewById(R.id.bBack);
        logOut = (TextView) findViewById(R.id.tvLogOut);
        toProducts = (Button) findViewById(R.id.kButton);

        bBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, Home.class));
            }
        });

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                startActivity(new Intent(Home.this, Login.class));
            }
        });

        toProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Home.this, Products.class));
            }
        });
    }


}