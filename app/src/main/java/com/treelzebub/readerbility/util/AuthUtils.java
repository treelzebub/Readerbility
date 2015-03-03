package com.treelzebub.readerbility.util;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;

/**
 * Created by Tre Murillo on 3/3/15.
 */
public class AuthUtils {

    public static class AccountManagerAuth extends AsyncTask<String, String, String> {
        private final Handler mHandler = new Handler();
        private static AccountManager mAccountManager;
        private static String mGoogleToken = null;
        private String mGoogleAccouAccountEmail = null;

        protected AccountManagerAuth(Context c) {
            mAccountManager = AccountManager.get(c);
            final Account[] accounts = mAccountManager.getAccountsByType(Constants.AUTH_TOKEN_TYPE);
            final Account account = accounts[2];

            //mGoogleAccountEmail = get from bundle ;

        }

        public static AccountManager getAccountMan() {
            return mAccountManager;
        }

        public static String getToken() {
            return mGoogleToken;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //TODO create, inflate Dialog;

        }

        @Override
        protected String doInBackground(String... params) {



            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

        }
    }

    public class ReadabilityAuth {
        //TODO need?
    }

}
