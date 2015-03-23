package com.treelzebub.readerbility.auth.async;

import android.os.AsyncTask;

import com.treelzebub.readerbility.api.Readability;

import org.scribe.model.Token;
import org.scribe.oauth.OAuthService;

/**
 * Created by Tre Murillo on 3/22/15
 */
public class SetAuthUrl extends AsyncTask<Void, Void, Void> {
    @Override
    protected Void doInBackground(Void... params) {
        OAuthService service = Readability.getInstance().getService();
        Token requestToken = service.getRequestToken();

        mAuthUrlTV.setText(service.getAuthorizationUrl(requestToken));
        return null;
    }
}