package com.chetan.stackoverflow.di.auth;

import com.chetan.stackoverflow.utils.Constants;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public abstract class AuthModule {

    @Singleton
    @Provides
    static String provideTokenUrl(){
        return Constants.OAUTH_URL +
                "?client_id=" + Constants.CLIENT_ID +
                "&scope=" + "read_inbox" +
                "&redirect_uri=" + Constants.REDIRECT_URI;
    }
}
