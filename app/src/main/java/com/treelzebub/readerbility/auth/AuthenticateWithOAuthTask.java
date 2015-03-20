package com.treelzebub.readerbility.auth;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.squareup.okhttp.OkHttpClient;
import com.treelzebub.readerbility.Constants;
import com.treelzebub.readerbility.api.ReadabilityApi;

import java.io.IOException;
import java.security.KeyException;
import java.security.NoSuchAlgorithmException;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by Tre Murillo on 3/18/15
 * <p/>
 * An AsyncTask that acquires OAuth tokens from an API endpoint
 */

public class AuthenticateWithOAuthTask extends
        AsyncTask<Void, Integer, AuthenticateWithOAuthTask.AuthenticationResult> {

    public enum AuthenticationResult {
        SUCCESS, AUTH_ERROR, INVALID_CREDENTIALS
    }

    private ProgressBar mProgressBar;
    private final Context mContext;
    private final CharSequence username, password;

    public AuthenticateWithOAuthTask(Context mContext, CharSequence username, CharSequence password) {
        this.mContext = mContext;
        this.username = username;
        this.password = password;
    }

    @Override
    protected AuthenticationResult doInBackground(Void... params) {
        try {
            setupRestAdapter();
        } catch (IOException e) {
            e.printStackTrace();
            return AuthenticationResult.INVALID_CREDENTIALS;
        }

        return AuthenticationResult.SUCCESS;
    }

    private AuthenticationResult setupRestAdapter() throws IOException {

        try {
            final String nonce = AuthUtils.getNonce();
            final String timestamp = AuthUtils.getTimestamp();
            final String signature = AuthUtils.getSignature(Constants.ACCESS_TOKEN_URL, Constants.CONSUMER_SECRET);

            RestAdapter.Builder builder = new RestAdapter.Builder()
                    .setRequestInterceptor(new RequestInterceptor() {
                        @Override
                        public void intercept(RequestFacade request) {
                            request.addHeader("oauth_signature", signature);
                            request.addHeader("oauth_signature_method", "PLAINTEXT");
                            request.addHeader("oauth_nonce", nonce);
                            request.addHeader("oauth_timestamp", timestamp);
                            request.addHeader("oauth_consumer_key", Constants.CONSUMER_KEY);
                            request.addHeader("oauth_consumer_secret", Constants.CONSUMER_SECRET);
                            request.addHeader("oauth_callback", Constants.CALLBACK_URL);
                            request.addHeader("x_auth_username", username.toString());
                            request.addHeader("x_auth_password", password.toString());
                            request.addHeader("x_auth_mode", "client_auth");
                        }
                    })
                    .setEndpoint(Constants.ACCESS_TOKEN_URL)
                    .setClient(new OkClient(new OkHttpClient()))
                    .setLogLevel(Constants.LOG_LEVEL);

            RestAdapter adapter = builder.build();
            ReadabilityApi mApi = adapter.create(ReadabilityApi.class);

//
//            if (response.message().equals("UNAUTHORIZED"))
//                return AuthenticationResult.INVALID_CREDENTIALS;
//
//            String token = response.header("token");
//            String tokenSecret = response.header("token_secret");
//
//            AccessToken.getInstance().setToken(token);
//            AccessToken.getInstance().setTokenSecret(tokenSecret);

            return AuthenticationResult.SUCCESS;
        } catch (NoSuchAlgorithmException | KeyException e) {
            //impossibru!
        }
////////////
//        if (nonce == null || signature.equals("")) {
//            Toast.makeText(mContext, "AuthUtils Error.", Toast.LENGTH_LONG).show();
//            Log.e("AuthUtils Error", "nonce IS: " + nonce + "AND signature IS: " + signature);
//
        return AuthenticationResult.AUTH_ERROR;
//        }


    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        mProgressBar.setVisibility(View.VISIBLE);
        mProgressBar.setIndeterminate(false);
        mProgressBar.setProgress(values[0]);
    }

    @Override
    protected void onPostExecute(AuthenticationResult authenticationResult) {
        super.onPostExecute(authenticationResult);
        mProgressBar.setProgress(0);
        mProgressBar.setVisibility(View.GONE);
        Toast.makeText(mContext, AccessToken.getInstance().getToken(), Toast.LENGTH_LONG).show();
    }

    public void setProgressBar(ProgressBar mProgressBar) {
        this.mProgressBar = mProgressBar;
    }
}
