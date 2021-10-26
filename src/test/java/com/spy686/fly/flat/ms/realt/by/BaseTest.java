package com.spy686.fly.flat.ms.realt.by;

import com.spy686.fly.flat.ms.realt.by.repository.RentFlatRepository;
import com.spy686.fly.flat.ms.realt.by.services.database.DatabaseRentFlatService;
import com.spy686.fly.flat.ms.realt.by.services.rest.RestRentFlatForLongService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;


@Slf4j
public abstract class BaseTest {

    @Autowired
    RestRentFlatForLongService restRentFlatForLongService = new RestRentFlatForLongService();
    @Autowired
    RentFlatRepository rentFlatRepository;
    @Autowired
    DatabaseRentFlatService databaseRentFlatService;

    @BeforeAll
    public static void beforeAll() {
        log.info("Start.");
    }
}
