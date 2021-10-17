package com.spy686.fly.flat.ms.realt.by.rest.requests;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RealtByFlatForLongRedirectRequestBody {

    /**
     * Search encrypted request
     */
    @JsonProperty("search")
    private String search;
    private Integer page = 0;


    public RealtByFlatForLongRedirectRequestBody generateRequestBody() {
        return new RealtByFlatForLongRedirectRequestBody();
    }
}
