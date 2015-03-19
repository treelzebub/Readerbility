package com.treelzebub.readerbility.util;

import com.treelzebub.readerbility.Constants;
import com.treelzebub.readerbility.auth.SignpostClient;

import oauth.signpost.OAuthConsumer;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

/**
 * Created by Tre Murillo on 3/16/15
 */
public class ServiceGenerator {

    private ServiceGenerator() {
    }

    public static <S> S createService(Class<S> serviceClass, OAuthConsumer consumer, String baseUrl) {
        return createService(serviceClass, consumer, baseUrl, null);
    }

    public static <S> S createService(Class<S> serviceClass, OAuthConsumer consumer,
                                      String baseUrl, final String accessToken) {
        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setEndpoint(baseUrl)
                .setClient(new SignpostClient(consumer));

        if (accessToken != null) {
            builder.setRequestInterceptor(new RequestInterceptor() {
                @Override
                public void intercept(RequestFacade request) {
                    request.addHeader("Accept", "application/json");
                    request.addHeader("X-Auth-Token", accessToken);
                    request.addHeader("User-Agent", Constants.USER_AGENT);
                }
            });
        }

        RestAdapter adapter = builder.build();

        return adapter.create(serviceClass);
    }
}