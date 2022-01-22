package com.wdsm.fitgain.Activities;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.FitnessOptions;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.fitness.request.DataReadRequest;
import com.google.android.gms.fitness.result.DataReadResponse;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.wdsm.fitgain.R;
import com.wdsm.fitgain.Utils.FitnessDataUtils;
import com.wdsm.fitgain.Utils.PermissionsUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class Home extends AppCompatActivity {

    private Button bBack;
    private TextView logOut;
    private FirebaseAuth firebaseAuth;
    private final String TAG = this.getClass().getSimpleName();

    String androidPermissions = Manifest.permission.ACTIVITY_RECOGNITION;
    FitnessOptions fitnessOptions = FitnessOptions.builder()
            .addDataType(DataType.TYPE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
            .build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        firebaseAuth = FirebaseAuth.getInstance();
        bBack = (Button) findViewById(R.id.bBack);
        logOut = (TextView) findViewById(R.id.tvLogOut);

        if (PermissionsUtils.checkAndroidPermissions(this, androidPermissions)) {
            PermissionsUtils.requestAndroidPermissions(this, androidPermissions);
        }

        GoogleSignInAccount account
                = PermissionsUtils.getGoogleAccount(this, fitnessOptions);

        if (!PermissionsUtils.checkGoogleFitPermissions(account, fitnessOptions)) {
            PermissionsUtils.requestGoogleFitPermissions(this, account, fitnessOptions);
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

        FitnessDataUtils.getStepCountDeltaHistoryClient(this, account, startDate, endDate)
                .addOnSuccessListener(response -> {
                    Long total = 0L;
                    DataSet dataSet = response.getDataSet(DataType.TYPE_STEP_COUNT_DELTA);
                    for (DataPoint dataPoint : dataSet.getDataPoints()) {
                        total += dataPoint.getValue(Field.FIELD_STEPS).asInt();
                    }
                    Log.i(TAG, Long.toString(total));
                    // access step count here
                });

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