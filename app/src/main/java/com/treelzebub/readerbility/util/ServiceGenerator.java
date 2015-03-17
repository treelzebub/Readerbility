package com.treelzebub.readerbility.util;

import com.squareup.okhttp.OkHttpClient;
import com.treelzebub.readerbility.auth.AccessToken;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by Tre Murillo on 3/16/15.
 */
public class ServiceGenerator {

    private ServiceGenerator() {
    }

    public static <S> S createService(Class<S> serviceClass, String baseUrl, final AccessToken accessToken) {
        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setEndpoint(baseUrl)
                .setClient(new OkClient(new OkHttpClient()));

        if (accessToken != null) {
            builder.setRequestInterceptor(new RequestInterceptor() {
                @Override
                public void intercept(RequestFacade request) {
                    request.addHeader("Accept", "application/json");
                    request.addHeader("Authorization", accessToken.getTokenType() +
                            " " + accessToken.getAccessToken());
                }
            });
        }

        RestAdapter adapter = builder.build();

        return adapter.create(serviceClass);
    }
}