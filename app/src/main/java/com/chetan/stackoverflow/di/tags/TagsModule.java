package com.chetan.stackoverflow.di.tags;

import com.chetan.stackoverflow.network.tags.TagsApi;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public abstract class TagsModule {

    @Provides
    static TagsApi provideAuthApi(Retrofit retrofit){
        return retrofit.create(TagsApi.class);
    }
}
