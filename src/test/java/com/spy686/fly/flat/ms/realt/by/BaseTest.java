package com.spy686.fly.flat.ms.realt.by;

import com.spy686.fly.flat.ms.realt.by.rest.services.RestRealtByFlatForLongService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;


@Slf4j
public abstract class BaseTest {
    RestRealtByFlatForLongService restRealtByFlatForLongService = new RestRealtByFlatForLongService();

    @BeforeAll
    public static void beforeAll() {
        log.info("Start.");
    }
}
