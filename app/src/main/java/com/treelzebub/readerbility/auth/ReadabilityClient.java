package com.treelzebub.readerbility.auth;

import android.content.Context;

import com.codepath.oauth.OAuthBaseClient;
import com.treelzebub.readerbility.Constants;
import com.treelzebub.readerbility.api.ReadabilityApi;

/**
 * Created by Tre Murillo on 3/22/15
 */
public class ReadabilityClient extends OAuthBaseClient {
    public ReadabilityClient(Context ctxt) {
        super(ctxt, ReadabilityApi.class, Constants.BASE_URL, Constants.CONSUMER_KEY,
                Constants.CONSUMER_SECRET, Constants.CALLBACK_URL);
    }
}
