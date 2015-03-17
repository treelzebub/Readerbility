package com.treelzebub.readerbility.api.model;

import org.joda.time.DateTime;

/**
 * Created by Tre Murillo on 3/16/15.
 */
public class Bookmarks {
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
}
