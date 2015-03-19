package com.treelzebub.readerbility.auth;

/**
 * Created by Tre Murillo on 3/18/15
 */
public class Parameter {

    private final String key, value;

    public Parameter(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public Parameter(String key, int value) {
        this.key = key;
        this.value = Integer.toString(value);
    }

    public Parameter(String key, CharSequence value) {
        this.key = key;
        this.value = value.toString();
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "(" + key + " : " + value +")";
    }

}
