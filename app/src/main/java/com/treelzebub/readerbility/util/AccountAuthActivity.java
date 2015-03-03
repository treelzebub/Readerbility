package com.treelzebub.readerbility.util;

import android.accounts.Account;
import android.accounts.AccountAuthenticatorActivity;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.treelzebub.readerbility.R;

import java.io.IOException;

/**
 * Created by Tre Murillo on 3/1/15.
 */
public class AccountAuthActivity extends AccountAuthenticatorActivity {

    private final Handler handler = new Handler();

    public ProgressDialog mProgressDialog;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_authenticator);

        final AccountManager acctMan = AccountManager.get(this);
        String authTokenType = "oauth2:https://www.googleapis.com/auth/"; //TODO what's the correct str here??
        final Account[] accounts = acctMan.getAccountsByType(authTokenType);
        final Account account = accounts[2];
        final AccountAuthActivity cbt = this;
        final AccountManagerFuture<Bundle> amf =
                acctMan.getAuthToken(accounts[0], authTokenType, null, cbt,
                        //TODO good candidate for kotlin lambda; this is hideous
                new AccountManagerCallback<Bundle>() {

                    @Override
                    public void run(AccountManagerFuture<Bundle> accountManagerCallback) {
                        try {
                            Bundle result;
                            Intent i;
                            String token;

                            result = accountManagerCallback.getResult();
                            if (result.containsKey(AccountManager.KEY_INTENT)) {
                                i = (Intent) result.get(AccountManager.KEY_INTENT);
                                if (i.toString().contains("GrantCredentialsPermissionActivity")) {
                                    //wait for the user to accept
                                    cbt.startActivity(i);
                                } else {
                                    cbt.startActivity(i);
                                }

                            } else {
                                token = (String) result.get(AccountManager.KEY_AUTHTOKEN);
                                //TODO work with token
                            }
                        } catch (OperationCanceledException e) {
                            Log.e("OperationCanceled", e.getMessage());
                        } catch (IOException e) {
                            Log.e("IOException", e.getMessage());
                        } catch (AuthenticatorException e) {
                            Log.e("AuthenticatorException", e.getMessage());
                        }
                    }
                }, handler);
    }
}
