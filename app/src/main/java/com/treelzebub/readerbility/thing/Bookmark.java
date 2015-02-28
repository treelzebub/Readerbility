package com.treelzebub.readerbility.thing;

/**
 * Created by Tre Murillo on 2/27/15.
 */
public class Bookmark {
    private String
            domain, title, url, lead_image_url, author, excerpt,
            direction, word_count, date_published, dek, id;
    private boolean processed;

    public Bookmark(String title, String date_published) {
        this.title = title;
        this.date_published = date_published;
    }

    public String getDate() {
        return date_published;
    }

    public String getTitle() {
        return title;
    }
}
