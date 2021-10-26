package com.spy686.fly.flat.ms.realt.by.rest.requests;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;


/**
 * Request body: GET rent/flat-for-long/
 * <p> Example: https://realt.by/rent/flat-for-long/
 * <p> ?tx_uedbflatrent_pi2%5BDATA%5D%5Btown_name%5D%5Blike%5D%5B%5D=%D0%9C%D0%B8%D0%BD%D1%81%D0%BA
 * <p> &tx_uedbflatrent_pi2%5BDATA%5D%5Bx_days_old%5D%5Be%5D=1
 * <p> &tx_uedbflatrent_pi2%5Bsort_by%5D%5B0%5D=date_revision
 * <p> &tx_uedbflatrent_pi2%5Basc_desc%5D%5B0%5D=1
 * <p> &tx_uedbflatrent_pi2%5Brec_per_page%5D=100
 */
@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestRentFlatForLongRequestBody {

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


    public RestRentFlatForLongRequestBody generateRequestBody() {
        return new RestRentFlatForLongRequestBody();
    }
}
