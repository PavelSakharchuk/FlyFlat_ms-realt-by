package com.spy686.fly.flat.ms.realt.by.services.rest;

import com.spy686.fly.flat.ms.realt.by.constants.Constants;
import com.spy686.fly.flat.ms.realt.by.models.RentFlat;
import com.spy686.fly.flat.ms.realt.by.rest.RestBase;
import com.spy686.fly.flat.ms.realt.by.rest.requests.RestRentFlatForLongObjectRequestBody;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


/**
 * Service for REST: GET rent/flat-for-long/object/[id]/
 * <p> Example: https://realt.by/rent/flat-for-long/object/2508964/
 */
@Service
@Slf4j
public class RestRentFlatForLongObjectService extends RestBase {

    private static final String OBJECT_GALLERY_CSS_QUERY = "#object-gallery img";
    private static final String SELLER_TYPE_CSS_QUERY = ".inner-object-contacts .agent-block .media-body .fs-small";
    private static final String SELLER_NAME_CSS_QUERY = ".inner-object-contacts .agent-block .media-body strong";
    private static final String SELLER_PHONES_CSS_QUERY = "#object-contacts .object-contacts strong";


    public RestRentFlatForLongObjectService() {
        super();
    }

    public RentFlat fetchRentFlatIsActual(RentFlat rentFlat) {
        RestRentFlatForLongObjectRequestBody restRentFlatForLongObjectRequestBody = new RestRentFlatForLongObjectRequestBody().generateRequestBody();
        return fetchRentFlatIsActual(restRentFlatForLongObjectRequestBody, rentFlat);
    }

    public RentFlat fetchRentFlatData(RentFlat rentFlat) {
        RestRentFlatForLongObjectRequestBody restRentFlatForLongObjectRequestBody = new RestRentFlatForLongObjectRequestBody().generateRequestBody();
        return fetchRentFlatData(restRentFlatForLongObjectRequestBody, rentFlat);
    }

    private RentFlat fetchRentFlatIsActual(RestRentFlatForLongObjectRequestBody restRentFlatForLongObjectRequestBody,
                                           RentFlat rentFlat) {
        Response response = get(restRentFlatForLongObjectRequestBody, rentFlat.getLink());

        switch (HttpStatus.valueOf(response.getStatusCode())) {
            case NOT_FOUND:
                return rentFlat.setActual(false);
            case OK:
                return rentFlat.setActual(true);
            default:
                throw new IllegalArgumentException("Status is not supported: "
                        + HttpStatus.valueOf(response.getStatusCode()));

        }
    }

    private RentFlat fetchRentFlatData(RestRentFlatForLongObjectRequestBody restRentFlatForLongObjectRequestBody,
                                       RentFlat rentFlat) {
        Response response = get(restRentFlatForLongObjectRequestBody, rentFlat.getLink());

        switch (HttpStatus.valueOf(response.getStatusCode())) {
            case NOT_FOUND:
                return rentFlat.setActual(false);
            case OK:
                String body = response.asString();
                Document currentHtmlDoc = Jsoup.parse(body);
                return fetchRentFlatData(currentHtmlDoc, rentFlat);
            default:
                throw new IllegalArgumentException("Status is not supported: "
                        + HttpStatus.valueOf(response.getStatusCode()));

        }
    }

    private RentFlat fetchRentFlatData(Document htmlDoc, RentFlat rentFlat) {
        Elements imageLinkElements = htmlDoc.select(OBJECT_GALLERY_CSS_QUERY);
        List<String> imageLinks = imageLinkElements.stream()
                .map(element -> element.attr(Constants.Attributes.DATA_SRC))
                .collect(Collectors.toList());
        rentFlat.setImageLinks(imageLinks);

        String sellerType = htmlDoc.select(SELLER_TYPE_CSS_QUERY).text();
        String sellerName = htmlDoc.select(SELLER_NAME_CSS_QUERY).text();
        rentFlat.setSellerName(String.format("%s: %s", sellerType, sellerName));

        Elements sellerPhonesElements = htmlDoc.select(SELLER_PHONES_CSS_QUERY);
        List<String> sellerPhones = sellerPhonesElements.stream().map(Element::text).collect(Collectors.toList());
        rentFlat.setSellerPhones(sellerPhones);
        return rentFlat;
    }
}
