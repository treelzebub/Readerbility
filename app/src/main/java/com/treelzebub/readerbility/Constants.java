package com.treelzebub.readerbility;

import retrofit.RestAdapter.LogLevel;

/**
 * Created by Tre Murillo on 2/27/15
 */
public class Constants {

    public static final String TAG = "Constants";

    public static final String CONSUMER_KEY = "treelzebub";
    public static final String CONSUMER_SECRET = "qMnTRpLAntrDC3f3xJyeHR6WwUWmCXYU";

    public static final String BASE_URL = "https://www.readability.com/api/rest/v1";
    public static final String AUTHORIZE_URL = BASE_URL + "/oauth/authorize";
    public static final String REQUEST_TOKEN_URL = BASE_URL + "/oauth/request_token";
    public static final String ACCESS_TOKEN_URL = BASE_URL + "/oauth/access_token";

    public static final String ENCODING = "UTF-8";
    public static final String SIGNATURE_METHOD = "HMAC-SHA1";
    public static final String CALLBACK_URL = "oauth://readerbility";
    public static final LogLevel LOG_LEVEL = LogLevel.BASIC;

    public static final String CONTENT_TYPE = "application/x-www-form-urlencoded";

    private Constants(){
    }

//                oauth_token=...
//               &oauth_token_secret=...
//               &oauth_callback_confirmed=true
//               oauth_verifier parameter is provided to the client either in the pre-configured
//                              callback URL or through the fingers of your users (the
//                              aforementioned oob (“out of band”) mechanism). This value MUST be
//                              included when exchanging Request Tokens for Access Tokens.


}
