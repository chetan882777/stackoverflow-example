package com.chetan.stackoverflow;


import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import com.chetan.stackoverflow.model.TokenResponse;
import com.chetan.stackoverflow.ui.auth.AuthResource;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SessionManager {

    private static final String TAG = "DaggerExample";

    // data
    private MediatorLiveData<AuthResource<TokenResponse>> cachedUser = new MediatorLiveData<>();

    @Inject
    public SessionManager() {}

    public void authenticateWithToken(final LiveData<AuthResource<TokenResponse>> source) {
        if(cachedUser != null){
            cachedUser.setValue(AuthResource.loading((TokenResponse) null));
            cachedUser.addSource(source, new Observer<AuthResource<TokenResponse>>() {
                @Override
                public void onChanged(AuthResource<TokenResponse> userAuthResource) {
                    cachedUser.setValue(userAuthResource);
                    cachedUser.removeSource(source);
                }
            });
        }
    }

    public void logOut() {
        Log.d(TAG, "logOut: logging out...");
        cachedUser.setValue(AuthResource.<TokenResponse>logout());
    }


    public LiveData<AuthResource<TokenResponse>> getAuthUser(){
        return cachedUser;
    }

}
