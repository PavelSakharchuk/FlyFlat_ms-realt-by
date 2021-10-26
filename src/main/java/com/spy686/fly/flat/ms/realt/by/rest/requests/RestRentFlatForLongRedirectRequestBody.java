package com.spy686.fly.flat.ms.realt.by.rest.requests;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;


/**
 * Redirect request body after: GET rent/flat-for-long/
 * <p> Example: https://realt.by/rent/flat-for-long/
 * <p> ?page=0&search=eJxNTbsOwjAM%2FBp3TgakMnSg5D8sk1gooiRRHCj9e9wykOUeuju75TVhoifDaV7iQ8mpMkoTOAPn647zeKADZ2G0h74MHwy
 * <p> 0CeYl6GDfTXZA4fYq6kk8BhbfnetSybXhbevCQI2x8jtKzOlfrOyxcMVC998HY77CijVj
 */
@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestRentFlatForLongRedirectRequestBody {

    /**
     * Search encrypted request
     */
    @JsonProperty("search")
    private String search;
    private Integer page = 0;


    public RestRentFlatForLongRedirectRequestBody generateRequestBody() {
        return new RestRentFlatForLongRedirectRequestBody();
    }
}
