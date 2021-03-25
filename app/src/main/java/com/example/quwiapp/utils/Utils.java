package com.example.quwiapp.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.view.inputmethod.InputMethodManager;

import androidx.preference.PreferenceManager;

import com.example.quwiapp.R;
import com.example.quwiapp.ui.SigninActivity;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class Utils {

    public static final String UserToken = "usertoken";

    public static ProgressDialog pDialog;

    public static boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean isOnline(Context context) {

        boolean result = false; // Returns connection type. 0: none; 1: mobile data; 2: wifi
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (cm != null) {
                NetworkCapabilities capabilities = cm.getNetworkCapabilities(cm.getActiveNetwork());
                if (capabilities != null) {
                    if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                        result = true;
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                        result = true;
                    }
                }
            }
        } else {
            if (cm != null) {
                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                if (activeNetwork != null) {
                    // connected to the internet
                    if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                        result = true;
                    } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                        result = true;
                    }
                }
            }
        }
        return result;

    }


    public static void showProgress(Context context) {
        try {
            if (pDialog != null && pDialog.isShowing()) {
                if (!((Activity) context).isFinishing()) {
                    pDialog.dismiss();
                }
            }
            pDialog = new ProgressDialog(context);
            if (!((Activity) context).isFinishing()) {
                pDialog.show();
                pDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                pDialog.setContentView(R.layout.progress_dialog);
                pDialog.setCancelable(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void hideProgress() {
        if (pDialog != null && pDialog.isShowing()) {
            pDialog.dismiss();

        }
    }

    public static void setStringPreference(Context context, String key, String value) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getStringPreference(Context context, String key, String defValue) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        return sharedPreferences.getString(key, defValue);
    }


    public static void removePreference(Context context, String key) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.apply();
    }

    public static boolean isContainPreference(Context context, String key) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        return sharedPreferences.contains(key);
    }

    public static void logoutUser(Activity context){
        Utils.removePreference(context, Utils.UserToken);
        context.startActivity(new Intent(context, SigninActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
        context.finish();
    }


    public static void hideKeyboard(Activity context){
        try {
            InputMethodManager imm = (InputMethodManager)context.getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(context.getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

