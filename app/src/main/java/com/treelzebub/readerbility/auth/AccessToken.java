package com.treelzebub.readerbility.auth;

import com.treelzebub.readerbility.util.Constants;

import java.io.Serializable;

import retrofit.client.Response;

/**
 * Created by Tre Murillo on 3/2/15.
 * Copyright(c) 2014 Level, Inc.
 */
public class AccessToken implements Serializable {
    protected Response response;
    protected String token;
    protected String tokenSecret;
    protected long userId;

    static AccessToken createEmptyToken() {
        return new AccessToken(null, null, Constants.EMPTY_LONG);
    }

    public String getToken() {
        return token;
    }

    private String tokenToString() {
        if (this.token == null) return "null";
        return "ACCESS_TOKEN_REMOVED";
    }


    public AccessToken(Response response) {
        this.response = response;
    }

    public AccessToken(String token, String tokenSecret) {
        this.token = token;
        this.tokenSecret = tokenSecret;
    }

    public AccessToken(String token, String tokenSecret, long userId) {
        this.token = token;
        this.tokenSecret = tokenSecret;
        this.userId = userId;
    }
}
