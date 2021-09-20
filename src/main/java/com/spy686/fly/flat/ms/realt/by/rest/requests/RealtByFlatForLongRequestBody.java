package com.spy686.fly.flat.ms.realt.by.rest.requests;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RealtByFlatForLongRequestBody {

    public RealtByFlatForLongRequestBody generateRequestBody() {
        return new RealtByFlatForLongRequestBody();
    }
}
