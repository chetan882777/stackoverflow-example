package com.chetan.stackoverflow.Repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.chetan.stackoverflow.SessionManager;
import com.chetan.stackoverflow.model.TokenResponse;
import com.chetan.stackoverflow.model.tags.TagItems;
import com.chetan.stackoverflow.persistence.StackDatabase;
import com.chetan.stackoverflow.ui.auth.AuthResource;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.functions.Action;

public class StackRepository {

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

    public void submitSelectedTags(List<TagItems> selectedTags) {
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {

            }
        });
    }
}
