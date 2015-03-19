package com.treelzebub.readerbility.auth;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.treelzebub.readerbility.Constants;

import java.io.IOException;

/**
 * Created by Tre Murillo on 3/18/15
 * <p/>
 * An AsyncTask that acquires OAuth tokens from an API endpoint
 */

public class AuthenticateWithOAuthTask extends
        AsyncTask<Void, String, AuthenticateWithOAuthTask.AuthenticationResult> {

    public enum AuthenticationResult {
        SUCCESS, INVALID_CREDENTIALS
    }

    private final Context mContext;
    private final CharSequence username, password;
    private XAuthConsumer consumer;
    private SignpostClient client;
    private final MessageSigner messageSigner;

    public AuthenticateWithOAuthTask(Context mContext, CharSequence username, CharSequence password) {
        this.mContext = mContext;
        this.username = username;
        this.password = password;
        messageSigner = new MessageSigner(Constants.CONSUMER_KEY, Constants.CONSUMER_SECRET);
    }

    @Override
    protected AuthenticationResult doInBackground(Void... params) {
        createHttpClient();

        try {
            requestAccessTokenFromServer();
        } catch (IOException e) {
            e.printStackTrace();
            return AuthenticationResult.INVALID_CREDENTIALS;
        }

        return AuthenticationResult.SUCCESS;
    }

    private void createHttpClient() {
        SignpostClient client = new SignpostClient(consumer);
    }

    private AuthenticationResult requestAccessTokenFromServer() throws IOException {
        final OkHttpClient client = new OkHttpClient();

        // http://oauthbible.com/#oauth-10a-xauth
        Request request = new Request.Builder()
                .url(Constants.ACCESS_TOKEN_URL)
                .addHeader("oauth_signature", "")
                .addHeader("oauth_signature_method", "PLAINTEXT")
                .addHeader("oauth_nonce", AuthUtils.getNonce())
                .addHeader("oauth_timestamp", AuthUtils.getTimestamp())
                .addHeader("oauth_consumer_key", Constants.CONSUMER_KEY)
                .addHeader("oauth_consumer_secret", Constants.CONSUMER_SECRET)
                .addHeader("oauth_callback", Constants.CALLBACK_URL)
                .addHeader("x_auth_username", username.toString())
                .addHeader("x_auth_password", password.toString())
                .addHeader("x_auth_mode", "client_auth")
                .build();

        Response response = client.newCall(request).execute();
        String token = response.header("token");
        String tokenSecret = response.header("token_secret");

        AccessToken.getInstance().setToken(token);
        AccessToken.getInstance().setTokenSecret(tokenSecret);

        if (token == null | tokenSecret == null) return AuthenticationResult.INVALID_CREDENTIALS;
        else return AuthenticationResult.SUCCESS;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        for (String value : values) {
            Toast.makeText(mContext, value, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPostExecute(AuthenticationResult authenticationResult) {
        super.onPostExecute(authenticationResult);

        Toast.makeText(mContext, AccessToken.getInstance().getToken(), Toast.LENGTH_LONG).show();
    }
}
