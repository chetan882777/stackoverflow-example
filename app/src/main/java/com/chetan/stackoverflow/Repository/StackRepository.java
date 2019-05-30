package com.chetan.stackoverflow.Repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.chetan.stackoverflow.SessionManager;
import com.chetan.stackoverflow.model.TokenResponse;
import com.chetan.stackoverflow.ui.auth.AuthResource;

public class StackRepository {

    private SessionManager sessionManager;
    private MutableLiveData<AuthResource<TokenResponse>> tokenResponse = new MutableLiveData<>();

    public StackRepository(SessionManager sessionManager){
        this.sessionManager = sessionManager;
    }

    public void authenticateWithToken(TokenResponse token){
        sessionManager.authenticateWithToken(tokenResponse);
        tokenResponse.postValue(AuthResource.authenticated(token));
    }

    public LiveData<AuthResource<TokenResponse>> observeAuthState(){
        return sessionManager.getAuthUser();
    }
}
