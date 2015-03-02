package com.treelzebub.readerbility.ui.fragtivity;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.treelzebub.readerbility.util.AccountAuthActivity;

import oauth.signpost.OAuth;

/**
 * Created by Tre Murillo on 3/1/15.
 */
public class OAuthRetrieveAccessToken extends AsyncTask<Uri, Void, Void> {
    public static final String TAG = "Readability";
    protected AccountAuthActivity context;

    public OAuthRetrieveAccessToken(AccountAuthActivity context) {
        this.context = context;
    }

    @Override
    protected Void doInBackground(Uri... params) {
        Uri uri = params[0];
        String oauth_verifier = uri.getQueryParameter(OAuth.OAUTH_VERIFIER);

        try {
            AccountAuthActivity.getProvider().retrieveAccessToken(AccountAuthActivity.getConsumer, oauth_verifier);

            Log.i("access_token", AccountAuthActivity.getConsumer().getToken());
            Log.i("access_token_secret", AccountAuthActivity.getConsumer().getTokenSecret());

            AccountManager acctMan = AccountManager.get(this.context);
            final Account account = new Account(username, AccountAuthActivity.ACCOUNT_TYPE);

            acctMan.addAccountExplicitly(account, null, null);
            acctMan.setUserData(account, "token", AccountAuthActivity.getConsumer().getToken());
            acctMan.setUserData(account, "tokenSecret", AccountAuthActivity.getConsumer().getTokenSecret());

            final Intent intent = new Intent();

            intent.putExtra(AccountManager.KEY_ACCOUNT_NAME, username);
            intent.putExtra(AccountManager.KEY_ACCOUNT_TYPE, AccountAuthActivity.ACCOUNT_TYPE);
            intent.putExtra(AccountManager.KEY_AUTHTOKEN, AccountAuthActivity.ACCOUNT_TYPE);
            this.context.setAccountAuthenticatorResult(intent.getExtras());
            this.context.setResult(Activity.RESULT_OK, intent);


        } catch (Exception e) {
            Log.e("Error Creating Account", e.getMessage());
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        onPostExecute(result);
        this.context.finish();
    }
}
