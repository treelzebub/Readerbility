package com.treelzebub.readerbility.util;

import com.treelzebub.readerbility.thing.Bookmark;

import java.util.List;

import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by Tre Murillo on 2/27/15.
 */
public interface ReadabilityService {


    @GET("GET /api/rest/v1/bookmarks/{bookmark_id}")
    List<Bookmark> getBookmark(@Path("bookmark_id") String bookmark_id);
}
