package com.spy686.fly.flat.ms.realt.by.rest.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spy686.fly.flat.ms.realt.by.constants.Endpoint;
import com.spy686.fly.flat.ms.realt.by.constants.PropertiesValue;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

import static io.restassured.RestAssured.given;


@Slf4j
public abstract class BaseRestService {
    private static final String LANGUAGE_KEY = "language";
    private static final String LANGUAGE_VALUE = "ru-RU";


    protected RequestSpecification requestSpecification;

    protected BaseRestService() {
        getSpecification();
    }


    // TODO: 10.02.2021: p.sakharchuk: Time to time we get 'HTTP/1.1 429 Too Many Requests' Error in the Response. Need to re-send request.
    public Response get(Object obj, Endpoint endpoint) {
        Map<String, Object> objMap = new ObjectMapper().convertValue(obj, Map.class);
        objMap.put(LANGUAGE_KEY, LANGUAGE_VALUE);

        RequestSpecification rs = given(requestSpecification);
        rs = (obj != null) ? rs.queryParams(objMap) : rs;
        Response response = rs.get(endpoint.getUrl());
        response.then().log().status();
        return response;
    }

    public Response post(Object obj, Endpoint endpoint) {
        RequestSpecification rs = given(requestSpecification);
        rs = (obj != null) ? rs.body(obj) : rs;
        Response response = rs.post(endpoint.getUrl());
        response.then().log().status();
        return response;
    }

    private RequestSpecification getSpecification() {
        requestSpecification = new RequestSpecBuilder()
                .setBaseUri(PropertiesValue.Config.BASE_URI)
                .build();
        requestSpecification.when().log().uri();
        return requestSpecification;
    }

}
