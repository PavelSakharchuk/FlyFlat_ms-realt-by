package com.spy686.fly.flat.ms.realt.by;

import com.spy686.fly.flat.ms.realt.by.repository.RentFlatRepository;
import com.spy686.fly.flat.ms.realt.by.services.database.RentFlatDatabaseService;
import com.spy686.fly.flat.ms.realt.by.services.rest.RentFlatRestService;
import com.spy686.fly.flat.ms.realt.by.services.rest.RestRealtByFlatForLongRestServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;


@Slf4j
public abstract class BaseTest {

    RentFlatRestService rentFlatRestService = new RestRealtByFlatForLongRestServiceImpl();
    @Autowired
    RentFlatRepository rentFlatRepository;
    @Autowired
    RentFlatDatabaseService rentFlatDatabaseService;

    @BeforeAll
    public static void beforeAll() {
        log.info("Start.");
    }
}
