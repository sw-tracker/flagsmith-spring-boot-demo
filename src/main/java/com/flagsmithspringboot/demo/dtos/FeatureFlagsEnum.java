package com.flagsmithspringboot.demo.dtos;

import com.fasterxml.jackson.annotation.JsonValue;

public enum FeatureFlagsEnum {
    FEATURE_ONE("feature_one"),
    FEATURE_TWO("feature_two");

    private String key;

    FeatureFlagsEnum(String feature) {
        this.key = feature;
    }

    @JsonValue
    public String getValue() {
        return key;
    }
}
