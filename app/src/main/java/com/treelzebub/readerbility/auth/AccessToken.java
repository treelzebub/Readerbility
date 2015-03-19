package com.treelzebub.readerbility.auth;

/**
 * Created by Tre Murillo on 3/18/15
 */
public class AccessToken {

    private static AccessToken instance;

    public static AccessToken getInstance() {
        return instance == null ? instance = new AccessToken() : instance;
    }

    private String token, tokenSecret;

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setTokenSecret(String tokenSecret) {
        this.tokenSecret = tokenSecret;
    }

    public String getTokenSecret() {
        return tokenSecret;
    }

    public boolean hasToken() {
        return (!token.equals("") && !tokenSecret.equals(""));
    }
}
