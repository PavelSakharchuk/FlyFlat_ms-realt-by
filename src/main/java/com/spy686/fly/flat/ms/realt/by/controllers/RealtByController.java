package com.spy686.fly.flat.ms.realt.by.controllers;

import com.spy686.fly.flat.ms.realt.by.services.RealtByService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Executors;


/**
 * Enter point for requests from Telegram bot
 */
@RestController
@Slf4j
@AllArgsConstructor
public class RealtByController {

    private RealtByService realtByService;
    private static boolean controllerLocker = false;

    @GetMapping(value = "/realt-by/fetch")
    public void fetchRealtBy() {
        if (controllerLocker) {
            log.info("Before process doesn't completed.");
            throw new OrderNotFoundException();
        }
        Executors.newSingleThreadExecutor().submit(() -> {
            log.info("START: /realt-by/fetch");
            controllerLocker = true;
            realtByService.fetchRealtBy();
            controllerLocker = false;
            log.info("END: /realt-by/fetch");
        });
    }

    @ResponseStatus(value = HttpStatus.LOCKED, reason = "Process Locked")
    public class OrderNotFoundException extends RuntimeException {
    }
}