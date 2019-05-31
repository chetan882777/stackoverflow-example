package com.chetan.stackoverflow.Repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.chetan.stackoverflow.SessionManager;
import com.chetan.stackoverflow.model.TokenResponse;
import com.chetan.stackoverflow.model.tags.TagItems;
import com.chetan.stackoverflow.persistence.StackDatabase;
import com.chetan.stackoverflow.ui.auth.AuthResource;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

public class StackRepository {

    private static final String TAG = "StackRepository";

    private SessionManager sessionManager;
    private StackDatabase database;

    private MutableLiveData<AuthResource<TokenResponse>> tokenResponse = new MutableLiveData<>();


    public StackRepository(SessionManager sessionManager, StackDatabase database){
        this.sessionManager = sessionManager;
        this.database = database;
    }

    public void authenticateWithToken(TokenResponse token){
        sessionManager.authenticateWithToken(tokenResponse);
        tokenResponse.postValue(AuthResource.authenticated(token));
    }

    public LiveData<AuthResource<TokenResponse>> observeAuthState(){
        return sessionManager.getAuthUser();
    }

    public void submitSelectedTags(final List<TagItems> selectedTags) {
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                database.getTagsDao().insetTags(selectedTags);
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: tags inserted ... size = " +
                        database.getTagsDao().getAllTags().getValue().size());
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onComplete: Failed to insert tags ...");
            }
        });
    }
}
