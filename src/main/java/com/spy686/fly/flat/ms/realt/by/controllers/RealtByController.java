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
    // TODO: 24.10.2021: p.sakharchuj: Need to apply with pool
    private static boolean controllerLocker = false;

    @GetMapping(value = "/realt-by/fetch")
    public void fetchRealtBy() {
        run("/realt-by/fetch", () -> realtByService.fetch());
    }

    @GetMapping(value = "/realt-by/delete-not-actual")
    public void deleteNotActualRealtBy() {
        run("/realt-by/delete-not-actual", () -> realtByService.deleteNotActualRealtBy());
    }

    private void run(String request, Runnable runnable) {
        if (controllerLocker) {
            log.info("Previous process doesn't completed.");
            throw new OrderNotFoundException();
        }
        Executors.newSingleThreadExecutor().submit(() -> {
            log.info("START: {}", request);
            controllerLocker = true;
            runnable.run();
            controllerLocker = false;
            log.info("END: {}", request);
        });
    }

    @ResponseStatus(value = HttpStatus.LOCKED, reason = "Process Locked")
    public class OrderNotFoundException extends RuntimeException {
    }
}