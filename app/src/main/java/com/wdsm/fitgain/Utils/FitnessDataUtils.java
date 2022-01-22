package com.wdsm.fitgain.Utils;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.request.DataReadRequest;
import com.google.android.gms.fitness.result.DataReadResponse;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;

import java.util.concurrent.TimeUnit;

public class FitnessDataUtils {
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
}

