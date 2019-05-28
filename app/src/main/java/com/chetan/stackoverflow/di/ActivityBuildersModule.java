package com.chetan.stackoverflow.di;


import com.chetan.stackoverflow.di.auth.AuthModule;
import com.chetan.stackoverflow.di.auth.AuthViewModelsModule;
import com.chetan.stackoverflow.di.tags.TagsModule;
import com.chetan.stackoverflow.di.tags.TagsViewModelsModule;
import com.chetan.stackoverflow.ui.auth.AuthActivity;
import com.chetan.stackoverflow.ui.tags.TagsActivity;

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

    @ContributesAndroidInjector(
            modules = {
                    TagsViewModelsModule.class,
                    TagsModule.class
            }
    )
    abstract TagsActivity contributeTagsActivity();


}
