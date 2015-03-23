package com.treelzebub.readerbility.api.model;

import java.util.List;

/**
 * Created by Tre Murillo on 3/16/15.
 */
public class Tags {
    List<Tag> tags;

    private class Tag {
        int id;
        String tag;
        int appliedCount; // num of bookmarks with this tag
        int[] bookmarkIds; //is of size [appliedCount]
    }
}
