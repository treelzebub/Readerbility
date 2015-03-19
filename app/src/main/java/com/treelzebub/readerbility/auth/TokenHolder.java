package com.treelzebub.readerbility.auth;

/**
 * Created by Tre Murillo on 3/18/15
 */
public class TokenHolder {

    private String token, tokenSecret;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTokenSecret() {
        return tokenSecret;
    }

    public void setTokenSecret(String tokenSecret) {
        this.tokenSecret = tokenSecret;
    }

    private static TokenHolder instance;

    public static TokenHolder getInstance() {
        return instance==null ? instance = new TokenHolder() : instance;
    }

}
