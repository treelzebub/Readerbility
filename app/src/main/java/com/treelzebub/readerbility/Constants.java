package com.treelzebub.readerbility;

import com.squareup.okhttp.MediaType;

import retrofit.RestAdapter.LogLevel;

/**
 * Created by Tre Murillo on 2/27/15
 */
public class Constants {

    public static final String TAG = "Constants";

    public static final String CONSUMER_KEY = "treelzebub";
    public static final String CONSUMER_SECRET = "qMnTRpLAntrDC3f3xJyeHR6WwUWmCXYU";

    public static final String BASE_URL = "https://www.readability.com/api/rest/v1";
    public static final String ACCESS_TOKEN_URL = "/oauth/access_token";
    public static final String ENCODING = "UTF-8";
    public static final String SIGNATURE_METHOD = "HMAC-SHA1";
    public static final String CALLBACK_URL = "com.treelzebub://readability";
    public static final String ACCOUNT_TYPE = "com.treelzebub.readerbility";
    public static final String USER_AGENT = "Readerbility for Android v0.001";
    public static final LogLevel LOG_LEVEL = LogLevel.BASIC;

    public static final String CONTENT_TYPE = "application/x-www-form-urlencoded";
    public static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse(CONTENT_TYPE);

    public static final long EMPTY_LONG = 0L;

    private Constants(){
    }

    /**
     * LINKS
     * // http://oauthbible.com/#oauth-10a-xauth
     *
     *
     *
     * **/

}
