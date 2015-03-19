package com.treelzebub.readerbility.auth;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import oauth.signpost.AbstractOAuthConsumer;
import oauth.signpost.http.HttpRequest;
import retrofit.client.Header;
import retrofit.client.Request;

public class XAuthConsumer extends AbstractOAuthConsumer {

    private static final long serialVersionUID = 1L;

    public XAuthConsumer(String consumerKey, String consumerSecret) {
        super(consumerKey, consumerSecret);
    }

    @Override
    protected HttpRequest wrap(Object request) {
        if (!(request instanceof Request)) {
            throw new IllegalArgumentException("This consumer expects requests of type " + retrofit.client.Request.class.getCanonicalName());
        }
        return new HttpRequestAdapter((Request) request);
    }

    private class HttpRequestAdapter implements HttpRequest {
        private static final String DEFAULT_CONTENT_TYPE = "application/json";

        private Request request;

        private String contentType;

        public HttpRequestAdapter(Request request) {
            this(request, request.getBody() != null ? request.getBody().mimeType() : DEFAULT_CONTENT_TYPE);
        }

        public HttpRequestAdapter(Request request, String contentType) {
            this.request = request;
            this.contentType = contentType;
        }

        @Override
        public Map<String, String> getAllHeaders() {
            HashMap<String, String> headers = new HashMap<>();
            for (Header header : request.getHeaders()) {
                headers.put(header.getName(), header.getValue());
            }
            return headers;
        }

        @Override
        public String getContentType() {
            return contentType;
        }

        @Override
        public String getHeader(String key) {
            for (Header header : request.getHeaders()) {
                if (key.equals(header.getName())) {
                    return header.getValue();
                }
            }
            return null;
        }

        @Override
        public InputStream getMessagePayload() throws IOException {
            final String contentType = getContentType();
            if (null != contentType && contentType.startsWith("application/x-www-form-urlencoded")) {
                long contentLength = request.getBody().length();
                ByteArrayOutputStream output = new ByteArrayOutputStream(Long.valueOf(contentLength)
                        .intValue());
                request.getBody().writeTo(output);
                return new ByteArrayInputStream(output.toByteArray());
            }

            throw new UnsupportedOperationException("The content type" + (contentType != null ? " " +
                    contentType : "") + " is not supported.");
        }

        @Override
        public String getMethod() {
            return request.getMethod();
        }

        @Override
        public String getRequestUrl() {
            return request.getUrl();
        }

        @Override
        public void setHeader(String key, String value) {
            ArrayList<Header> headers = new ArrayList<>();
            headers.addAll(request.getHeaders());
            headers.add(new Header(key, value));
            request = new Request(request.getMethod(), request.getUrl(),
                                  headers, request.getBody());
        }

        @Override
        public void setRequestUrl(String url) {
            request = new Request(request.getMethod(), url, request.getHeaders(), request.getBody());
        }

        @Override
        public Object unwrap() {
            return request;
        }
    }
}
