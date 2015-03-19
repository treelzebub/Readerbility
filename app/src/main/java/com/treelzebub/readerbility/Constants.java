package com.treelzebub.readerbility;

import retrofit.RestAdapter.LogLevel;

/**
 * Created by Tre Murillo on 2/27/15
 */
public class Constants {
//    https://www.readability.com/api/rest/v1/oauth/authorize

    public static final String TAG = "OAuthSamples";

    protected static final String CREDENTIALS_STORE_PREF_FILE = "oauth";

    public static final String CONSUMER_KEY = "treelzebub";
    public static final String CONSUMER_SECRET = "qMnTRpLAntrDC3f3xJyeHR6WwUWmCXYU";

    public static final String CALLBACK_URL = "com.treelzebub://readability";
    public static final String ACCOUNT_TYPE = "com.treelzebub.readerbility";
    public static final String USER_AGENT = "Readerbility for Android v0.001";
    public static final LogLevel LOG_LEVEL = LogLevel.BASIC;

    public static final String BASE_URL = "https://www.readability.com/api/rest/v1";
    public static final String ACCESS_TOKEN_URL = BASE_URL + "/oauth/access_token";
    public static final String REDIRECT_URL = "http://localhost/Callback";

    public static final String BOOKMARK_CONTENT_TYPE = "application/x-www-form-urlencoded";

    public static final long EMPTY_LONG = 0L;

    private Constants(){
    }

}
