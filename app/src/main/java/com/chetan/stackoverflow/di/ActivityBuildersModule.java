package com.chetan.stackoverflow.di;


import com.chetan.stackoverflow.di.auth.AuthModule;
import com.chetan.stackoverflow.di.auth.AuthViewModelsModule;
import com.chetan.stackoverflow.ui.auth.AuthActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuildersModule {

    @ContributesAndroidInjector(
            modules = {
                    AuthViewModelsModule.class,
                    AuthModule.class
            }
    )
    abstract AuthActivity contributeAuthActivity();


}
