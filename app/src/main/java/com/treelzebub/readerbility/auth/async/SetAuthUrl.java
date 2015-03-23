package com.treelzebub.readerbility.auth.async;

import android.os.AsyncTask;
import android.widget.TextView;

import com.codepath.oauth.OAuthLoginActionBarActivity;
import com.treelzebub.readerbility.R;
import com.treelzebub.readerbility.api.Readability;

import org.scribe.model.Token;

/**
 * Created by Tre Murillo on 3/22/15
 *
 * An AsyncTask that uses this app's consumer key/secret to obtain a token and url
 * for the upcoming access token request. The url is displayed to the user as a link
 * to be opened in the system default browser
 */
public class SetAuthUrl extends AsyncTask<Void, Void, String> {
    private final Readability readability;
    private final OAuthLoginActionBarActivity mActivity;

    public SetAuthUrl(OAuthLoginActionBarActivity mActivity) {
        this.readability = Readability.getInstance();
        this.mActivity = mActivity;
    }

    @Override
    protected String doInBackground(Void... params) {
        Token requestToken = readability.getService().getRequestToken();

        return readability.getService().getAuthorizationUrl(requestToken);
    }

    @Override
    protected void onPostExecute(String authUrl) {
        super.onPostExecute(authUrl);
        TextView tv = (TextView) mActivity.findViewById(R.id.auth_url_tv);
        tv.setText(authUrl);
        Readability.setAuthUrl(authUrl);
    }
}