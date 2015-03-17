package com.treelzebub.readerbility.api;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;
import com.treelzebub.readerbility.api.model.Pagination;

import java.util.List;

/**
 * Created by Tre Murillo on 3/17/15.
 */
public abstract class ReadabilityResponse extends ClientErrors {

    public static final String KEY_DATA = "data";

    private Pagination pagination;

    public final Pagination getPagination() {
        return pagination;
    }

    public final ReadabilityResponse setPagination(Pagination pagination) {
        this.pagination = pagination;
        return this;
    }

    @Override
    public ReadabilityResponse clone() {
        return (ReadabilityResponse) super.clone();
    }

    @Override
    public ReadabilityResponse set(String fieldName, Object value) {
        return (ReadabilityResponse) super.set(fieldName, value);
    }

    private class ClientErrors extends GenericJson {

        @Key("message")
        private String message;

        @Key("errors")
        private List<Error> errors;

        public final String getMessage() {
            return message;
        }

        public final List<Error> getErrors() {
            return errors;
        }

        @Override
        public ClientErrors clone() {
            return (ClientErrors) super.clone();
        }

        @Override
        public ClientErrors set(String fieldName, Object value) {
            return (ClientErrors) super.set(fieldName, value);
        }

    }

}