package com.treelzebub.readerbility.util;

import android.accounts.AccountManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.treelzebub.readerbility.util.AuthUtils.AccountAuthenticator;

/**
 * Created by Tre Murillo on 3/1/15.
 */
public class ReadabilityAuthenticatorService extends Service {
    private static final String TAG = "ReadabilityAuthenticatorService";
    private static AccountAuthenticator sAccountAuthenticator;

    @Override
    public IBinder onBind(Intent intent) {
        IBinder ret = null;

        if (intent.getAction().equals(AccountManager.ACTION_AUTHENTICATOR_INTENT)) {
            ret = getAuthenticator().getIBinder();
        }

        return ret;
    }

    private AccountAuthenticator getAuthenticator() {
        if (sAccountAuthenticator == null)
            sAccountAuthenticator = new AccountAuthenticator(this);

        return sAccountAuthenticator;

    }
}
