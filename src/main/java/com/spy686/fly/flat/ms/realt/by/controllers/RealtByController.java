package com.spy686.fly.flat.ms.realt.by.controllers;

import com.spy686.fly.flat.ms.realt.by.rest.services.RestRealtByFlatForLongService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Executors;


/**
 * Enter point for requests from Telegram bot
 */
@RestController
@Slf4j
public class RealtByController {

    @GetMapping(value = "/realt-by/fetch")
    public void fetchRealtBy() {
        Executors.newSingleThreadExecutor().submit(() -> {
            log.info("START: /realt-by/fetch");
            new RestRealtByFlatForLongService().getRentFlatList();
            log.info("END: /realt-by/fetch");
        });
    }
}