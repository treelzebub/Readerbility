package com.treelzebub.readerbility.api;

import com.treelzebub.readerbility.Constants;
import com.treelzebub.readerbility.api.model.Bookmark;

import org.scribe.builder.ServiceBuilder;
import org.scribe.model.Token;
import org.scribe.oauth.OAuthService;

import java.util.List;

/**
 * Created by Tre Murillo on 3/17/15
 * <p/>
 * A singleton that describes an authenticated Readability session
 */

public class Readability {

    public static Readability instance;
    private static OAuthService sAuth;

    private static List<Bookmark> mLibrary;
    private static String mAuthUrl;
    private static Token mRequestToken;
    private static Token mAccessToken; // WHITE! WHALE!

    public static Readability getInstance() {
        return instance == null ? instance = new Readability() : instance;
    }

    public OAuthService getService() {
        return sAuth == null ?
                new ServiceBuilder()
                        .provider(ReadabilityApi.class)
                        .apiKey(Constants.CONSUMER_KEY)
                        .apiSecret(Constants.CONSUMER_SECRET)
                        .build()
                :
                sAuth;
    }

    public static String getAuthUrl() {
        return mAuthUrl;
    }

    public static void setAuthUrl(String mAuthUrl) {
        Readability.mAuthUrl = mAuthUrl;
    }

    public static Token getRequestToken() {
        return mRequestToken;
    }

    public static void setRequestToken(Token token) {
        mRequestToken = token;
    }

    public static Token getAccessToken() {
        return mAccessToken;
    }

    public static void setAccessToken(Token mAccessToken) {
        Readability.mAccessToken = mAccessToken;
    }

    public static List<Bookmark> getLibrary() {
        return mLibrary;
    }

    public static void setLibrary(List<Bookmark> library) {
        mLibrary = library;
    }

}