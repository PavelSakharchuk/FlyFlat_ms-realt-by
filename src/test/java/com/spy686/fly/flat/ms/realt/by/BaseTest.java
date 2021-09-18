package com.spy686.fly.flat.ms.realt.by;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Slf4j
public abstract class BaseTest {

    @BeforeAll
    public static void beforeAll() {
        log.info("Start.");
    }
}
