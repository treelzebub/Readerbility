package com.treelzebub.readerbility.thing;

/**
 * Created by Tre Murillo on 2/27/15.
 */
public class Bookmark {
    public int user_id;
    public float read_percent;
    public String id, article_href;
    public Article article;
//    public DateTime date_archived, date_opened, date_added, date_favorited, date_updated;
    public boolean favorite, archive;
}
