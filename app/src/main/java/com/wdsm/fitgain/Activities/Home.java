package com.wdsm.fitgain.Activities;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.fitness.FitnessOptions;
import com.google.android.gms.fitness.data.DataType;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.wdsm.fitgain.R;
import com.wdsm.fitgain.Utils.FitnessDataUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Home extends AppCompatActivity {

    private Button bBack;
    private TextView logOut;
    private FirebaseAuth firebaseAuth;

    FitnessOptions fitnessOptions = FitnessOptions.builder()
            .addDataType(DataType.TYPE_STEP_COUNT_CUMULATIVE, FitnessOptions.ACCESS_READ)
            .build();
    String dataType = Manifest.permission.ACTIVITY_RECOGNITION;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        firebaseAuth = FirebaseAuth.getInstance();
        bBack = (Button) findViewById(R.id.bBack);
        logOut = (TextView) findViewById(R.id.tvLogOut);

        if (FitnessDataUtils.checkAndroidPermissions(this, dataType)) {
            FitnessDataUtils.requestAndroidPermissions(this,dataType);
        }

        if (!FitnessDataUtils.checkGoogleFitPermissions(this, fitnessOptions)) {
            FitnessDataUtils.requestGoogleFitPermissions(this, this, fitnessOptions);
        }

        Timestamp startDate = new Timestamp(new Date());
        Timestamp endDate = new Timestamp(new Date());

        // set startDate and endDate values for testing purposes
        // replace with data from Firebase
        SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy hh:mm:ss");
        String startDateString = "21-01-2022 21:16:00";
        String endDateString = "21-01-2022 22:35:00";

        try {
            startDate = new Timestamp(sdf.parse(startDateString));
            endDate = new Timestamp(sdf.parse(endDateString));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        System.out.println(FitnessDataUtils.getActivityValueFromRange(this, startDate, endDate, DataType.TYPE_STEP_COUNT_DELTA));

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
    }
}