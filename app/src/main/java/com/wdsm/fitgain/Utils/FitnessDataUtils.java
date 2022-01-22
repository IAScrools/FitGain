package com.wdsm.fitgain.Utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class FitnessDataUtils {
    public static final FitnessOptions getStepCountDeltaFitnessOptions() {
        return FitnessOptions.builder()
                .addDataType(DataType.TYPE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
                .build();
    }

    public static Task<DataReadResponse> getStepCountDeltaHistoryClient(
            @NonNull Context context,
            @NonNull GoogleSignInAccount account,
            @NonNull Timestamp startDate,
            @NonNull Timestamp endDate) {

        DataReadRequest dataReadRequest = new DataReadRequest.Builder()
                .read(DataType.TYPE_STEP_COUNT_DELTA)
                .setTimeRange(
                        startDate.toDate().getTime(),
                        endDate.toDate().getTime(),
                        TimeUnit.MILLISECONDS)
                .build();

        return Fitness.getHistoryClient(context, account)
                .readData(dataReadRequest);
    }

    public static DocumentReference getUserFirestoreDocument() {
        return FirebaseFirestore.getInstance().collection("Users")
                .document(FirebaseAuth.getInstance().getCurrentUser().getUid());
    }

    public static boolean checkStepCountPermissions(@NonNull Context context,
                                                    @NonNull Activity activity,
                                                    @NonNull GoogleSignInAccount account) {

        String androidPermissions = Manifest.permission.ACTIVITY_RECOGNITION;
        FitnessOptions fitnessOptions = getStepCountDeltaFitnessOptions();

        if (!PermissionsUtils.checkAndroidPermissions(context, androidPermissions)) {
            PermissionsUtils.requestAndroidPermissions(activity, androidPermissions);
        }

        if (!PermissionsUtils.checkGoogleFitPermissions(account, fitnessOptions)) {
            PermissionsUtils.requestGoogleFitPermissions(activity, account, fitnessOptions);
        }

        if (!PermissionsUtils.checkAndroidPermissions(context, androidPermissions)
                || !PermissionsUtils.checkGoogleFitPermissions(account, fitnessOptions)) {
            return false;
        }

        return true;
    }

    public static void updateStepCount (
            @NonNull Context context,
            @NonNull Activity activity,
            @NonNull String TAG
            ) {

        FitnessOptions fitnessOptions = getStepCountDeltaFitnessOptions();
        GoogleSignInAccount account = PermissionsUtils.getGoogleAccount(context, fitnessOptions);

        if (!checkStepCountPermissions(context, activity, account)) {
            Log.w(TAG, "Permissions denied!");
            return;
        }

        DocumentReference dc = getUserFirestoreDocument();

        dc.get().addOnSuccessListener( response -> {
            Timestamp startDate = new Timestamp(new Date());
            Timestamp endDate = new Timestamp(new Date());
            try {
            startDate = response.getTimestamp("lastCheck");
            } catch (RuntimeException e) {
                // TODO set custom (e.g. today at 00:00:00) Timestamp if not exist on Firestore
                Log.i(TAG, "Error occured: " + e.getMessage());
                e.printStackTrace();
            }
            updateStepsAndCoins(context, account, startDate, endDate, dc);
        });
    }

    private static void updateStepsAndCoins(
            @NonNull Context context,
            @NonNull GoogleSignInAccount account,
            @NonNull Timestamp startDate,
            @NonNull Timestamp endDate,
            @NonNull DocumentReference dc) {
        FitnessDataUtils.getStepCountDeltaHistoryClient(context, account, startDate, endDate)
                .addOnSuccessListener(response -> {
                    Long total = 0L;
                    DataSet dataSet = response.getDataSet(DataType.TYPE_STEP_COUNT_DELTA);
                    for (DataPoint dataPoint : dataSet.getDataPoints()) {
                        total += dataPoint.getValue(Field.FIELD_STEPS).asInt();
                    }
                    // update Firestore
                    dc.update("steps", FieldValue.increment(total));
                    dc.update("coins", FieldValue.increment(total / 100));
                    dc.update("lastCheck", endDate);
                });
    }
}
