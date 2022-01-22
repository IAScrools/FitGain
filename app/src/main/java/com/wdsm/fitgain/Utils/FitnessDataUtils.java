package com.wdsm.fitgain.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.FitnessOptions;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.fitness.request.DataReadRequest;
import com.google.firebase.Timestamp;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class FitnessDataUtils {
    public static GoogleSignInAccount getGoogleAccount(@NonNull Context content, @NonNull FitnessOptions fitnessOptions) {
        return GoogleSignIn.getAccountForExtension(content, fitnessOptions);
    }

    public static void requestGoogleFitPermissions(@NonNull Context context, @NonNull Activity activity, @NonNull FitnessOptions fitnessOptions) {
        GoogleSignInAccount account = getGoogleAccount(context, fitnessOptions);
        GoogleSignIn.requestPermissions(
                activity,
                1,
                account,
                fitnessOptions
        );
    }

    public static boolean checkGoogleFitPermissions(@NonNull Context context, @NonNull FitnessOptions fitnessOptions) {
            GoogleSignInAccount account = getGoogleAccount(context, fitnessOptions);
            return GoogleSignIn.hasPermissions(account, fitnessOptions);
    }

    public static void requestAndroidPermissions(@NonNull Activity activity, @NonNull String... permissions) {
        activity.requestPermissions(
                permissions,
                1);
    }

    public static boolean checkAndroidPermissions(@NonNull Context context, @NonNull String... permissions) {
        for (String permission : permissions) {
            if (!(ContextCompat.checkSelfPermission(
                    context, permission) ==
                    PackageManager.PERMISSION_GRANTED)) {
                return false;
            }
        }
        return true;
    }

    public static long getActivityValueFromRange(@NonNull Context context, @NonNull Timestamp startDate, @NonNull Timestamp endDate, @NonNull DataType dataType) {
        AtomicLong total = new AtomicLong();

        Fitness.getHistoryClient(context, GoogleSignIn.getLastSignedInAccount(context))
                .readData(new DataReadRequest.Builder()
                        .read(dataType)
                        .setTimeRange(startDate.toDate().getTime(), endDate.toDate().getTime(), TimeUnit.MILLISECONDS)
                        .build())
                .addOnSuccessListener(response -> {
                    DataSet dataSet = response.getDataSet(dataType);
                    for (DataPoint dataPoint : dataSet.getDataPoints()) {
                        total.addAndGet(dataPoint.getValue(Field.FIELD_STEPS).asInt());
                    }
                });
        return total.get();
    }
}
