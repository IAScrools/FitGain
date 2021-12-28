package com.wdsm.fitgain.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.wdsm.fitgain.R;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        FirebaseUser user = firebaseAuth.getCurrentUser();

        if(user == null){
            Intent intent = new Intent(Splash.this, Home.class);
        } else {

        }
    }
}