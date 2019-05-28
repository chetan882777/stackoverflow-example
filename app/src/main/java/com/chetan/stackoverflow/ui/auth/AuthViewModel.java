package com.chetan.stackoverflow.ui.auth;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.chetan.stackoverflow.SessionManager;
import com.chetan.stackoverflow.model.TokenResponse;

import javax.inject.Inject;

public class AuthViewModel extends ViewModel {

    private static final String TAG = "AuthViewModel";

    private final SessionManager sessionManager;
    private MutableLiveData<AuthResource<TokenResponse>> response = new MutableLiveData<>();

    @Inject
    public AuthViewModel(SessionManager sessionManager){
        Log.d(TAG, "AuthViewModel: view model working...");
        this.sessionManager = sessionManager;
    }

    public void authenticateWithToken(TokenResponse token){
        sessionManager.authenticateWithToken(response);
        response.postValue(AuthResource.authenticated(token));
    }

    public LiveData<AuthResource<TokenResponse>>observeAuthState(){
        return sessionManager.getAuthUser();
    }


}
