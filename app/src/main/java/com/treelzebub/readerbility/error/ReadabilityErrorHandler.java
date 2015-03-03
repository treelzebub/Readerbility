package com.treelzebub.readerbility.error;

import retrofit.ErrorHandler;
import retrofit.RetrofitError;

/**
 * Created by Tre Murillo on 3/2/15.
 * Copyright(c) 2014 Level, Inc.
 */
public class ReadabilityErrorHandler implements ErrorHandler {

    @Override
    public Throwable handleError(RetrofitError retrofitError) {
        return null;
    }
}

