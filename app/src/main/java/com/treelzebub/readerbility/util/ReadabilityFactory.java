package com.treelzebub.readerbility.util;

import android.accounts.AccountManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.NonNull;

import com.treelzebub.readerbility.error.ReadabilityErrorHandler;
import com.treelzebub.readerbility.util.ReadabilityApi.AccessToken;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RestAdapter.Builder;

/**
 * Created by Tre Murillo on 2/28/15.
 */
public class ReadabilityFactory {
    private ReadabilityService sReadability = null;
    private AccountAuthenticator mAccountAuthenticator;
    private ReadabilityErrorHandler mErrorHandler;

    public <S> ReadabilityService createService(Class<S> serviceClass, String baseUrl, final AccessToken accessToken) {

        if (sReadability == null && accessToken != null) {
            Builder builder = new Builder();
            builder.setRequestInterceptor(new RequestInterceptor() {
                @Override
                public void intercept(RequestFacade request) {
                    request.addHeader("Accept", "application/json");
                    request.addHeader("Authorization", Constants.AUTH_TOKEN_TYPE + "" + accessToken.getToken());
                }
            });
            RestAdapter adapter = builder.build();
            sReadability = (ReadabilityService) adapter.create(serviceClass);
        }
        return sReadability;
    }

    private class ReadabilityService extends Service {

        @Override
        public IBinder onBind(@NonNull Intent intent) {
            if (intent.getAction().equals(AccountManager.ACTION_AUTHENTICATOR_INTENT)) {
                return AccountAuthenticator.getInstance().getIBinder();
            }
            return null;
        }

    }

}
