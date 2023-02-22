package com.card.my;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_DELAY = 3000; // 3 seconds

    private boolean isAdmin(String username, String password) {
        //TODO: Add code to check if the user is an admin
        //For example, you can check if the user's credentials match with a list of admin users
        if(username.equals("admin") && password.equals("password")) {
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SharedPreferences sharedPreferences = getSharedPreferences("userCredentials", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");
        String password = sharedPreferences.getString("password", "");

        if (isAdmin(username, password)) {
            //User is admin, redirect to AdminActivity
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(SplashActivity.this, AdminActivity.class));
                    finish();
                }
            }, SPLASH_DELAY);
        } else {
            //User is not admin, redirect to LoginActivity
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
                }
            }, SPLASH_DELAY);
        }
    }
}
