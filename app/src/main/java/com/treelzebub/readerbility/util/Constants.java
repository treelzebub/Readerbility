package com.treelzebub.readerbility.util;

import retrofit.RestAdapter.LogLevel;

/**
 * Created by Tre Murillo on 2/27/15.
 */
public class Constants {
    public static final String BASE_URL = "https://www.readability.com/api/rest/v1";
    public static final String CALLBACK_URL = "com.treelzebub://readability";
    public static final String AUTHORIZATION_BASE_URL = BASE_URL + "oauth/authorize/";
    public static final String REQUEST_TOKEN_URL = BASE_URL + "oauth/request_token/";
    public static final String ACCESS_TOKEN_URL = BASE_URL + "oauth/access_token/";

    public static final String CONSUMER_KEY = "treelzebub";
    public static final String CONSUMER_SECRET = "qMnTRpLAntrDC3f3xJyeHR6WwUWmCXYU";

    public static final String ACCOUNT_TYPE = "com.treelzebub.readerbility";
    public static final String AUTH_TOKEN_TYPE = BASE_URL;
    public static final String USER_AGENT = "Readerbility for Android v0.001";
    public static final LogLevel LOG_LEVEL = LogLevel.BASIC;

    public static final long EMPTY_LONG = 0L;
}
