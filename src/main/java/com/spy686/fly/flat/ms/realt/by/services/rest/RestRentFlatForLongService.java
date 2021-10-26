package com.spy686.fly.flat.ms.realt.by.services.rest;

import com.spy686.fly.flat.ms.realt.by.constants.Constants;
import com.spy686.fly.flat.ms.realt.by.constants.Endpoint;
import com.spy686.fly.flat.ms.realt.by.models.RentFlat;
import com.spy686.fly.flat.ms.realt.by.models.Source;
import com.spy686.fly.flat.ms.realt.by.rest.RestBase;
import com.spy686.fly.flat.ms.realt.by.rest.requests.RestRentFlatForLongRedirectRequestBody;
import com.spy686.fly.flat.ms.realt.by.rest.requests.RestRentFlatForLongRequestBody;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


/**
 * Service for REST: GET rent/flat-for-long/
 * <p> Example: https://realt.by/rent/flat-for-long/
 * <p> ?tx_uedbflatrent_pi2%5BDATA%5D%5Btown_name%5D%5Blike%5D%5B%5D=%D0%9C%D0%B8%D0%BD%D1%81%D0%BA
 * <p> &tx_uedbflatrent_pi2%5BDATA%5D%5Bx_days_old%5D%5Be%5D=1
 * <p> &tx_uedbflatrent_pi2%5Bsort_by%5D%5B0%5D=date_revision
 * <p> &tx_uedbflatrent_pi2%5Basc_desc%5D%5B0%5D=1
 * <p> &tx_uedbflatrent_pi2%5Brec_per_page%5D=100
 */
@Service
@Slf4j
public class RestRentFlatForLongService extends RestBase {

    private static final String ITEMS_CSS_QUERY = "div[class*='listing-item'][data-mode]";
    private static final String ITEM_HIGHLIGHTED_CSS_QUERY = "div[class*='highlight-icon']";

    private static final String ITEM_LEFT_SECTION_CSS_QUERY = "div[class*='teaser-tile-left']";
    private static final String ITEM_LEFT_CONTENT_SECTION_CSS_QUERY = "div[class='bmcontainer']";
    private static final String ITEM_LEFT_LINK_CSS_QUERY = "a[href][class*='image']";
    private static final String ITEM_LEFT_IMAGE_LINK_CSS_QUERY = "img[src][data-original]";
    private static final String ITEM_LEFT_PRICE_CSS_QUERY = "a[class*='price-switchable']";
    private static final String ITEM_LEFT_PRICE_NEGOTIABLE_CSS_QUERY = "span[class*='negotiable']";

    private static final String ITEM_RIGHT_SECTION_CSS_QUERY = "div[class*='teaser-tile-right']";
    private static final String ITEM_RIGHT_UPDATED_DATE_CSS_QUERY = "div[class*='info-mini'] > span:not([class])";
    private static final String ITEM_RIGHT_TITLE_CSS_QUERY = "a[href][class*='teaser-title']";
    private static final String ITEM_RIGHT_LOCATION_CSS_QUERY = "div[class*='location']";
    private static final String ITEM_RIGHT_FLAT_PARAMS_CSS_QUERY = "div[class*='info-large']";
    private static final String ITEM_RIGHT_ROOMS_CSS_QUERY = "div[class*='info-large'] > span";
    private static final String ITEM_RIGHT_DETAILS_CSS_QUERY = "div[class*='info-text']";

    private static final String ITEM_CONTACT_SECTION_CSS_QUERY = "div[class*='contacts']";
    private static final String ITEM_CONTACT_LOGO_CSS_QUERY = "div[class*='logo'] > img";
    private static final String ITEM_CONTACT_SELLER_NAME_CSS_QUERY = "div[class*='seller']";
    private static final String ITEM_CONTACT_SELLER_PHONE_CSS_QUERY = "a[class*='phone-preview']";

    private static final String PAGING_LIST_CSS_QUERY = "div[class*=paging-list] > a";


    public RestRentFlatForLongService() {
        super();
    }

    public List<RentFlat> getRentFlatList() {
        log.info("Get Rent Flat List: " + Source.REALT_BY);

        RestRentFlatForLongRequestBody restRentFlatForLongRequestBody = new RestRentFlatForLongRequestBody().generateRequestBody();
        restRentFlatForLongRequestBody.setTownName("Минск");
        restRentFlatForLongRequestBody.setDaysOld(1);
        restRentFlatForLongRequestBody.setSortBy("date_revision");
        restRentFlatForLongRequestBody.setAscDesc("1");

        RestRentFlatForLongRedirectRequestBody restRentFlatForLongRedirectRequestBody =
                getRealtByRentFlatRequestRedirect(restRentFlatForLongRequestBody);

        return getRentFlatList(restRentFlatForLongRedirectRequestBody);
    }

    @SneakyThrows
    private RestRentFlatForLongRedirectRequestBody getRealtByRentFlatRequestRedirect(
            RestRentFlatForLongRequestBody restRentFlatForLongRequestBody) {
        log.info("Get Request after redirect.");
        Response firstResponseWithRedirect = get(restRentFlatForLongRequestBody, Endpoint.RENT_FLAT_FOR_LONG, false);
        String firstUrlWithRedirect = firstResponseWithRedirect.getHeader("Location");
        Response responseWithLocation = get(restRentFlatForLongRequestBody, new URL(firstUrlWithRedirect), false);
        String urlString = responseWithLocation.getHeader("Location");
        String searchParam = UriComponentsBuilder.fromUriString(urlString).build().getQueryParams().getFirst("search");

        RestRentFlatForLongRedirectRequestBody restRentFlatForLongRedirectRequestBody =
                new RestRentFlatForLongRedirectRequestBody().generateRequestBody();
        restRentFlatForLongRedirectRequestBody.setSearch(URLDecoder.decode(searchParam, "UTF-8"));

        return restRentFlatForLongRedirectRequestBody;
    }

    @SneakyThrows
    private List<RentFlat> getRentFlatList(RestRentFlatForLongRedirectRequestBody realtByFlatForLongRequestBody) {
        log.info("Get Rent Flats.");
        Map<Integer, Document> htmlDocMap = new HashMap<>();

        int currentPage = realtByFlatForLongRequestBody.getPage();
        Document currentHtmlDoc;
        do {
            realtByFlatForLongRequestBody.setPage(currentPage++);
            Response response = get(realtByFlatForLongRequestBody, Endpoint.RENT_FLAT_FOR_LONG);

            String body = response.asString();
            currentHtmlDoc = Jsoup.parse(body);

            htmlDocMap.put(currentPage, currentHtmlDoc);
        } while (!isLastPage(currentHtmlDoc));

        return htmlDocMap.entrySet().parallelStream()
                .map(htmlDocSet -> getRentFlatList(htmlDocSet.getKey(), htmlDocSet.getValue()))
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    private List<RentFlat> getRentFlatList(Integer page, Document htmlDoc) {
        List<RentFlat> rentFlatList = new ArrayList<>();
        Elements items = htmlDoc.select(ITEMS_CSS_QUERY);

        AtomicInteger i = new AtomicInteger(1);
        int itemsSize = items.size();
        items.parallelStream()
                .forEach(htmlNode -> {
                    log.info("In progress: page {} - {}/{}", page, i.getAndIncrement(), itemsSize);

                    Element leftSection = htmlNode.selectFirst(ITEM_LEFT_SECTION_CSS_QUERY);
                    Element rightSection = htmlNode.selectFirst(ITEM_RIGHT_SECTION_CSS_QUERY);

                    boolean highlighted = htmlNode.selectFirst(ITEM_HIGHLIGHTED_CSS_QUERY) != null;

                    Element itemLeftContentSection = leftSection.selectFirst(ITEM_LEFT_CONTENT_SECTION_CSS_QUERY);
                    String contentJson = itemLeftContentSection.attr(Constants.Attributes.CONTENT);
                    long id = Long.parseLong(JsonPath.from(contentJson).getString("code"));
                    String link = leftSection.selectFirst(ITEM_LEFT_LINK_CSS_QUERY).attr(Constants.Attributes.HREF);
                    String imageLink = leftSection.selectFirst(ITEM_LEFT_IMAGE_LINK_CSS_QUERY).attr(Constants.Attributes.DATA_ORIGINAL);
                    Element priceSection = leftSection.selectFirst(ITEM_LEFT_PRICE_CSS_QUERY);
                    String price;
                    Integer priceUsd = null;
                    if (priceSection != null) {
                        price = priceSection.attr(Constants.Attributes.DATA_CONTENT);
                        String usdString = priceSection.attr(Constants.Attributes.DATA_840);
                        priceUsd = Integer.parseInt(usdString.replaceAll("\\D", ""));
                    } else {
                        price = leftSection.selectFirst(ITEM_LEFT_PRICE_NEGOTIABLE_CSS_QUERY).text();
                    }

                    String updatedDate = rightSection.selectFirst(ITEM_RIGHT_UPDATED_DATE_CSS_QUERY).text();
                    String title = rightSection.selectFirst(ITEM_RIGHT_TITLE_CSS_QUERY).text();
                    String location = rightSection.selectFirst(ITEM_RIGHT_LOCATION_CSS_QUERY).text();
                    String flatParams = rightSection.selectFirst(ITEM_RIGHT_FLAT_PARAMS_CSS_QUERY).text();
                    String originalRoomCountString = rightSection.selectFirst(ITEM_RIGHT_ROOMS_CSS_QUERY).text();
                    Integer rooms = RentFlat.RoomCount.valueByRoomCountString(originalRoomCountString).getRoomCount();
                    String details = rightSection.selectFirst(ITEM_RIGHT_DETAILS_CSS_QUERY).text();

                    Element contactSection = rightSection.selectFirst(ITEM_CONTACT_SECTION_CSS_QUERY);
                    Element logoLinkSection = contactSection.selectFirst(ITEM_CONTACT_LOGO_CSS_QUERY);
                    String logoLink = logoLinkSection == null ? null : logoLinkSection.attr(Constants.Attributes.SRC);
                    String sellerName = contactSection.selectFirst(ITEM_CONTACT_SELLER_NAME_CSS_QUERY).text();
                    String sellerPhone = contactSection.selectFirst(ITEM_CONTACT_SELLER_PHONE_CSS_QUERY).text();

                    RentFlat rentFlat = new RentFlat();
                    rentFlat.setObjectId(id);
                    rentFlat.setHighlighted(highlighted);
                    rentFlat.setLink(link);
                    rentFlat.setImageLink(imageLink);
                    rentFlat.setPrice(price);
                    rentFlat.setPriceUsd(priceUsd);
                    rentFlat.setUpdatedDate(updatedDate);
                    rentFlat.setTitle(title);
                    rentFlat.setLocation(location);
                    rentFlat.setFlatParams(flatParams);
                    rentFlat.setOriginalRoomString(originalRoomCountString);
                    rentFlat.setRoomCount(rooms);
                    rentFlat.setDetails(details);
                    rentFlat.setSellerName(sellerName);
                    rentFlat.getSellerPhones().add(sellerPhone);

                    rentFlatList.add(rentFlat);
                });

        return rentFlatList;
    }

    private boolean isLastPage(Document htmlDoc) {
        Element lastPageSection = htmlDoc.select(PAGING_LIST_CSS_QUERY).last();
        return lastPageSection == null || lastPageSection.hasClass("active");
    }
}
