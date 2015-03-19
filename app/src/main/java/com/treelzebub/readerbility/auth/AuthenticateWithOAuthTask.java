package com.treelzebub.readerbility.auth;

import android.content.Context;
import android.os.AsyncTask;

import com.treelzebub.readerbility.Constants;

import oauth.signpost.basic.DefaultOAuthConsumer;
import retrofit.client.Request;
import retrofit.client.Response;

/**
 * Created by Tre Murillo on 3/18/15
 *
 * An AsyncTask that acquires OAuth tokens from an API endpoint
 *
 */

public class AuthenticateWithOAuthTask extends AsyncTask<Void, String, AuthenticateWithOAuthTask.AuthenticationResult>{

    public enum AuthenticationResult {
        SUCCESS, INVALID_CREDENTIALS
    }

    private final Context mContext;
    private final CharSequence username, email;
    private DefaultOAuthConsumer consumer;
    private SignpostClient client;
    private final MessageSigner messageSigner;

    public AuthenticateWithOAuthTask(Context mContext, CharSequence username, CharSequence email) {
        this.mContext = mContext;
        this.username = username;
        this.email = email;
        messageSigner = new MessageSigner(Constants.CONSUMER_KEY, Constants.CONSUMER_SECRET);
    }

    @Override
    protected AuthenticationResult doInBackground(Void... params) {
        createHttpClient();

        requestAuthTokenFromServer();
        boolean loginResult = loginAndAuthorizeOAuthToken();
        if(!loginResult) {
            return AuthenticationResult.INVALID_CREDENTIALS;
        }

        requestAccessTokenFromServer();
        return AuthenticationResult.SUCCESS;
    }


    private void createHttpClient() {
        SignpostClient client = new SignpostClient(consumer);
    }

    private void requestAuthTokenFromServer() {
        try {
            Request signedRequest = messageSigner.sign(Constants.REQUEST_TOKEN_URL);
            Response response = client.execute(signedRequest);

            //TODO how to check retrofit Response success?
            if (response.getBody() != null) {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
