package com.treelzebub.readerbility.thing;

/**
 * Created by Tre Murillo on 2/28/15.
 */
public class Article {
    public String url, domain, id, title, content, bookmark_id, favorite, archive, read_percent;

    public Article(String title, String url) {
        this.title = title;
        this.url = url;
    }
}
