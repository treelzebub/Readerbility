package com.treelzebub.readerbility.util;

import android.accounts.AccountAuthenticatorActivity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.PersistableBundle;

import com.treelzebub.readerbility.R;

import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthProvider;

/**
 * Created by Tre Murillo on 3/1/15.
 */
public class AccountAuthActivity extends AccountAuthenticatorActivity {
    protected static CommonsHttpOAuthConsumer mConsumer;
    protected static CommonsHttpOAuthProvider mProvider;

    public ProgressDialog mProgressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.fragment_authenticator);


    }

    public static CommonsHttpOAuthConsumer getConsumer() {
        if (mConsumer == null) {
            mConsumer = new CommonsHttpOAuthConsumer(Constants.CONSUMER_KEY, Constants.CONSUMER_SECRET);
        }

        return mConsumer;
    }

    public static CommonsHttpOAuthProvider getProvider() {
        if (mProvider == null) {
            mProvider = new CommonsHttpOAuthProvider(Constants.REQUEST_TOKEN_URL, Constants.ACCESS_TOKEN_URL, Constants.AUTHORIZATION_BASE_URL);
            mProvider.setOAuth10a(true);
        }

        return mProvider;
    }
}
