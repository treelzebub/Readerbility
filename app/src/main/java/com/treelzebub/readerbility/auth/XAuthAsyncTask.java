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

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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

    private String API_URI;

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
            List<String> xAuthList = xAuthList(AccessToken.getInstance().getUsername(), AccessToken.getInstance().getPassword());
        } catch (JSONException | NoSuchAlgorithmException | InvalidKeyException | IOException e) {
            e.printStackTrace();
            Log.e("Auth Error", e.getMessage());
        }
        return null;
    }

    public List<String> xAuthList(String username, String password)
            throws InvalidKeyException, NoSuchAlgorithmException, IOException, JSONException {
        setApiUri(Constants.ACCESS_TOKEN_URL);

        // XAUTH Post requests
        String xPost = "&x_auth_username=" + username + "&x_auth_password="
                + password + "&x_auth_mode=client_auth";

        // OAUTH Post requests
        String post = "oauth_consumer_key=" + Constants.CONSUMER_KEY
                + "&oauth_consumer_secret=" + Constants.CONSUMER_SECRET
                + "&oauth_nonce=" + AuthUtils.getNonce()
                + "&oauth_signature_method=HMAC-SHA1&oauth_timestamp="
                + AuthUtils.getTimestamp() + "&oauth_version=1.0" + xPost;
        if (getSafeEncode().compareTo("1") == 0) {
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
        String url = Constants.BASE_URL + getApiUri() + oauth_sig;
        // Log.i("URL", url);
        String response = sendRequest(url, post);
        // Log.i("xAuth Response", response);

        // Parse JSON to extract Results
        JsonParser parser = new JsonParser();
        JsonObject json = (JsonObject) parser.parse(response);
        JsonObject result = json.get("result").getAsJsonObject();

        AccessToken.getInstance().setTokenKey(result.get("oauth_token_key").getAsString());
        // Log.i("User Key", this.USER_KEY);
        AccessToken.getInstance().setTokenSecret(result.get("oauth_token_secret").getAsString());
        // Log.i("User Secret", this.USER_SECRET);
        String first_name = result.get("first_name").getAsString();
        // Log.i("First Name", first_name);
        String last_name = result.get("last_name").getAsString();
        // Log.i("Last Name", last_name);
        String account_id = result.get("account_id").getAsString();
        // Log.i("Account ID", account_id);

        List<String> xAuthValList = new ArrayList<String>();
        xAuthValList.add(AccessToken.getInstance().getTokenKey());
        xAuthValList.add(AccessToken.getInstance().getTokenSecret());
        xAuthValList.add(first_name);
        xAuthValList.add(last_name);
        xAuthValList.add(account_id);

        AccessToken.getInstance().clearPassword();
        return xAuthValList;
    }

    public void setApiUri(String s) {
        this.API_URI = s;
    }

    public String getApiUri() {
        return this.API_URI;
    }

    public void setSafeEncode(String s) {
        this.SAFE_ENCODE = s;
    }

    public String getSafeEncode() {
        return this.SAFE_ENCODE;
    }

    private String sendRequest(String url, String postString) throws IOException {
        //POST&https%3A%2F%2Fapi.twitter.com%2Foauth%2Faccess_token&oauth_consumer_key%3DJvyS7DO2qd6NNTsXJ4E7zA%26oauth_nonce%3D6AN2dKRzxyGhmIXUKSmp1JcB4pckM8rD3frKMTmVAo%26oauth_signature_method%3DHMAC-SHA1%26oauth_timestamp%3D1284565601%26oauth_version%3D1.0%26x_auth_mode%3Dclient_auth%26x_auth_password%3Dtwitter-xauth%26x_auth_username%3Doauth_test_exec
        OkHttpClient client = new OkHttpClient();

        if (postString.compareTo("") != 0) {
            // GET & POST Request

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
                    if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                    Headers responseHeaders = response.headers();
                    for (int i = 0; i < responseHeaders.size(); i++) {
                        //TODO
                        System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                        System.out.println("SUCCESS!!!!!!!!!111`~~`");
                    }

                    //TODO
                    AccessToken.responseBody = response.body();
                }
            };

            client.newCall(request).enqueue(tokenCallback);

//            HttpPost post = new HttpPost(url);

            // Create POST List
            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
            String[] exp = postString.split("&");

            for (String anExp : exp) {
                String[] kv = anExp.split("=");
                pairs.add(new BasicNameValuePair(kv[0], kv[1]));
            }
            post.setEntity(new UrlEncodedFormEntity(pairs));

            // Get HTTP Response & Parse it
            HttpResponse response = client.execute(post);
            HttpEntity entity = response.getEntity();
            // DEBUG
            // post =
            // postString = oauth_consumer_key=treelzebub&oauth_consumer_secret=qMnTRpLAntrDC3f3xJyeHR6WwUWmCXYU&oauth_nonce=2ae58f0172e2cbbde1e58eed8afe72&oauth_signature_method=HMAC-SHA1&oauth_timestamp=1426918233956&oauth_version=1.0&x_auth_username= --- &x_auth_password= --- &x_auth_mode=client_auth

            if (entity != null) {
                InputStream instream = entity.getContent();
                String result = convertStreamToString(instream);
                // Log.i("HTTPError", "Result of conversion: [" + result + "]");

                instream.close();
                return result;
            }
        } else {
            // GET Request

        }

        return "";
    }

    private String sendRequest(String url, String postString, String file_path) throws IOException {
        HttpClient client = new DefaultHttpClient();

        if (postString.compareTo("") != 0) {
            // GET & POST Request
            HttpPost post = new HttpPost(url);

            // Create POST List
            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
            String[] exp = postString.split("&");
            int max = exp.length;
            for (int i = 0; i < max; i++) {
                String[] kv = exp[i].split("=");
                pairs.add(new BasicNameValuePair(kv[0], kv[1]));
            }
            post.setEntity(new UrlEncodedFormEntity(pairs));

            // Get HTTP Response & Parse it
            HttpResponse response = client.execute(post);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream instream = entity.getContent();
                String result = convertStreamToString(instream);
                // Log.i("HTTPError", "Result of conversion: [" + result + "]");

                instream.close();
                return result;
            }
        } else {
            // GET Request
        }

        return "";
    }

    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    private String generateBaseSignature(String s) {
        return "POST&" + URLEncoder.encode(Constants.BASE_URL + getApiUri()) + "&"
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


    //TODO organize

}
