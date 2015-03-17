package com.treelzebub.readerbility.api.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.json.JsonString;
import com.google.api.client.util.Key;

/**
 * Created by Tre Murillo on 3/17/15.
 */
public class ReadabilityResponse extends GenericJson {
    @Key
    private String whatever;

    @JsonString
    @Key("code")
    private Integer errorCode;

}
