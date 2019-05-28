package com.chetan.stackoverflow.di;

import android.app.Application;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    @Provides
    static String provideString(){
        return "this is test String";
    }


    @Provides
    static boolean getApp(Application application){
        return application == null;
    }
}
