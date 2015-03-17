package com.treelzebub.readerbility.api;

import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;

import retrofit.converter.ConversionException;
import retrofit.converter.Converter;
import retrofit.mime.TypedInput;
import retrofit.mime.TypedOutput;

/**
 * Created by Tre Murillo on 3/16/15.
 */
public class ReadabilityConverter implements Converter {
    private GsonBuilder gsonBuilder;

    public ReadabilityConverter(){
        gsonBuilder = new GsonBuilder();

`    }

    @Override
    public Object fromBody(TypedInput body, Type type) throws ConversionException {
        return null;
    }

    @Override
    public TypedOutput toBody(Object object) {
        return null;
    }
}
