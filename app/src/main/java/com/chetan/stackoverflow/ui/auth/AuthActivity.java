package com.chetan.stackoverflow.ui.auth;

import android.os.Bundle;
import android.util.Log;

import com.chetan.stackoverflow.R;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class AuthActivity extends DaggerAppCompatActivity {

    private static final String TAG = "AuthActivity";

    @Inject
    String testString;


    @Inject
    boolean isAppNull;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_auth);


            Log.d(TAG, "onCreate: testString: " + testString);
            Log.d(TAG, "onCreate: is App null: " + isAppNull);
    }
}
