package com.treelzebub.readerbility.util;

import android.accounts.AccountManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.squareup.okhttp.OkHttpClient;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RestAdapter.Builder;
import retrofit.client.OkClient;

/**
 * Created by Tre Murillo on 2/28/15.
 */
public class ServiceGenerator {
    private ReadabilityService sReadability;
    private AccountAuthenticator mAccountAuthenticator;

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

    private class ReadabilityService extends Service {

        @Override
        public IBinder onBind(Intent intent) {
            IBinder binder = null;

            if (intent.getAction().equals(AccountManager.ACTION_AUTHENTICATOR_INTENT)) {
                return getAuthenticator().getIBinder();
            }

            return null;
        }

        private AccountAuthenticator getAuthenticator() {
            if (mAccountAuthenticator == null) mAccountAuthenticator = new AccountAuthenticator();
        }
    }
}
