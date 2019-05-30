package com.chetan.stackoverflow.ui.tags;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.chetan.stackoverflow.model.Items;
import com.chetan.stackoverflow.model.Tags;
import com.chetan.stackoverflow.network.tags.TagsApi;
import com.chetan.stackoverflow.ui.Resource;
import com.chetan.stackoverflow.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class TagsViewModel extends ViewModel {

    private static final String TAG = "TagsViewModel";

    private TagsApi tagsApi;

    private MediatorLiveData<Resource<Tags>> tags;

    @Inject
    public TagsViewModel(TagsApi tagsApi){
        Log.d(TAG, "TagsViewModel: view model working...");

        if(tagsApi != null) {
            this.tagsApi = tagsApi;
        }else{
            Log.d(TAG, "TagsViewModel: Items api null...");
        }

    }


    public LiveData<Resource<Tags>> observePost(){
        Log.d(TAG, "observePost: observe post");
        if(tags == null){
            tags = new MediatorLiveData<>();
            tags.setValue(Resource.loading((Tags) null));

            Log.d(TAG, "observePost: .....");

            final LiveData<Resource<Tags>> source = LiveDataReactiveStreams.fromPublisher(
                    tagsApi.getTags(
                            "1",
                            "40",
                            Constants.ORDER,
                            Constants.SORT,
                            Constants.SITE_STACKOVERFLOW
                    )
                            .onErrorReturn(new Function<Throwable, Tags>() {
                                @Override
                                public Tags apply(Throwable throwable) throws Exception {
                                    Log.e(TAG, "apply: " + throwable );
                                    Items tag = new Items("UNKNOWN" , -1 , false);
                                    ArrayList<Items> tags = new ArrayList<>();
                                    Log.d(TAG, "apply: " + throwable.getMessage() + " " + throwable.getLocalizedMessage());
                                    tags.add(tag);
                                    Tags mTag = new Tags();
                                    mTag.setTags(tags);
                                    return mTag;
                                }
                            })

                           .map(new Function<Tags, Resource<Tags>>() {
                               @Override
                               public Resource<Tags> apply(Tags tags) throws Exception {
                                   if(tags.getTags().size() > 0){
                                       if(tags.getTags().get(0).getCount() == -1){
                                           return Resource.error("Some thing went wrong!", null);
                                       }
                                   }
                                   return Resource.success(tags);
                               }
                           })
                            .subscribeOn(Schedulers.io())
            );
            tags.addSource(source, new Observer<Resource<Tags>>() {
                @Override
                public void onChanged(Resource<Tags> listResource) {
                    tags.setValue(listResource);
                    tags.removeSource(source);
                }
            });
        }
        return tags;
    }
}
