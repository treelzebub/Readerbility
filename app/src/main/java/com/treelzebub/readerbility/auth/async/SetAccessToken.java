package com.treelzebub.readerbility.auth.async;

import android.content.Intent;
import android.os.AsyncTask;

import com.treelzebub.readerbility.api.Readability;
import com.treelzebub.readerbility.ui.fragtivity.Library;

import org.scribe.model.Token;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

/**
 * Created by Tre Murillo on 3/22/15
 */
public class SetAccessToken extends AsyncTask<Verifier, Void, Token> {
    @Override
    protected Token doInBackground(Verifier... params) {
        OAuthService service = Readability.getInstance().getService();

        return service.getAccessToken(mRequestToken, params[0]);
    }

    @Override
    protected void onPostExecute(Token token) {
        super.onPostExecute(token);
        Readability.getInstance().setToken(token);
        startActivity(new Intent(getApplicationContext(), Library.LibraryActivity.class));
    }
}