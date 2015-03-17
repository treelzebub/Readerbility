package com.treelzebub.readerbility.api.model;

import org.joda.time.DateTime;

/**
 * Created by Tre Murillo on 2/27/15.
 */
public class Bookmark {
    int userId;
    float readPercent;
    DateTime dateUpdated;
    boolean isFavorite;
    Article article;

    int id;
    DateTime dateArchived, dateOpened, dateAdded;
    String archiveHref;
    DateTime dateFavorited;
    boolean isArchived;
    Tags tags;

    String[] acceptedFilters = new String[]{
            "archive", "favorite", "domain", "added_since",
            "added_until", "opened_since", "opened_until", "archived_since",
            "archived_until", "updated_since", "updated_until", "page", "per_page",
            "only_deleted", "tags"
    };
}
