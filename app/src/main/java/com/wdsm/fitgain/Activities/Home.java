package com.wdsm.fitgain.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.wdsm.fitgain.R;
import com.wdsm.fitgain.Utils.FitnessDataUtils;

public class Home extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();
    private Button bBack;
    private Button bDataUpdate;
    private TextView logOut;
    private TextView coins;
    private TextView stepCount;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        firebaseAuth = FirebaseAuth.getInstance();
        bBack = (Button) findViewById(R.id.bBack);
        logOut = (TextView) findViewById(R.id.tvLogOut);
        coins = findViewById(R.id.tvUserCoins);
        stepCount = findViewById(R.id.tvUserStepCount);
        bDataUpdate = findViewById(R.id.bDataUpdate);

        FitnessDataUtils.updateStepCount(this, this, TAG);
        updateUserCoinAndStepCountFromFirebase();

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

        bDataUpdate.setOnClickListener(v -> {
            FitnessDataUtils.updateStepCount(this, this, TAG);
        });
    }

    private void updateUserCoinAndStepCountFromFirebase() {
        DocumentReference dc = FitnessDataUtils.getUserFirestoreDocument();
        dc.addSnapshotListener((snapshot, e) -> {
            if (snapshot != null && snapshot.exists() && e == null) {
                Log.d(TAG, "Current data: " + snapshot.getData());
                coins.setText("Punkty: " + ((Double) snapshot.get("coins")).longValue());
                stepCount.setText("Łączna zgromadzona liczba kroków: " + snapshot.get("steps"));
            } else {
                coins.setText("Nie można wczytać danych");
                stepCount.setText("");
            }
        });
    }
}