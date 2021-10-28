package com.spy686.fly.flat.ms.realt.by;

import com.spy686.fly.flat.ms.realt.by.services.RealtByFetchService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Slf4j
class TasksTests extends BaseTest {

    @Autowired
    private RealtByFetchService realtByFetchService;

    @SneakyThrows
    @Test
    void fetchRealtByTest() {
        realtByFetchService.fetch();
    }

}
