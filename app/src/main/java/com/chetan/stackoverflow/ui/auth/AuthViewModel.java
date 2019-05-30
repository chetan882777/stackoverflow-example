package com.chetan.stackoverflow.ui.auth;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.chetan.stackoverflow.Repository.StackRepository;
import com.chetan.stackoverflow.model.TokenResponse;

import javax.inject.Inject;

public class AuthViewModel extends ViewModel {

    private static final String TAG = "AuthViewModel";

    private final StackRepository repository;

    @Inject
    public AuthViewModel(StackRepository repository){
        Log.d(TAG, "AuthViewModel: view model working...");
        this.repository = repository;
    }

    public void authenticateWithToken(TokenResponse token){
        repository.authenticateWithToken(token);
    }

    public LiveData<AuthResource<TokenResponse>>observeAuthState(){
        return repository.observeAuthState();
    }


}
