package com.treelzebub.readerbility.auth;

import android.util.Base64;

import com.google.gdata.util.common.base.PercentEscaper;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Tre Murillo on 3/18/15
 */
public class MessageSigner {

    private static final String OAUTH_VERSION = "1.0";
    private static final String SIGNATURE_METHOD = "HMAC-SHA1";

    private Random random = new Random(System.nanoTime());
    private PercentEscaper escaper = new PercentEscaper("-._~", false);

    private final String consumerKey, consumerSecret;
    private String token, tokenSecret;

    public MessageSigner(String consumerKey, String consumerSecret) {
        this.consumerKey = consumerKey;
        this.consumerSecret = consumerSecret;
    }

    public String sign(String requestUrl) {
        return sign(requestUrl, new ArrayList<Parameter>());
    }

    public String sign(String requestUrl, List<Parameter> requestParams) {
        List<Parameter> parameters = new ArrayList<>(requestParams);
        addMissingOAuthParams(parameters);

        //calculate OAuth signature for base string and add it to the parameters
        String signatureString = calculateOAuthSignature(requestUrl, parameters);
        parameters.add(new Parameter("oauth_signature", signatureString));

        return requestUrl + "?" + createRequestStringFromParameters(parameters);
    }

    protected void addMissingOAuthParams(List<Parameter> parameters) {
        if (!listContainsParameter(parameters, "oauth_consumer_key")) {
            parameters.add(new Parameter("oauth_consumer_key", this.consumerKey));
        }
        if (!listContainsParameter(parameters, "oauth_nonce")) {
            parameters.add(new Parameter("oauth_nonce", generateNonce()));
        }
        if (!listContainsParameter(parameters, "oauth_signature_method")) {
            parameters.add(new Parameter("oauth_signature_method", SIGNATURE_METHOD));
        }
        if (!listContainsParameter(parameters, "oauth_timestamp")) {
            parameters.add(new Parameter("oauth_timestamp", generateTimestamp()));
        }
        if (!listContainsParameter(parameters, "oauth_version")) {
            parameters.add(new Parameter("oauth_version", OAUTH_VERSION));
        }

        if (null != token) {
            if (!listContainsParameter(parameters, "oauth_token")) {
                parameters.add(new Parameter("oauth_token", getOAuthToken()));
            }
        }
    }

    private boolean listContainsParameter(List<Parameter> parameters, String expectedKey) {
        for (Parameter parameter : parameters) {
            if (parameter.getKey().equals(expectedKey)) {
                return true;
            }
        }
        return false;
    }

    protected String calculateOAuthSignature(String requestUrl, List<Parameter> parameters) throws Error {
        String baseString = createBaseString(requestUrl, parameters);
        try {
            SecretKeySpec secretKeySpec = createSigningKey();
            Mac mac = Mac.getInstance(SIGNATURE_METHOD);
            mac.init(secretKeySpec);
            byte[] signatureBytes = mac.doFinal(baseString.getBytes("UTF-8"));
            byte[] encode = Base64.encode(signatureBytes, Base64.DEFAULT);

            return new String(encode, "UTF-8").trim();
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException | InvalidKeyException e) {
            e.printStackTrace();
        }
        return "";
    }

    protected String createBaseString(String requestUrl, List<Parameter> parameters) {
        // creates basestring for OAuth signing from given url and parameters
        // parameters are ordered according to OAuth spec
        List<Parameter> parametersForBaseString = new ArrayList<>(parameters);
        Collections.sort(parametersForBaseString, new OAuthRequestParameterComparator());
        String parameterString = createRequestStringFromParameters(parametersForBaseString);
        StringBuilder baseStringBuilder = new StringBuilder();
        baseStringBuilder.append("GET");
        baseStringBuilder.append("&");
        baseStringBuilder.append(urlEncode(requestUrl));
        baseStringBuilder.append("&");
        baseStringBuilder.append(urlEncode(parameterString));
        String baseString = baseStringBuilder.toString();
        return baseString;
    }

    protected SecretKeySpec createSigningKey() throws UnsupportedEncodingException {
        // create signing key from consumer secret and token secret
        String keyString = urlEncode(consumerSecret) + "&" + urlEncode(getTokenSecretOrEmptyString());
        return new SecretKeySpec(keyString.getBytes("UTF-8"), SIGNATURE_METHOD);
    }

    private String getTokenSecretOrEmptyString() {
        if (tokenSecret == null) {
            return "";
        }
        return tokenSecret;
    }

    public String createRequestStringFromParameters(List<Parameter> parameters) {
        StringBuilder parametersBuilder = new StringBuilder();
        for (Parameter parameter : parameters) {
            parametersBuilder.append(parameter.getKey());
            parametersBuilder.append("=");
            parametersBuilder.append(urlEncode(parameter.getValue()));
            parametersBuilder.append("&");
        }
        parametersBuilder.setLength(parametersBuilder.length() - 1);

        return parametersBuilder.toString();
    }

    public String urlEncode(String string) {
        return escaper.escape(string);
    }

    private String generateTimestamp() {
        return Long.toString(System.currentTimeMillis() / 1000L);
    }

    private String generateNonce() {
        return Long.toString(random.nextLong());
    }

    public void setOAuthTokenAndSecret(String token, String tokenSecret) {
        this.token = token;
        this.tokenSecret = tokenSecret;
    }

    public String getOAuthToken() {
        return this.token;
    }
}
