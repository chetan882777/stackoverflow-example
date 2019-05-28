package com.chetan.stackoverflow.di;

import android.app.Application;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;

import com.chetan.stackoverflow.R;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    @Singleton
    @Provides
    static Drawable provideAppDrawable(Application application) {
        return ContextCompat.getDrawable(application, R.mipmap.logo);
    }
}
