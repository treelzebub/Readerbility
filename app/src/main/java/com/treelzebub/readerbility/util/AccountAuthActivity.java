package com.treelzebub.readerbility.util;

import android.accounts.AccountAuthenticatorActivity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.PersistableBundle;

import com.treelzebub.readerbility.R;

/**
 * Created by Tre Murillo on 3/1/15.
 */
public class AccountAuthActivity extends AccountAuthenticatorActivity {


    public ProgressDialog mProgressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_authenticator);

        //TODO probably want to make this a Dialog instead of an Activity
    }

}
