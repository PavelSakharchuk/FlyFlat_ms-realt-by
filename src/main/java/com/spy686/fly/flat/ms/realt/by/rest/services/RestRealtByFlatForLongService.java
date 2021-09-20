package com.spy686.fly.flat.ms.realt.by.rest.services;

import com.spy686.fly.flat.ms.realt.by.constants.Endpoint;
import com.spy686.fly.flat.ms.realt.by.models.RentFlat;
import com.spy686.fly.flat.ms.realt.by.rest.requests.RealtByFlatForLongRequestBody;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

import java.util.List;


@Slf4j
public class RestRealtByFlatForLongService extends BaseRestService {

    public RestRealtByFlatForLongService() {
        super();
    }

    public List<RentFlat> getRealtByRentFaltList() {
        RealtByFlatForLongRequestBody realtByFlatForLongRequestBody = new RealtByFlatForLongRequestBody().generateRequestBody();
        return getRealtByRentFaltList(realtByFlatForLongRequestBody);
    }

    public List<RentFlat> getRealtByRentFaltList(RealtByFlatForLongRequestBody realtByFlatForLongRequestBody) {
        log.info("Get My Projects.");
        Response response = get(realtByFlatForLongRequestBody, Endpoint.RENT_FLAT_FOR_LONG);
        return getRealtByRentFaltList(response);
    }

    protected List<RentFlat> getRealtByRentFaltList(Response bloggerInfoResponse) {
//        String body = bloggerInfoResponse.asString();
//        Document doc = Jsoup.parse(body);
//
//        AtomicInteger columnIndex = new AtomicInteger();
//        List<String> headers = doc.getElementsByTag("thead").first()
//                .getElementsByTag("tr").first()
//                .getElementsByTag("th").stream()
//                .map(Element::text)
//                .map(columnName -> {
//                    columnIndex.getAndIncrement();
//                    if (columnName.isEmpty()) {
//                        columnName = String.valueOf(columnIndex.get());
//                    }
//                    return columnName;
//                })
//                .collect(Collectors.toList());
//
//        return doc.getElementsByTag("tbody").get(0)
//                .getElementsByTag("tr").parallelStream()
//                .map(htmlNode -> {
//                    long projectId = Long.parseLong(htmlNode.attr("data-key"));
//
//                    List<String> values = htmlNode.getElementsByTag("td").stream()
//                            .map(Element::text)
//                            .collect(Collectors.toList());
//
//                    Map<String, String> parametersMap =
//                            IntStream.range(0, headers.size())
//                                    .boxed()
//                                    .collect(Collectors.toMap(headers::get, values::get));
//
//                    // TODO: 31.07.2021: p.sakharchuk: Need to investigate if parametersMap move to constructor
//                    return new Project()
//                            .setId(projectId)
//                            .setParameters(parametersMap);
//                })
//                .collect(Collectors.toList());
        return null;
    }
}
