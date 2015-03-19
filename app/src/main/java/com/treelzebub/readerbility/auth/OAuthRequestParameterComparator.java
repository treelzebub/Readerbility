package com.treelzebub.readerbility.auth;

import java.util.Comparator;

/**
 * Created by Tre Murillo on 3/18/15
 *
 * Comparator for sorting Parameters to order specified in OAuth spec: first alphabetically by key, then by value.
 *
 */
class OAuthRequestParameterComparator implements Comparator<Parameter> {
    public int compare(Parameter firstParameter, Parameter secondParameter) {
        int keyCompareResult = firstParameter.getKey().compareTo(secondParameter.getKey());
        if (keyCompareResult == 0) {
            // if keys are equal compare the values
            return firstParameter.getValue().compareTo(secondParameter.getValue());
        } else {
            return keyCompareResult;
        }
    }
}
