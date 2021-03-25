package com.example.quwiapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.quwiapp.databinding.ActivitySplashBinding;
import com.example.quwiapp.ui.ProjectListActivity;
import com.example.quwiapp.ui.SigninActivity;
import com.example.quwiapp.utils.Utils;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 2000;
    private Intent intent;
    Handler handler;
    Runnable runnable;

    ActivitySplashBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {

                if(Utils.isContainPreference(SplashActivity.this, Utils.UserToken)){
                    intent = new Intent(SplashActivity.this, ProjectListActivity.class);
                }else {
                    intent = new Intent(SplashActivity.this, SigninActivity.class);
                }
                startActivity(intent);
                finish();

            }
        };
        handler.postDelayed(runnable, SPLASH_TIME_OUT);
    }
}
