package com.chetan.stackoverflow.ui.tags;

import android.util.Log;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.chetan.stackoverflow.network.tags.TagsApi;
import com.chetan.stackoverflow.ui.Resource;

import javax.inject.Inject;

public class TagsViewModel extends ViewModel {

    private static final String TAG = "TagsViewModel";

    private final TagsApi tagsApi;

    @Inject
    public TagsViewModel(TagsApi tagsApi){
        Log.d(TAG, "TagsViewModel: view model working...");
        this.tagsApi = tagsApi;
    }
}
