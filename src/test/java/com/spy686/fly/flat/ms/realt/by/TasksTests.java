package com.spy686.fly.flat.ms.realt.by;

import com.spy686.fly.flat.ms.realt.by.services.RealtByService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Slf4j
class TasksTests extends BaseTest {

    @Autowired
    private RealtByService realtByService;

    @Test
    void fetchRealtByTest() {
        log.info("fetchRealtByTest");

        List<RentFlat> rentFlatFullList = restRealtByFlatForLongService.getRentFlatList();
    }

}
