package com.treelzebub.readerbility.api;

import com.treelzebub.readerbility.api.model.Bookmark;

import org.scribe.model.Token;

import java.util.List;

/**
 * Created by Tre Murillo on 3/17/15
 *
 * A singleton that describes an authenticated Readability session
 */

public class Readability {
    private List<Bookmark> library;
    private Token token;

    public void setToken(Token token) {
        this.token = token;
    }

    public Token getToken() {
        return token;
    }

    public List<Bookmark> getLibrary() {
        return library;
    }

    public void setLibrary(List<Bookmark> library) {
        this.library = library;
    }


    public static Readability instance;
    public static Readability getInstance(){
        return instance==null ? instance = new Readability() : instance;
    }
}