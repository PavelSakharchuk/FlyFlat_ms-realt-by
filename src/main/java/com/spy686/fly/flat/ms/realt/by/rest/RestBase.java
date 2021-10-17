package com.spy686.fly.flat.ms.realt.by.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spy686.fly.flat.ms.realt.by.constants.Endpoint;
import com.spy686.fly.flat.ms.realt.by.constants.PropertiesValue;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;

import java.net.URL;
import java.util.Map;

import static io.restassured.RestAssured.given;


@Slf4j
public abstract class RestBase {
    private static final String LANGUAGE_KEY = "language";
    private static final String LANGUAGE_VALUE = "ru-RU";


    protected RequestSpecification requestSpecification;

    protected RestBase() {
        getSpecification();
    }


    public Response get(Object obj, Endpoint endpoint, boolean redirect) {
        return get(obj, endpoint.getUrl(), redirect);
    }

    public Response get(Object obj, Endpoint endpoint) {
        return get(obj, endpoint.getUrl(), true);
    }

    public Response get(Object obj, URL url, boolean redirect) {
        return get(obj, url.toString(), redirect);
    }

    public Response get(Object obj, URL url) {
        return get(obj, url.toString(), true);
    }

    // TODO: 10.02.2021: p.sakharchuk: Time to time we get 'HTTP/1.1 429 Too Many Requests' Error in the Response. Need to re-send request.
    private Response get(Object obj, String urlString, boolean redirect) {
        Map<String, Object> objMap = new ObjectMapper().convertValue(obj, Map.class);

        RequestSpecification rs = given(requestSpecification);
        rs = (obj != null) ? rs.queryParams(objMap) : rs;
        rs.contentType("charset=utf-8");
        Response response = rs.redirects().follow(redirect).get(urlString);
        response.then().log().status();
        return response;
    }

    public Response post(Object obj, Endpoint endpoint) {
        Map<String, Object> objMap = new ObjectMapper().convertValue(obj, Map.class);

        RequestSpecification rs = given(requestSpecification);
        rs = (obj != null) ? rs.queryParams(objMap) : rs;
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
