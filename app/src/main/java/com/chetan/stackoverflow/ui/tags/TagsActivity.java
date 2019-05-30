package com.chetan.stackoverflow.ui.tags;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.chetan.stackoverflow.R;
import com.chetan.stackoverflow.model.Items;
import com.chetan.stackoverflow.model.Tags;
import com.chetan.stackoverflow.ui.Resource;
import com.chetan.stackoverflow.viewmodels.ViewModelProviderFactory;

import java.util.List;

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

        subscribeObservers();

    }

    private void subscribeObservers() {
        viewModel.observePost().observe(this, new Observer<Resource<Tags>>() {
            @Override
            public void onChanged(Resource<Tags> listResource) {
                if (listResource != null) {
                    switch (listResource.status) {
                        case ERROR: {
                            Log.d(TAG, "onChanged: " + listResource.message);
                            break;
                        }
                        case LOADING: {
                            Log.d(TAG, "onChanged: LOADING...");
                            break;
                        }
                        case SUCCESS: {
                            Log.d(TAG, "onChanged: " + listResource.data.getTags().size() + " "
                                    + listResource.data.getTags().get(0).getName());
                            break;
                        }
                    }
                }
            }
        });
    }
}
