package com.treelzebub.readerbility.util;

import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;
import com.treelzebub.readerbility.error.ReadabilityErrorHandler;
import com.treelzebub.readerbility.thing.Bookmark;

import java.util.ArrayList;
import java.util.List;

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
    private static ReadabilityApi mApi;

    private static OkClient mClient;
    private static RestAdapter mRestAdapter;
    private static CommonsHttpOAuthConsumer mConsumer;
    private static CommonsHttpOAuthProvider mProvider;
    private static Gson mGson;
    private static ReadabilityErrorHandler mErrorHandler;

    public static ReadabilityApi getInstance() {
        return mApi == null ? mApi = new ReadabilityApi() : mApi;
    }

    public ReadabilityApi() {
        getClient(new OkHttpClient());
        getRestAdapter();
        getConsumer();
        getProvider();
        mGson = new Gson();
        mErrorHandler = new ReadabilityErrorHandler();
    }

    public static OkClient getClient(OkHttpClient client) {
        if (mClient == null) mClient = new OkClient(client);
        return mClient;
    }

    public static RestAdapter getRestAdapter() {
        if (mRestAdapter == null) {
            Builder builder = new Builder()
                    .setEndpoint(Constants.BASE_URL)
                    .setLogLevel(Constants.LOG_LEVEL)
                    .setConverter(new GsonConverter(getGson()))
                    .setErrorHandler(mErrorHandler)
                    .setClient(getClient(new OkHttpClient()));
            mRestAdapter = builder.build();
        }
        return mRestAdapter;
    }

    private static Gson getGson() {
        return mGson;
    }

    //signpost stuff that i don't understand
    public static CommonsHttpOAuthConsumer getConsumer() {
        return mConsumer == null ?
                new CommonsHttpOAuthConsumer(Constants.CONSUMER_KEY, Constants.CONSUMER_SECRET) :
                mConsumer;
    }

    public static CommonsHttpOAuthProvider getProvider() {
        if (mProvider == null) {
            mProvider = new CommonsHttpOAuthProvider(
                    Constants.REQUEST_TOKEN_URL, Constants.ACCESS_TOKEN_URL, Constants.AUTHORIZATION_BASE_URL);
            mProvider.setOAuth10a(true);
        }
        return mProvider;
    }
    ////////


    public List<Bookmark> getLibrary() {
        List<Bookmark> library = new ArrayList<>();

        //toDO THE DAMN THING

        return library;
    }
}