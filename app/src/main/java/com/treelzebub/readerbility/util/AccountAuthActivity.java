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

        final AccountManager acctMan;
        final Account[] accounts;
        final Account account;
        final AccountManagerFuture<Bundle> amf;
        final AccountAuthActivity cbt = this;
        String authTokenType;

        acctMan = AccountManager.get(this);
        authTokenType = "oauth2:https://www.googleapis.com/auth/"; //TODO what's the correct str here??
        accounts = acctMan.getAccountsByType(authTokenType);
        account = accounts[2];

        amf = acctMan.getAuthToken(accounts[0], authTokenType, null, cbt,
                new AccountManagerCallback<Bundle>() {

                    @Override
                    public void run(AccountManagerFuture<Bundle> arg0) {

                        try {
                            Bundle result;
                            Intent i;
                            String token;

                            result = arg0.getResult();
                            if (result.containsKey(AccountManager.KEY_INTENT)) {
                                i = (Intent) result.get(AccountManager.KEY_INTENT);
                                if (i.toString().contains("GrantCredentialsPermissionActivity")) {
                                    // Will have to wait for the user to accept
                                    // the request therefore this will have to
                                    // run in a foreground application
                                    cbt.startActivity(i);
                                } else {
                                    cbt.startActivity(i);
                                }

                            } else {
                                token = (String) result.get(AccountManager.KEY_AUTHTOKEN);

                                /*
                                 * work with token
                                 */

                                // Remember to invalidate the token if the web service rejects it
                                // if(response.isTokenInvalid()){
                                //    acctMan.invalidateAuthToken(authTokenType, token);
                                // }

                            }
                        } catch (OperationCanceledException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (AuthenticatorException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                    }
                }, handler);

    }
}
