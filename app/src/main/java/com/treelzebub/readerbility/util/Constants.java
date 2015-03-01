package com.treelzebub.readerbility.util;

/**
 * Created by Tre Murillo on 2/27/15.
 */
public class Constants {
    public static final String PREFIX = "readability_";

    protected static final String CONSUMER_KEY = "treelzebub";
    protected static final String CONSUMER_SECRET = "qMnTRpLAntrDC3f3xJyeHR6WwUWmCXYU";

    protected static final String BASE_URL = "https://www.readability.com/api/rest/v1";

    protected static final String REQUEST_TOKEN_URL = BASE_URL + "/oauth/request_token/";
    protected static final String ACCESS_TOKEN_URL = BASE_URL + "/oauth/access_token/";
    protected static final String CALLBACK_URL = "readability?action=return";
    protected static final String AUTHORIZATION_BASE_URL = BASE_URL + "/oauth/authorize/";

    //TODO
    public static final String ACCOUNT_TYPE = "com.treelzebub.readerbility";
}
