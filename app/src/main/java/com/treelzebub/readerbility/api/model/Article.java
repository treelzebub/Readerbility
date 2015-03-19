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

    //Get-Set-ville
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public DateTime getDatePublished() {
        return datePublished;
    }

    public void setDatePublished(DateTime datePublished) {
        this.datePublished = datePublished;
    }

    public String getDek() {
        return dek;
    }

    public void setDek(String dek) {
        this.dek = dek;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getExcerpt() {
        return excerpt;
    }

    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isProcessed() {
        return isProcessed;
    }

    public void setIsProcessed(boolean isProcessed) {
        this.isProcessed = isProcessed;
    }

    public URL getLeadImageUrl() {
        return leadImageUrl;
    }

    public void setLeadImageUrl(URL leadImageUrl) {
        this.leadImageUrl = leadImageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public int getWordcount() {
        return wordcount;
    }

    public void setWordcount(int wordcount) {
        this.wordcount = wordcount;
    }
}
