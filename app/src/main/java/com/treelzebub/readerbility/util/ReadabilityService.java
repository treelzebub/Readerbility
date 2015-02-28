package com.treelzebub.readerbility.util;

import com.treelzebub.readerbility.thing.Bookmark;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by Tre Murillo on 2/27/15.
 */
public interface ReadabilityService {

    @POST("POST /api/rest/v1/bookmarks")
    void addBookmark(@Body Bookmark bookmark, Callback<Bookmark> cb);

    @GET("GET /api/rest/v1/bookmarks/{bookmark_id}")
    List<Bookmark> getBookmark(@Path("bookmark_id") String bookmark_id);
}
