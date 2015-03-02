package com.treelzebub.readerbility.util;

import com.treelzebub.readerbility.thing.Bookmark;

import java.util.List;

import retrofit.Callback;

/**
 * Created by Tre Murillo on 2/27/15.
 */

public class ReadabilityApi implements Parser {

    @Override
    public List<Bookmark> getBookmarks() {
        return null;
    }

    @Override
    public Bookmark getBookmark(String bookmark_id) {
        return null;
    }

    @Override
    public void addBookmark(Bookmark bookmark, Callback<Bookmark> cb) {

    }
}