package com.chetan.stackoverflow.di;

import android.app.Application;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;
import androidx.room.Room;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.chetan.stackoverflow.R;
import com.chetan.stackoverflow.Repository.StackRepository;
import com.chetan.stackoverflow.SessionManager;
import com.chetan.stackoverflow.persistence.StackDatabase;
import com.chetan.stackoverflow.utils.Constants;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.chetan.stackoverflow.persistence.StackDatabase.DATABASE_NAME;

@Module
public class AppModule {

    @Singleton
    @Provides
    static RequestOptions provideRequestOptions() {
        return RequestOptions
                .placeholderOf(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground);
    }

    @Singleton
    @Provides
    static RequestManager provideGlideInstance(Application application, RequestOptions requestOptions){
        return Glide.with(application).setDefaultRequestOptions(requestOptions);
    }

    @Singleton
    @Provides
    static Drawable provideAppDrawable(Application application) {
        return ContextCompat.getDrawable(application, R.mipmap.logo);
    }

    @Singleton
    @Provides
    static Retrofit provideRetrofitInstance(){
        return new Retrofit.Builder()
                .baseUrl(Constants.REQUEST_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Singleton
    @Provides
    static StackRepository provideStackRepository(SessionManager sessionManager){
        return new StackRepository(sessionManager);
    }

    @Singleton
    @Provides
    static StackDatabase provideStackDatabase(Application application){
        return Room.databaseBuilder(
                application,
                StackDatabase.class,
                DATABASE_NAME
        ).build();
    }
}
