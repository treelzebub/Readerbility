package com.treelzebub.readerbility.auth;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.treelzebub.readerbility.Constants;

import org.json.JSONException;

import java.io.IOException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Tre Murillo on 3/20/15
 * <p/>
 * An AsyncTask which obtains an xAuth token from Readability
 */
public class XAuthAsyncTask extends AsyncTask<Void, Integer, Void> {
    public static final int OAUTH_SAFE_ENCODE = 1;

    private String SAFE_ENCODE = "0";

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void v) {
        super.onPostExecute(v);
        AccessToken.getInstance().clearPassword();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);

    }

    @Override
    protected Void doInBackground(Void... params) {
/**TODO
 *  will return a string with the following format:
 *  USER_KEY+"|||||"+USER_SECRET+"|||||"+first_name+"|||||"+last_name+"|||||"+account_id
 *  Split the string with "|||||" to retrieve the needed information.
 **/
        try {
            xAuthList(AccessToken.getInstance().getUsername(), AccessToken.getInstance().getPassword());
        } catch (JSONException | NoSuchAlgorithmException | InvalidKeyException | IOException e) {
            e.printStackTrace();
            Log.e("Auth Error", e.getMessage());
        }
        return null;
    }

    public List<String> xAuthList(String username, String password)
            throws InvalidKeyException, NoSuchAlgorithmException, IOException, JSONException {

        // XAUTH Post requests
        String xPost = "&x_auth_username=" + username + "&x_auth_password="
                + password + "&x_auth_mode=client_auth";

        // OAUTH Post requests
        String post = "oauth_consumer_key=" + Constants.CONSUMER_KEY
                + "&oauth_consumer_secret=" + Constants.CONSUMER_SECRET
                + "&oauth_nonce=" + AuthUtils.getNonce()
                + "&oauth_signature_method=HMAC-SHA1&oauth_timestamp="
                + AuthUtils.getTimestamp() + "&oauth_version=1.0" + xPost;
        if (getSafeEncode().equals("")) {
            post += "&safe_encode=" + Integer.toString(OAUTH_SAFE_ENCODE);
        }

        // Encode POST
        String postify = encodeForOAuth(post);

        // Generate Base Signature
        String baseSignature = generateBaseSignature(postify);

        // Generate Signature for xAuth
        String signature = AuthUtils.sha1(baseSignature, Constants.CONSUMER_SECRET + "&");
        // Log.i("Signature", signature);

        // Send to Readability
        String oauth_sig = "?oauth_signature=";
        oauth_sig += URLEncoder.encode(signature);
        String url = Constants.BASE_URL + Constants.ACCESS_TOKEN_URL + oauth_sig;

        // Execute OkHttp Request
        try {
            sendRequest(url, post);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Parse JSON to extract Results
        JsonParser parser = new JsonParser();
        JsonObject json = (JsonObject) parser.parse(AccessToken.getResponseBody());
        JsonObject result = json.get("result").getAsJsonObject();

        AccessToken.getInstance().setTokenKey(result.get("oauth_token_key").getAsString());
        AccessToken.getInstance().setTokenSecret(result.get("oauth_token_secret").getAsString());
        String first_name = result.get("first_name").getAsString();
        String last_name = result.get("last_name").getAsString();
        String account_id = result.get("account_id").getAsString();

        List<String> xAuthValList = new ArrayList<String>();
        xAuthValList.add(AccessToken.getInstance().getTokenKey());
        xAuthValList.add(AccessToken.getInstance().getTokenSecret());
        xAuthValList.add(first_name);
        xAuthValList.add(last_name);
        xAuthValList.add(account_id);

        AccessToken.getInstance().clearPassword();
        return xAuthValList;
    }

    public void setSafeEncode(String s) {
        this.SAFE_ENCODE = s;
    }

    public String getSafeEncode() {
        return this.SAFE_ENCODE;
    }

    private void sendRequest(String url, String postString) throws IOException {
        // Example encoded xAuth post:
        // POST&https%3A%2F%2Fapi.twitter.com%2Foauth%2Faccess_token&oauth_consumer_key%3DJvyS7DO2qd6NNTsXJ4E7zA%26oauth_nonce%3D6AN2dKRzxyGhmIXUKSmp1JcB4pckM8rD3frKMTmVAo%26oauth_signature_method%3DHMAC-SHA1%26oauth_timestamp%3D1284565601%26oauth_version%3D1.0%26x_auth_mode%3Dclient_auth%26x_auth_password%3Dtwitter-xauth%26x_auth_username%3Doauth_test_exec
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(Constants.MEDIA_TYPE_MARKDOWN, postString))
                .build();

        Callback tokenCallback = new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (!response.isSuccessful())
                    throw new IOException("Unexpected code " + response);

                Headers responseHeaders = response.headers();
                for (int i = 0; i < responseHeaders.size(); i++) {
                    //TODO
                    System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                    System.out.println("SUCCESS!!!!!!!!!111`~~`");
                }

                //TODO
                AccessToken.setResponseBody(response.body());
            }
        };

        client.newCall(request).execute();
        //TODO
        // Response{protocol=http/1.1, code=401, message=UNAUTHORIZED,
        // url=https://www.readability.com/api/rest/v1/oauth/access_token?oauth_signature=8RvcJTzmzLOLMQouH3QXPL27DKs%3D}

    }

    private String generateBaseSignature(String s) {
        return "POST&" + URLEncoder.encode(Constants.BASE_URL + Constants.ACCESS_TOKEN_URL) + "&"
                + URLEncoder.encode(s);
    }

    private String encodeForOAuth(String s) {
        // Sort the requests

        String[] params = s.split("&");
        Arrays.sort(params);

        // URL Encode Key and Values
        int max = params.length;
        String postify = "";
        for (String param : params) {
            postify += "&";
            String[] temp = param.split("=");
            postify += URLEncoder.encode(temp[0]) + "="
                    + URLEncoder.encode(temp[1]);
        }
        return postify;
    }

}
