package com.chetan.stackoverflow.ui.tags;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

public class TagsViewModel extends ViewModel {

    private static final String TAG = "TagsViewModel";

    @Inject
    public TagsViewModel(){
        Log.d(TAG, "TagsViewModel: view model working...");
    }
}
