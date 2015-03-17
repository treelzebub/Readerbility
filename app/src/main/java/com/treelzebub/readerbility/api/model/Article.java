package com.treelzebub.readerbility.api.model;

import org.joda.time.DateTime;

import java.net.URL;

/**
 * Created by Tre Murillo on 2/28/15.
 */
public class Article {
    String domain;
    String excerpt;
    int wordcount;
    boolean isProcessed;
    int id;
    String title;
    URL leadImageUrl;
    String author;
    Direction direction;
    DateTime datePublished;
    String dek;
    URL url;

    private enum Direction{
        LTR, RTL
    }

}
