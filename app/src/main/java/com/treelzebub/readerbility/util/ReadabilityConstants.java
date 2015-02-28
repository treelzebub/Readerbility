package com.treelzebub.readerbility.util;

/**
 * Created by Tre Murillo on 2/27/15.
 */
public class ReadabilityConstants {
    public static final String PREFIX = "readability_";

    protected static final String consumerKey = "treelzebub";
    protected static final String consumerSecret = "qMnTRpLAntrDC3f3xJyeHR6WwUWmCXYU";

    protected static final String urlBase = "https://www.readability.com/api/rest/v1";

    protected static final String requestTokenUrl = urlBase + "/oauth/request_token/";
    protected static final String accessTokenUrl = urlBase + "/oauth/access_token/";
    protected static final String authorizationUrlBase = urlBase
            + "/oauth/authorize/";

    protected static final String callbackUrl = "readability?action=return";
}
