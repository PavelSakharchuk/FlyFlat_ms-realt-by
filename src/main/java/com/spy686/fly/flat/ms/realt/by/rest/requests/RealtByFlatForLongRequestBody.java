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
     * days_old
     */
    @JsonProperty("tx_uedbflatrent_pi2[DATA][x_days_old][e]")
    // TODO: 09.10.2021: p.sakharchuk: Need to add enum
    private Integer daysOld;
    /**
     * sort_by
     * date_revision - default
     */
    @JsonProperty("tx_uedbflatrent_pi2[sort_by][0]")
    // TODO: 09.10.2021: p.sakharchuk: Need to add enum
    private String sortBy;
    /**
     * asc_desc
     * 0 - a - z - default
     * 1 - z - a - default
     */
    @JsonProperty("tx_uedbflatrent_pi2[asc_desc][0]")
    // TODO: 09.10.2021: p.sakharchuk: Need to add enum
    private String ascDesc;
    /**
     * rec_per_page
     * 100 - max value what can be here
     * 50 - by UI
     */
    @JsonProperty("tx_uedbflatrent_pi2[rec_per_page]")
    private final Integer recPerPage = 100;


    public RealtByFlatForLongRequestBody generateRequestBody() {
        return new RealtByFlatForLongRequestBody();
    }
}
