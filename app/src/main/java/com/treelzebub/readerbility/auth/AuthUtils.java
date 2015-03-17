package com.treelzebub.readerbility.auth;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Handler;
import android.preference.PreferenceManager;

import com.google.android.gms.auth.GoogleAuthUtil;

/**
 * Created by Tre Murillo on 3/3/15.
 */
public class AuthUtils {

    public static class XAuthenticator {
    }

    private static class AccountManagerUtils {
        private static final String KEY_ACCOUNT_NAME = "account_name";

        private static String mCurrentUser = null;

        public static Account getGoogleAccountByName(Context c, String accountName) {
            if (accountName != null) {
                AccountManager accountMan = AccountManager.get(c);
                Account[] accounts = accountMan.getAccountsByType(GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE);

                for (Account a : accounts) if (accountName.equals(a.name)) return a;
            }
            return null;
        }

        public static String getAccountName(Context c) {
            if (mCurrentUser != null) return mCurrentUser;
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(c);
            return prefs.getString(KEY_ACCOUNT_NAME, null);
        }

        public static void setAccountName(Context c, String accountName) {
            Editor editor = PreferenceManager.getDefaultSharedPreferences(c).edit();

            editor.putString(KEY_ACCOUNT_NAME, accountName);
            editor.apply();

            mCurrentUser = accountName;
        }

        public static void removeAccount(Context c) {
            Editor editor = PreferenceManager.getDefaultSharedPreferences(c).edit();

            editor.remove(KEY_ACCOUNT_NAME);
            editor.apply();

            mCurrentUser = null;
        }

        public static void invalidateToken(boolean purgeAll) {
            AccountManagerAuth.getAccountMan().invalidateAuthToken(
                    GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE, purgeAll ? null : AccountManagerAuth.getToken());
        }

        public static class AccountManagerAuth extends AsyncTask<String, String, String> {
            private final Handler mHandler = new Handler();
            private static AccountManager mAccountManager;
            private static String mGoogleToken = null;
            private String mGoogleAccountEmail = null;

            protected AccountManagerAuth(Context c) {
                mAccountManager = AccountManager.get(c);
                final Account[] accounts = mAccountManager.getAccountsByType(Constants.AUTH_TOKEN_TYPE);
                final Account account = accounts[2];

                //mGoogleAccountEmail = get from bundle ;

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

            public static AccountManager getAccountMan() {
                return mAccountManager;
            }

            public static String getToken() {
                return mGoogleToken;
            }

            public static boolean isAuthenticated() {
                return mGoogleToken != null;
            }

        }
    }
}
