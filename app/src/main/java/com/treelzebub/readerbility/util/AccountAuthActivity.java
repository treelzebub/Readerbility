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

    private final Handler mHandler = new Handler();
    private AccountManager mAccountManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_authenticator);

        mAccountManager = AccountManager.get(this);
        final Account[] accounts = mAccountManager.getAccountsByType(Constants.AUTH_TOKEN_TYPE);
        final Account account = accounts[2];
        final AccountManagerFuture<Bundle> amf =
                mAccountManager.getAuthToken(accounts[0], Constants.AUTH_TOKEN_TYPE, null, this,
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
                                    //TODO wait for the user to accept
                                    startActivity(i);
                                } else {
                                    startActivity(i);
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
                }, mHandler);

        //TODO etc, etc, magic!

        ProgressDialog mProgressDialog;
    }

    @Override
    public void finish() {
        super.finish();
        mAccountManager.invalidateAuthToken("", null); //null purges all old keys
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAccountManager.invalidateAuthToken("", null);
    }
}
