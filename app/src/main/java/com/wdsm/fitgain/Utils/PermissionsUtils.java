package com.wdsm.fitgain.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.fitness.FitnessOptions;

public class PermissionsUtils {
    public static GoogleSignInAccount getGoogleAccount(@NonNull Context content, @NonNull FitnessOptions fitnessOptions) {
        return GoogleSignIn.getAccountForExtension(content, fitnessOptions);
    }

    public static void requestGoogleFitPermissions(@NonNull Context context, @NonNull Activity activity, @NonNull GoogleSignInAccount account, @NonNull FitnessOptions fitnessOptions) {
        GoogleSignIn.requestPermissions(
                activity,
                1,
                account,
                fitnessOptions
        );
    }

    public static boolean checkGoogleFitPermissions(@NonNull Context context, @NonNull GoogleSignInAccount account, @NonNull FitnessOptions fitnessOptions) {
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
}
