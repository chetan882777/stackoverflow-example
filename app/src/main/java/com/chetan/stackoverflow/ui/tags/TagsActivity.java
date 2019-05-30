package com.chetan.stackoverflow.ui.tags;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chetan.stackoverflow.R;
import com.chetan.stackoverflow.adapter.TagsAdapter;
import com.chetan.stackoverflow.model.tags.TagItems;
import com.chetan.stackoverflow.model.tags.Tags;
import com.chetan.stackoverflow.ui.Resource;
import com.chetan.stackoverflow.viewmodels.ViewModelProviderFactory;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;


import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class TagsActivity extends DaggerAppCompatActivity {

    private static final String TAG = "TagsActivity";

    @Inject
    ViewModelProviderFactory providerFactory;

    private RecyclerView recyclerView;
    private TagsViewModel viewModel;
    private ChipGroup chips;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tags);
        getSupportActionBar().hide();

        recyclerView = findViewById(R.id.tags_recyclerView);
        chips = findViewById(R.id.chipGroup);

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

                            setRecyclerView(listResource.data.getTags());
                            break;
                        }
                    }
                }
            }
        });
    }

    private void setRecyclerView(List<TagItems> tags) {
        TagsAdapter adapter = new TagsAdapter(this, tags);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        adapter.setTagClickListener(new TagsAdapter.TagClickListener() {
            @Override
            public void onTagClicked(TagItems tag) {
                final Chip chip = new Chip(TagsActivity.this);
                chip.setText(tag.getName());
                chip.setCloseIconVisible(true);
                chip.setCloseIcon(getDrawable(R.drawable.close));
                chip.setChipBackgroundColor(ColorStateList.valueOf(
                        getResources().getColor(R.color.colorAccent, getTheme())
                ));
                chip.setTextColor(getResources().getColor(android.R.color.white, getTheme()));
                chips.addView(chip);

                chip.setOnCloseIconClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        chips.removeView(chip);
                    }
                });
            }
        });
    }
}
