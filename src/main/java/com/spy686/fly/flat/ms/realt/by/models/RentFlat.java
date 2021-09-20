package com.spy686.fly.flat.ms.realt.by.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;


@Data
@Accessors(chain = true)
@NoArgsConstructor
public class RentFlat implements Serializable {
    private long id;
}
