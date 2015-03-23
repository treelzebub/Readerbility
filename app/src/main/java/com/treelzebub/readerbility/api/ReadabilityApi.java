package com.treelzebub.readerbility.api;

import com.treelzebub.readerbility.Constants;

import org.scribe.builder.api.DefaultApi10a;
import org.scribe.model.Token;

/**
 * Created by Tre Murillo on 2/27/15
 */
public class ReadabilityApi extends DefaultApi10a {

    @Override
    public String getRequestTokenEndpoint() {
        return Constants.REQUEST_TOKEN_URL;
    }

    @Override
    public String getAccessTokenEndpoint() {
        return Constants.ACCESS_TOKEN_URL;
    }

    @Override
    public String getAuthorizationUrl(Token requestToken) {
        return Constants.AUTHORIZE_URL + "?oauth_token=" + requestToken.getToken();
    }
}