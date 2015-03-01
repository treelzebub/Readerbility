package com.treelzebub.readerbility.util;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.accounts.NetworkErrorException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.squareup.okhttp.OkHttpClient;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RestAdapter.Builder;
import retrofit.client.OkClient;

/**
 * Created by Tre Murillo on 2/28/15.
 */
public class AuthUtils {

    public class ServiceGenerator {
        private ServiceGenerator() {
        }

        public <S> S createService(Class<S> serviceClass, String baseUrl, final AccessToken accessToken) {
            RestAdapter.Builder builder = new Builder()
                    .setEndpoint(baseUrl)
                    .setClient(new OkClient(new OkHttpClient()));

            if (accessToken != null) {
                builder.setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        request.addHeader("Accept", "application/json");
                        request.addHeader("Authorization", accessToken.getTokenType() + "" + accessToken.getAccessToken());
                    }
                });
            }

            RestAdapter adapter = builder.build();
            return adapter.create(serviceClass);
        }

    }

    public class AccountAuthenticator extends AbstractAccountAuthenticator {
        private Context context;
        private ParserService sReadability;

        public AccountAuthenticator(ReadabilityAuthActivity context) {
            super(context);
            this.context = context;
        }

        @Override
        public Bundle addAccount(AccountAuthenticatorResponse response,
                                 String accountType, String authTokenType,
                                 String[] requiredFeatures, Bundle options)
                throws NetworkErrorException {

            final Bundle result = new Bundle();
            final Intent intent = new Intent(this.context, AuthUtils.class);

            intent.putExtra(AccountManager.KEY_AUTHTOKEN, authTokenType);
            intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);
            result.putParcelable(AccountManager.KEY_INTENT, intent);

            return result;
        }

        @Override
        public Bundle editProperties(AccountAuthenticatorResponse response, String accountType) {
            return null;
        }

        @Override
        public Bundle confirmCredentials(AccountAuthenticatorResponse response, Account account, Bundle options) throws NetworkErrorException {
            return null;
        }

        @Override
        public Bundle getAuthToken(AccountAuthenticatorResponse response, Account account, String authTokenType, Bundle options) throws NetworkErrorException {
            final AccountManager am = AccountManager.get(context);
            String authToken = am.peekAuthToken(account, authTokenType);

            if (!TextUtils.isEmpty(authToken)) {
                final String password = am.getPassword(account);
                if (password != null) {
//                    authToken = sReadability.userSignIn(account.name, password, authTokenType);
                }
            }

            if (!TextUtils.isEmpty(authToken)) {
                final Bundle result = new Bundle();
                result.putString(AccountManager.KEY_ACCOUNT_NAME, account.name);
                result.putString(AccountManager.KEY_ACCOUNT_TYPE, authTokenType);
                result.putString(AccountManager.KEY_AUTHTOKEN, authToken);
                return result;
            }

            //Couldn't access user/pass; re-prompt for credentials
            final Intent i = new Intent(context, AuthUtils.class);
            i.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);
            i.putExtra(Constants.ACCOUNT_TYPE, account.type);
            i.putExtra(, authTokenType);
            Bundle b = new Bundle();
            b.putParcelable(AccountManager.KEY_INTENT, i);
            return b;
        }

        @Override
        public String getAuthTokenLabel(String authTokenType) {
            return null;
        }

        @Override
        public Bundle updateCredentials(AccountAuthenticatorResponse response, Account account, String authTokenType, Bundle options) throws NetworkErrorException {
            return null;
        }

        @Override
        public Bundle hasFeatures(AccountAuthenticatorResponse response, Account account, String[] features) throws NetworkErrorException {
            return null;
        }
    }

}