package com.spy686.fly.flat.ms.realt.by.rest.requests;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RealtByFlatForLongRequestBody {

    /**
     * town_name
     */
    @JsonProperty("tx_uedbflatrent_pi2[DATA][town_name][like][]")
    private String townName;
    /**
     * rec_per_page
     * 100 - max value what can be here
     */
    @JsonProperty("tx_uedbflatrent_pi2[rec_per_page]")
    private final Integer recPerPage = 100;


    public RealtByFlatForLongRequestBody generateRequestBody() {
        return new RealtByFlatForLongRequestBody();
    }
}
