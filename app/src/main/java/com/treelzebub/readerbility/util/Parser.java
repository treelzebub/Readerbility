package com.treelzebub.readerbility.util;

import com.treelzebub.readerbility.api.model.Bookmark;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by Tre Murillo on 3/2/15.
 * Copyright(c) 2014 Level, Inc.
 */
public interface Parser {
    @GET("GET /api/rest/v1/bookmarks")
    List<Bookmark> getBookmarks();

    @GET("GET /api/rest/v1/bookmarks/{bookmark_id}")
    Bookmark getBookmark(@Path("bookmark_id") String bookmark_id);

    @POST("POST /api/rest/v1/bookmarks")
        //Response = ... "Location: {bookmark_id} ..."
    void addBookmark(@Body Bookmark bookmark, Callback<Bookmark> cb);
}
