package com.treelzebub.readerbility.api.model;

import org.joda.time.DateTime;

/**
 * Created by Tre Murillo on 2/27/15.
 */
public class Bookmark {
    private int userId;
    private float readPercent;
    private DateTime dateUpdated;
    private boolean isFavorite;
    private Article article;

    private int id;
    private DateTime dateArchived;
    private DateTime dateOpened;
    private DateTime dateAdded;
    private String archiveHref;
    private DateTime dateFavorited;
    private boolean isArchived;
    private Tags tags;

    static final String[] acceptedFilters = new String[]{
            "archive", "favorite", "domain", "added_since",
            "added_until", "opened_since", "opened_until", "archived_since",
            "archived_until", "updated_since", "updated_until", "page", "per_page",
            "only_deleted", "tags"
    };

    //Get-Set-ville

    public String getArchiveHref() {
        return archiveHref;
    }

    public void setArchiveHref(String archiveHref) {
        this.archiveHref = archiveHref;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public DateTime getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(DateTime dateAdded) {
        this.dateAdded = dateAdded;
    }

    public DateTime getDateArchived() {
        return dateArchived;
    }

    public void setDateArchived(DateTime dateArchived) {
        this.dateArchived = dateArchived;
    }

    public DateTime getDateFavorited() {
        return dateFavorited;
    }

    public void setDateFavorited(DateTime dateFavorited) {
        this.dateFavorited = dateFavorited;
    }

    public DateTime getDateOpened() {
        return dateOpened;
    }

    public void setDateOpened(DateTime dateOpened) {
        this.dateOpened = dateOpened;
    }

    public DateTime getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(DateTime dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isArchived() {
        return isArchived;
    }

    public void setIsArchived(boolean isArchived) {
        this.isArchived = isArchived;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(boolean isFavorite) {
        this.isFavorite = isFavorite;
    }

    public float getReadPercent() {
        return readPercent;
    }

    public void setReadPercent(float readPercent) {
        this.readPercent = readPercent;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Tags getTags() {
        return tags;
    }

    public void setTags(Tags tags) {
        this.tags = tags;
    }
}
