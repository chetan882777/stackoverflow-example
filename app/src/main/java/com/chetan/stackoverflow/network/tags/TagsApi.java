package com.chetan.stackoverflow.network.tags;

import com.chetan.stackoverflow.model.tags.Tags;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TagsApi {

    @GET("tags")
    Flowable<Tags> getTags(
            @Query("page") String page,
            @Query("pagesize") String pageSize,
            @Query("order") String order,
            @Query("sort") String sort,
            @Query("site") String site
    );
}
