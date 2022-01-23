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
    private Button toProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        firebaseAuth = FirebaseAuth.getInstance();
        bBack = (Button) findViewById(R.id.bBack);
        logOut = (TextView) findViewById(R.id.tvLogOut);
        toProducts = (Button) findViewById(R.id.kButton);
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

        toProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Home.this, Products.class));
            }
        });

        srlRefresh.setColorSchemeResources(
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        srlRefresh.setOnRefreshListener(() -> {
            FitnessDataUtils.updateStepCount(Home.this, Home.this);
            srlRefresh.setRefreshing(false);
        });
    }

    public void updateUserCoinAndStepCountFromFirebase() {
        DocumentReference dc = FitnessDataUtils.getUserFirestoreDocument();
        dc.addSnapshotListener((snapshot, e) -> {
            if (snapshot != null && snapshot.exists() && e == null
                    && (snapshot.get("coins")) != null
                    && (snapshot.get("steps")) != null) {
                coins.setText("Punkty: " + ((Double) snapshot.get("coins")).longValue());
                stepCount.setText("Łączna zgromadzona liczba kroków: " + snapshot.get("steps"));
            } else {
                coins.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                coins.setText("Nie wczytano danych, spróbuj odświeżyć aplikację");
                stepCount.setText("");
            }
        });
    }
}