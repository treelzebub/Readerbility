package com.treelzebub.readerbility.thing;

/**
 * Created by Tre Murillo on 2/28/15.
 * Copyright(c) 2014 Level, Inc.
 */
public class Article {
    public String
            url, domain, id, title, content, bookmark_id,
            favorite, archive, read_percent;

    public Article(String title, String url) {
        //TODO prelim test
        this.title = title;
        this.url = url;
    }
}
