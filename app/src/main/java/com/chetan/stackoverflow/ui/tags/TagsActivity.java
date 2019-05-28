package com.chetan.stackoverflow.ui.tags;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.chetan.stackoverflow.R;
import com.chetan.stackoverflow.viewmodels.ViewModelProviderFactory;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class TagsActivity extends DaggerAppCompatActivity {

    private static final String TAG = "TagsActivity";

    @Inject
    ViewModelProviderFactory providerFactory;

    private TagsViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tags);
        getSupportActionBar().hide();

        Log.d(TAG, "onCreate: Activity started...");

        viewModel = ViewModelProviders.of(this, providerFactory).get(TagsViewModel.class);
    }
}
