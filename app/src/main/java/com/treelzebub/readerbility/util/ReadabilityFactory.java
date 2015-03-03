package com.treelzebub.readerbility.util;

import android.accounts.AccountManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.NonNull;

import com.treelzebub.readerbility.error.ReadabilityErrorHandler;
import com.treelzebub.readerbility.util.ReadabilityApi.AccessToken;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter.Builder;

/**
 * Created by Tre Murillo on 2/28/15.
 */
public class ReadabilityFactory {
    private ReadabilityService sReadability = null;
    private AccountAuthenticator mAccountAuthenticator;
    private ReadabilityErrorHandler mErrorHandler;

    public <S> ReadabilityService createService(Class<S> serviceClass, String baseUrl, final AccessToken accessToken) {
        if (accessToken != null) {
            Builder builder = new Builder();
            builder.setRequestInterceptor(new RequestInterceptor() {
                @Override
                public void intercept(RequestFacade request) {
                    request.addHeader("User-Agent", Constants.USER_AGENT);
                    request.addHeader("Accept", "application/json");
                    request.addHeader("Authorization", Constants.AUTH_TOKEN_TYPE + accessToken.getToken());
                }
            });
            return (ReadabilityService) builder.build().create(serviceClass);
        }
        //TODO handle errors here or let Authenticator do it?
        return null;
    }


    private class ReadabilityService extends Service {

        @Override
        public void onCreate() {
            super.onCreate();
        }

        @Override
        public IBinder onBind(@NonNull Intent intent) {
            if (intent.getAction().equals(AccountManager.ACTION_AUTHENTICATOR_INTENT)) {
                return AccountAuthenticator.getInstance().getIBinder();
            }
            return null;
        }

        @Override
        public boolean onUnbind(Intent intent) {
            return super.onUnbind(intent);
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
        }
    }

}
