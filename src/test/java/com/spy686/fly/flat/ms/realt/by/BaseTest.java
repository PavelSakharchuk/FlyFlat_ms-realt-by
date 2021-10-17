package com.spy686.fly.flat.ms.realt.by;

import com.spy686.fly.flat.ms.realt.by.repository.RentFlatRepository;
import com.spy686.fly.flat.ms.realt.by.services.data.RentFlatDataService;
import com.spy686.fly.flat.ms.realt.by.services.rest.RentFlatService;
import com.spy686.fly.flat.ms.realt.by.services.rest.RestRealtByFlatForLongServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;


@Slf4j
public abstract class BaseTest {

    RentFlatService rentFlatService = new RestRealtByFlatForLongServiceImpl();
    @Autowired
    RentFlatRepository rentFlatRepository;
    @Autowired
    RentFlatDataService rentFlatDataService;

    @BeforeAll
    public static void beforeAll() {
        log.info("Start.");
    }
}
