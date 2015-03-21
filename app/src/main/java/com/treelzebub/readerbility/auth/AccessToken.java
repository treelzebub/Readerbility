package com.treelzebub.readerbility.auth;

import com.squareup.okhttp.ResponseBody;

import java.io.IOException;

/**
 * Created by Tre Murillo on 3/18/15
 */
public class AccessToken {

    private static AccessToken instance;
    private static ResponseBody responseBody;

    public static AccessToken getInstance() {
        return instance == null ? instance = new AccessToken() : instance;
    }

    private String token;
    private String tokenSecret;
    private String username;
    private String password;

    public static String getResponseBody() throws IOException {
        return responseBody.string();
    }

    public static void setResponseBody(ResponseBody responseBody) {
        AccessToken.responseBody = responseBody;
    }

    public void setTokenKey(String token) {
        this.token = token;
    }

    public String getTokenKey() {
        return token;
    }

    public void setTokenSecret(String tokenSecret) {
        this.tokenSecret = tokenSecret;
    }

    public String getTokenSecret() {
        return tokenSecret;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean hasToken() {
        return (!token.equals("") && !tokenSecret.equals(""));
    }

    public void clearPassword() {
        password = "";
    }
}
