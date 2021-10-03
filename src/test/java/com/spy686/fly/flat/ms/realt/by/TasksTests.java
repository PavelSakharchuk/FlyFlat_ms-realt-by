package com.spy686.fly.flat.ms.realt.by;

import com.spy686.fly.flat.ms.realt.by.models.RentFlat;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.List;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Slf4j
class TasksTests extends BaseTest {

    @Test
    void fetchRealtByTest() {
        log.info("fetchRealtByTest");

        List<RentFlat> rentFlatFullList = restRealtByFlatForLongService.getRentFlatList();
    }

}
