package com.chetan.stackoverflow.ui.tags;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

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
import com.chetan.stackoverflow.ui.main.MainActivity;
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
    private Button submitButton;

    private List<TagItems> mySelectedTags = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tags);
        getSupportActionBar().hide();

        recyclerView = findViewById(R.id.tags_recyclerView);
        chips = findViewById(R.id.chipGroup);
        submitButton = findViewById(R.id.button_submit);

        submitButton.setVisibility(View.GONE);

        Log.d(TAG, "onCreate: Activity started...");

        viewModel = ViewModelProviders.of(this, providerFactory).get(TagsViewModel.class);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mySelectedTags.size() == 4) {
                    viewModel.submitSelectedTags();
                }else{
                    Toast.makeText(TagsActivity.this, "4 tags should be selected", Toast.LENGTH_SHORT).show();
                }
            }
        });

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

                            listResource.data.getTags().add(new TagItems("" , 0 , false));

                            setRecyclerView(listResource.data.getTags());
                            submitButton.setVisibility(View.VISIBLE);
                            break;
                        }
                    }
                }
            }
        });

        viewModel.getSelectedTags().observe(this, new Observer<List<TagItems>>() {
            @Override
            public void onChanged(List<TagItems> tagItems) {

                for (final TagItems tag : tagItems) {

                    final Chip chip = getNewChip();

                    if(!mySelectedTags.contains(tag)) {
                        chip.setText(tag.getName());
                        chips.addView(chip);

                        chip.setOnCloseIconClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Boolean isRemoved = viewModel.removeTag(tag);
                                if (isRemoved) {
                                    chips.removeView(chip);
                                    mySelectedTags.remove(tag);
                                } else {
                                    Toast.makeText(TagsActivity.this, "Failed to remove tag", Toast.LENGTH_SHORT).show();
                                    Log.d(TAG, "onTagClicked: Failed to remove tag ...");
                                }
                            }
                        });
                    }
                }
            }
        });

        viewModel.getTagsFromDb().observe(this, new Observer<List<TagItems>>() {
            @Override
            public void onChanged(List<TagItems> tagItems) {
                if(tagItems != null){
                    Log.d(TAG, "onChanged: tags = " + tagItems.size() + " name:" + tagItems.get(0).getName());
                    Intent intent = new Intent(TagsActivity.this, MainActivity.class);
                    startActivity(intent);
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
            public void onTagClicked(final TagItems tag) {

                if(viewModel.getSelectedTags().getValue().size() < 4 ) {
                    if (!viewModel.getSelectedTags().getValue().contains(tag)) {

                        viewModel.addTag(tag);
                        mySelectedTags.add(tag);

                    } else {
                        Toast.makeText(TagsActivity.this, "Tag already added", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onTagClicked: tag already added ...");
                    }
                }else{
                    Toast.makeText(TagsActivity.this, "Max number of tags selected", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onTagClicked: Max number of tags selected ...");
                }
            }
        });
    }


    private Chip getNewChip() {
        final Chip chip = new Chip(TagsActivity.this);
        chip.setCloseIconVisible(true);
        chip.setCloseIcon(getDrawable(R.drawable.close));
        chip.setChipBackgroundColor(ColorStateList.valueOf(
                getResources().getColor(R.color.colorAccent, getTheme())
        ));
        chip.setTextColor(getResources().getColor(android.R.color.white, getTheme()));
        return chip;
    }

}
