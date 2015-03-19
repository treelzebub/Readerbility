package com.treelzebub.readerbility.auth;

import com.treelzebub.readerbility.Constants;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.security.NoSuchAlgorithmException;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.http.HttpRequest;
import retrofit.client.Header;
import retrofit.client.Request;
import retrofit.client.UrlConnectionClient;
import retrofit.mime.TypedOutput;

/**
 * Created by Tre Murillo on 3/18/15
 */
public class SignpostClient extends UrlConnectionClient {

    public static OAuthConsumer mConsumer;

    public SignpostClient(OAuthConsumer consumer) {
        super();
        mConsumer = consumer;
    }

    @Override
    protected HttpURLConnection openConnection(Request request) throws IOException {
        HttpURLConnection connection = super.openConnection(request);
        try {
            HttpRequest signedRequest = mConsumer.sign(connection);

        } catch (OAuthMessageSignerException | OAuthExpectationFailedException | OAuthCommunicationException e) {
            //Fail to sign, ignore
            e.printStackTrace();
        }
        return connection;
    }

    void prepareRequest(HttpURLConnection connection, Request request)
            throws IOException, OAuthExpectationFailedException, NoSuchAlgorithmException,
            OAuthCommunicationException, OAuthMessageSignerException {

        connection.setRequestMethod(request.getMethod());
        connection.setDoInput(true);

        for (Header header : request.getHeaders()) {
            connection.addRequestProperty(header.getName(), header.getValue());
        }

        TypedOutput body = request.getBody();
        long length = -1;
        if (body != null) {
            connection.setDoOutput(true);
            length = body.length();
        }
//        MessageSigner.create(Constants.CONSUMER_KEY, Constants.CONSUMER_SECRET)
//                .setOAuthTokenAndSecret(getToken(request), getOAuthSecret(request))
//                .sign(request, connection);
        if (length != -1) {
            connection.setFixedLengthStreamingMode((int) length);
        } else {
//            connection.setChunkedStreamingMode(CHUNK_SIZE);
        }
        if (body != null) {
            body.writeTo(connection.getOutputStream());
        }
    }

}