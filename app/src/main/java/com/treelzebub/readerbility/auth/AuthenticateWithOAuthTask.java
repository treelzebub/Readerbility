package com.treelzebub.readerbility.auth;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Looper;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.treelzebub.readerbility.R;
import com.treelzebub.readerbility.ui.fragtivity.Library;

import java.io.IOException;

/**
 * Created by Tre Murillo on 3/18/15
 * <p/>
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
            return setupRestAdapter();
        } catch (IOException e) {
            e.printStackTrace();
            return AuthenticationResult.INVALID_CREDENTIALS;
        }
    }

    private AuthenticationResult setupRestAdapter() throws IOException {
        FragmentManager fm = mContext.getSupportFragmentManager();
        Looper.prepare();
        fm.beginTransaction()
                .replace(R.id.container, new Library.LibraryFragment())
                .commit();
        return AuthenticationResult.AUTH_ERROR;
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
        Toast.makeText(mContext, AccessToken.getInstance().getTokenKey(), Toast.LENGTH_LONG).show();
    }

    public void setProgressBar(ProgressBar mProgressBar) {
        this.mProgressBar = mProgressBar;
    }
}
