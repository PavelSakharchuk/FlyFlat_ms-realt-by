package com.spy686.fly.flat.ms.realt.by.rest.requests;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;


/**
 * Request body: GET rent/flat-for-long/object/[id]/
 * <p> Example: https://realt.by/rent/flat-for-long/object/2508964/
 */
@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestRentFlatForLongObjectRequestBody {

    public RestRentFlatForLongObjectRequestBody generateRequestBody() {
        return new RestRentFlatForLongObjectRequestBody();
    }
}
