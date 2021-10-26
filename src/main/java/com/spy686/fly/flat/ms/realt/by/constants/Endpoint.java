package com.spy686.fly.flat.ms.realt.by.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


@Getter
@AllArgsConstructor
public enum Endpoint {
    RENT_FLAT_FOR_LONG("rent/flat-for-long/"),
    RENT_FLAT_FOR_LONG_OBJECT_ID("rent/flat-for-long/object/{id}", "id");

    private final String url;
    private Map<String, String> pathParams = new HashMap<>();

    Endpoint(String url, String... pathParams) {
        this.url = url;
        if (pathParams != null) {
            Arrays.asList(pathParams).forEach(param -> this.pathParams.put(param, null));
        }
    }
}
