package com.chetan.stackoverflow.network.tags;

import io.reactivex.Flowable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TagsApi {

    @GET("tags")
    Flowable<Response> getTags(
            @Query("page") int page,
            @Query("pagesize") int pageSize,
            @Query("order") String order,
            @Query("sort") String sort,
            @Query("site") String site
    );
}
