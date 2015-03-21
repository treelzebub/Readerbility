package com.treelzebub.readerbility.auth;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Looper;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.treelzebub.readerbility.Constants;
import com.treelzebub.readerbility.R;
import com.treelzebub.readerbility.ui.fragtivity.Library;

import java.io.IOException;
import java.security.KeyException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Tre Murillo on 3/18/15
 *
 * An AsyncTask that acquires an OAuth token from an API endpoint
 */

public class AuthenticateWithOAuthTask extends
        AsyncTask<Void, Integer, AuthenticateWithOAuthTask.AuthenticationResult> {

    public enum AuthenticationResult {
        SUCCESS, AUTH_ERROR, INVALID_CREDENTIALS
    }

    private ProgressBar mProgressBar;
    private final ActionBarActivity mContext;
    private final CharSequence username, password;

    public AuthenticateWithOAuthTask(Context mContext, CharSequence username, CharSequence password) {
        this.mContext = (ActionBarActivity) mContext;
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



            FragmentManager fm = mContext.getSupportFragmentManager();
            Looper.prepare();
            fm.beginTransaction()
                    .replace(R.id.container, new Library.LibraryFragment())
                    .commit();

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
