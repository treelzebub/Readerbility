package com.treelzebub.readerbility.api;

import com.treelzebub.readerbility.api.model.Article;
import com.treelzebub.readerbility.api.model.Bookmark;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by Tre Murillo on 2/27/15.
 */
public interface ReadabilityApi {

    @GET("/articles/")
    List<Article> getArticles();

    @GET("/articles/{article_id}")
    Article getArticle(@Path("article_id") String article_id);

    @GET("/bookmarks/")
    List<Bookmark> getBookmarks();

    @GET("/bookmarks/{bookmark_id}")
    Bookmark  getBookmark(@Path("bookmark_id") String bookmark_id);

    @GET("/bookmarks/{bookmark_id}/tags")
    String[] getBookmarkTags(@Path("bookmark_id") String bookmark_id);

}