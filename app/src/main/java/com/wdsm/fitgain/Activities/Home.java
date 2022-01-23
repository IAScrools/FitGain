package com.wdsm.fitgain.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.wdsm.fitgain.R;
import com.wdsm.fitgain.Utils.FitnessDataUtils;

public class Home extends AppCompatActivity {

    private Button logOut;
    private Button unlocked;
    private TextView coins;
    private TextView stepCount;
    private FirebaseAuth firebaseAuth;
    private Button toProducts;
    private SwipeRefreshLayout srlRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        firebaseAuth = FirebaseAuth.getInstance();
        logOut = findViewById(R.id.bLogOut);
        toProducts = findViewById(R.id.kButton);
        coins = findViewById(R.id.tvUserCoins);
        stepCount = findViewById(R.id.tvUserStepCount);
        srlRefresh = findViewById(R.id.srlRefresh);
        unlocked = findViewById(R.id.bOdblokowane);

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

        FitnessDataUtils.updateStepCount(Home.this, Home.this);
        updateUserCoinAndStepCountFromFirebase();

        unlocked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, UnlockedBooks.class));
            }
        });
    }



    public void updateUserCoinAndStepCountFromFirebase() {
        DocumentReference dc = FitnessDataUtils.getUserFirestoreDocument();
        dc.addSnapshotListener((snapshot, e) -> {
            if (snapshot != null && snapshot.exists() && e == null
                    && (snapshot.get("coins")) != null
                    && (snapshot.get("steps")) != null) {
                coins.setTextSize(25);
                coins.setText("Punkty: " + ((Double) snapshot.get("coins")).longValue());
                stepCount.setText("Łączna zgromadzona liczba kroków: " + snapshot.get("steps"));
            } else {
                coins.setTextSize(16);
                coins.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                coins.setText("Nie wczytano jeszcze danych, spróbuj odświeżyć aplikację");
                stepCount.setText("");
            }
        });
    }
}