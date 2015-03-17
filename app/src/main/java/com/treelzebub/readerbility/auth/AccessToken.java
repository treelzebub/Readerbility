package com.treelzebub.readerbility.auth;

import com.treelzebub.readerbility.error.BaseResponse;

/**
 * Created by Tre Murillo on 3/2/15.
 */
public class AccessToken extends BaseResponse {

    private String accessToken;
    private String tokenType;

    public String getAccessToken() {
        return accessToken;
    }

    public String getTokenType() {
        // OAuth requires uppercase Authorization HTTP header for token type
        if (!Character.isUpperCase(tokenType.charAt(0))) {
            tokenType = Character.toString(tokenType.charAt(0)).toUpperCase() + tokenType.substring(1);
        }

        return tokenType;
    }
}
