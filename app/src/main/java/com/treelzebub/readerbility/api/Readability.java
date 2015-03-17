package com.treelzebub.readerbility.api;

import com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient;
import com.treelzebub.readerbility.api.model.Bookmark;

import java.io.IOException;

/**
 * Created by Tre Murillo on 3/17/15.
 */

public class Readability extends AbstractGoogleJsonClient {

    public static final String DEFAULT_ROOT_URL = "https://api.Readability.com/";

    public static final String DEFAULT_SERVICE_PATH = "";

    public static final String DEFAULT_BASE_URL = DEFAULT_ROOT_URL + DEFAULT_SERVICE_PATH;

    public Readability(com.google.api.client.http.HttpTransport transport,
                  com.google.api.client.json.JsonFactory jsonFactory,
                  com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
        this(new Builder(transport, jsonFactory, httpRequestInitializer));
    }

    Readability(Builder builder) {
        super(builder);
    }

    @Override
    protected void initialize(AbstractGoogleClientRequest<?> httpClientRequest) throws IOException {
        super.initialize(httpClientRequest);
    }

    public User user() {
        return new User();
    }

    public class User {

        public ListBookmarksRequest repos() throws IOException {
            ListBookmarksRequest request = new ListBookmarksRequest();
            initialize(request);
            return request;
        }

        public class ListBookmarksRequest extends ReadabilityRequest<Bookmark> {

            private static final String REST_PATH = "user/repos";

            public static final String TYPE_ALL = "all";
            public static final String TYPE_OWNER = "owner";
            public static final String TYPE_PUBLIC = "public";
            public static final String TYPE_PRIVATE = "private";
            public static final String TYPE_MEMBER = "member";

            public static final String SORT_CREATED = "created";
            public static final String SORT_UPDATED = "updated";
            public static final String SORT_PUSHED = "pushed";
            public static final String SORT_FULLNAME = "full_name";

            public static final String DIRECTION_ASC = "asc";
            public static final String DIRECTION_DESC = "desc";

            @Key("type")
            private String repoType;

            @Key("sort")
            private String sort;

            @Key("direction")
            private String direction;

            @Key("page")
            private Integer page;

            protected ListBookmarksRequest() {
                super(Readability.this,
                        HttpMethods.GET,
                        REST_PATH,
                        null,
                        Repositories.class);
            }

            public final String getRepoType() {
                return repoType;
            }

            public final ListBookmarksRequest setRepoType(String repoType) {
                this.repoType = repoType;
                return this;
            }

            public final String getSort() {
                return sort;
            }

            public final ListBookmarksRequest setSort(String sort) {
                this.sort = sort;
                return this;
            }

            public final String getDirection() {
                return direction;
            }

            public final ListBookmarksRequest setDirection(String direction) {
                this.direction = direction;
                return this;
            }

            public final Integer getPage() {
                return page;
            }

            public final ListBookmarksRequest setPage(Integer page) {
                this.page = page;
                return this;
            }

            @Override
            public ListBookmarksRequest set(String fieldName, Object value) {
                return (ListBookmarksRequest) super.set(fieldName, value);
            }

        }

    }

    public static final class Builder extends
            com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient.Builder {

        public Builder(com.google.api.client.http.HttpTransport transport,
                       com.google.api.client.json.JsonFactory jsonFactory,
                       com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
            super(transport,
                    jsonFactory,
                    DEFAULT_ROOT_URL,
                    DEFAULT_SERVICE_PATH,
                    httpRequestInitializer,
                    false);
        }

        @Override
        public Readability build() {
            return new Readability(this);
        }

        @Override
        public Builder setRootUrl(String rootUrl) {
            return (Builder) super.setRootUrl(rootUrl);
        }

        @Override
        public Builder setServicePath(String servicePath) {
            return (Builder) super.setServicePath(servicePath);
        }

        @Override
        public Builder setGoogleClientRequestInitializer(
                GoogleClientRequestInitializer googleClientRequestInitializer) {
            return (Builder) super
                    .setGoogleClientRequestInitializer(googleClientRequestInitializer);
        }

        @Override
        public Builder setHttpRequestInitializer(HttpRequestInitializer httpRequestInitializer) {
            return (Builder) super.setHttpRequestInitializer(httpRequestInitializer);
        }

        @Override
        public Builder setApplicationName(String applicationName) {
            return (Builder) super.setApplicationName(applicationName);
        }

        @Override
        public Builder setSuppressPatternChecks(boolean suppressPatternChecks) {
            return (Builder) super.setSuppressPatternChecks(suppressPatternChecks);
        }

        @Override
        public Builder setSuppressRequiredParameterChecks(boolean suppressRequiredParameterChecks) {
            return (Builder) super
                    .setSuppressRequiredParameterChecks(suppressRequiredParameterChecks);
        }

        @Override
        public Builder setSuppressAllChecks(boolean suppressAllChecks) {
            return (Builder) super.setSuppressAllChecks(suppressAllChecks);
        }

        public Builder setReadabilityRequestInitializer(
                ReadabilityRequestInitializer ReadabilityRequestInitializer) {
            return (Builder) super.setGoogleClientRequestInitializer(ReadabilityRequestInitializer);
        }

    }
}