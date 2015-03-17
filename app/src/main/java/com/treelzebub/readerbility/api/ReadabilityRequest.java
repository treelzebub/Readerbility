package com.treelzebub.readerbility.api;

import android.text.TextUtils;

import com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient;
import com.google.api.client.googleapis.services.json.AbstractGoogleJsonClientRequest;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.util.Key;
import com.google.api.client.util.ObjectParser;
import com.treelzebub.readerbility.api.model.ReadabilityResponse;
import com.treelzebub.readerbility.auth.Constants;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.logging.Logger;

/**
 * @param abstractGoogleJsonClient Google JSON client
 * @param requestMethod            HTTP Method
 * @param uriTemplate              URI template for the path relative to the base URL. If it starts with a "/"
 *                                 the base path from the base URL will be stripped out. The URI template can also be a
 *                                 full URL. URI template expansion is done using
 *                                 {@link UriTemplate#expand(String, String, Object, boolean)}
 * @param jsonContent              POJO that can be serialized into JSON content or {@code null} for none
 * @param responseClass            response class to parse into
 */

/**
 * Created by Tre Murillo on 3/17/15.
 */
public class ReadabilityRequest extends AbstractGoogleJsonClientRequest<T> {

    static final Logger LOGGER = Logger.getLogger(Constants.TAG);

    protected ReadabilityRequest(AbstractGoogleJsonClient abstractGoogleJsonClient, String requestMethod, String uriTemplate, Object jsonContent, Class<T> responseClass) {
        super(abstractGoogleJsonClient, requestMethod, uriTemplate, jsonContent, responseClass);
    }

    public ReadabilityRequest(Readability client, String requestMethod, String uriTemplate, Object content, Class<T> responseClass) {
        super(client, requestMethod, uriTemplate, content, responseClass);
    }

    @Key
    private String accessToken;

    public final String getAccessToken() {
        return accessToken;
    }

    public final ReadabilityRequest<T> setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    @Override
    public ReadabilityRequest<T> setDisableGZipContent(boolean disableGZipContent) {
        return (ReadabilityRequest<T>) super.setDisableGZipContent(disableGZipContent);
    }

    @Override
    public ReadabilityRequest<T> setRequestHeaders(HttpHeaders headers) {
        return (ReadabilityRequest<T>) super.setRequestHeaders(headers);
    }

    @Override
    public ReadabilityRequest<T> set(String fieldName, Object value) {
        return (ReadabilityRequest<T>) super.set(fieldName, value);
    }

    @Override
    public T execute() throws IOException {
        HttpResponse response = super.executeUnparsed();
        ObjectParser parser = response.getRequest().getParser();
        // This will degrade parsing performance but is an inevitable workaround
        // for the inability to parse JSON arrays.
        String content = response.parseAsString();
        if (response.isSuccessStatusCode()
                && !TextUtils.isEmpty(content)
                && content.charAt(0) == '[') {
            content = TextUtils.concat("{\"", ReadabilityResponse.KEY_DATA, "\":", content, "}")
                    .toString();
        }
        Reader reader = new StringReader(content);
        T parsedResponse = parser.parseAndClose(reader, getResponseClass());

        // parse pagination from Link header
        if (parsedResponse instanceof ReadabilityResponse) {
            Pagination pagination =
                    new Pagination(response.getHeaders().getFirstHeaderStringValue("Link"));
            ((ReadabilityResponse) parsedResponse).setPagination(pagination);
        }

        return parsedResponse;
    }


}
