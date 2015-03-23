package com.treelzebub.readerbility.api;

import android.os.AsyncTask;

import com.treelzebub.readerbility.api.model.Bookmark;
import com.treelzebub.readerbility.auth.AccessToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tre Murillo on 3/20/15
 */
public class BookmarksAsyncTask extends AsyncTask<AccessToken, Integer, List<Bookmark>> {

    //For not, just get list of bookmarks... figure everything else out later
    @Override
    protected List<Bookmark> doInBackground(AccessToken... params) {
//        AccessToken token = params[0];
        List<Bookmark> library = new ArrayList<>();
        library.add(new Bookmark());
//        try {
//            final String nonce = AuthUtils.getNonce();
//            final String timestamp = AuthUtils.getTimestamp();
//            final String signature = AuthUtils.getSignature(Constants.ACCESS_TOKEN_URL, Constants.CONSUMER_SECRET);
//
//            RestAdapter.Builder builder = new RestAdapter.Builder()
//                    .setRequestInterceptor(new RequestInterceptor() {
//                        @Override
//                        public void intercept(RequestFacade request) {
//                            request.addHeader("oauth_signature", signature);
//                            request.addHeader("oauth_signature_method", "PLAINTEXT");
//                            request.addHeader("oauth_nonce", nonce);
//                            request.addHeader("oauth_timestamp", timestamp);
//                            request.addHeader("oauth_consumer_key", Constants.CONSUMER_KEY);
//                            request.addHeader("oauth_consumer_secret", Constants.CONSUMER_SECRET);
//                            request.addHeader("oauth_callback", Constants.CALLBACK_URL);
//                            request.addHeader("-- TOKEN --", " ");
//                            request.addHeader("x_auth_mode", "client_auth");
//                        }
//                    })
//                    .setEndpoint(Constants.ACCESS_TOKEN_URL)
//                    .setClient(new OkClient(new OkHttpClient()))
//                    .setLogLevel(Constants.LOG_LEVEL);
//
//            RestAdapter adapter = builder.build();
//            Readability.API = adapter.create(ReadabilityApi.class);
//            library = Readability.API.getBookmarks();

            return library;
//        } catch (NoSuchAlgorithmException | UnsupportedEncodingException | KeyException e) {
//            //impossibru!
//            return null;
//        }
    }

}
