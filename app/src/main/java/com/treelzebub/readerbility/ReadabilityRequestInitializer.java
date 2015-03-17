package com.treelzebub.readerbility;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.services.json.AbstractGoogleJsonClientRequest;
import com.google.api.client.googleapis.services.json.CommonGoogleJsonClientRequestInitializer;
import com.google.api.client.util.Preconditions;
import com.treelzebub.readerbility.api.ReadabilityRequest;

import java.io.IOException;

/**
 * Created by Tre Murillo on 3/17/15.
 */
public class ReadabilitytInitializer extends CommonGoogleJsonClientRequestInitializer {

    private final Credential credential;

    public ReadabilitytInitializer(Credential credential) {
        super();
        this.credential = Preconditions.checkNotNull(credential);
    }

    @Override
    protected void initializeJsonRequest(AbstractGoogleJsonClientRequest<?> request)
            throws IOException {
        super.initializeJsonRequest(request);
        initializeReadabilityRequest((ReadabilityRequest<?>) request);
    }

    protected void initializeReadabilityRequest(ReadabilityRequest<?> request)
            throws java.io.IOException {
        request.setAccessToken(credential.getAccessToken());
    }

}