package com.treelzebub.readerbility.util;

import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;
import com.treelzebub.readerbility.error.ReadabilityErrorHandler;

import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthProvider;
import retrofit.RestAdapter;
import retrofit.RestAdapter.Builder;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

/**
 * Created by Tre Murillo on 2/27/15.
 */
public class ReadabilityApi {
    private static ReadabilityApi mApi = null;
    private static OkClient mClient;
    private static RestAdapter mRestAdapter;
    private static CommonsHttpOAuthConsumer mConsumer;
    private static CommonsHttpOAuthProvider mProvider;
    private static Gson mGson;
    private static ReadabilityErrorHandler mErrorHandler;

    public ReadabilityApi() {
        if (mApi == null) {
            mApi = new ReadabilityApi();
            init();
        }
    }

    private static void init() {
        getClient(new OkHttpClient());
        getAdapter();
        getConsumer();
        getProvider();
        mGson = new Gson();
        mErrorHandler = new ReadabilityErrorHandler();
    }

    public static OkClient getClient(OkHttpClient client) {
        if (mClient == null) mClient = new OkClient(client);
        return mClient;
    }

    public static RestAdapter getAdapter() {
        if (mRestAdapter == null) {
            RestAdapter.Builder builder = new Builder()
                    .setEndpoint(Constants.BASE_URL)
                    .setLogLevel(Constants.LOG_LEVEL)
                    .setConverter(new GsonConverter(getGson()))
                    .setErrorHandler(mErrorHandler)
                    .setClient(getClient(new OkHttpClient()));
            mRestAdapter = builder.build();
            return mRestAdapter;
        }
        return mRestAdapter;
    }

    private static Gson getGson() {
        return mGson;
    }

    public static CommonsHttpOAuthConsumer getConsumer() {
        if (mConsumer == null) {
            mConsumer = new CommonsHttpOAuthConsumer(
                    Constants.CONSUMER_KEY, Constants.CONSUMER_SECRET);
        }
        return mConsumer;
    }

    public static CommonsHttpOAuthProvider getProvider() {
        if (mProvider == null) {
            mProvider = new CommonsHttpOAuthProvider(
                    Constants.REQUEST_TOKEN_URL, Constants.ACCESS_TOKEN_URL, Constants.AUTHORIZATION_BASE_URL);
            mProvider.setOAuth10a(true);
        }
        return mProvider;
    }

}
