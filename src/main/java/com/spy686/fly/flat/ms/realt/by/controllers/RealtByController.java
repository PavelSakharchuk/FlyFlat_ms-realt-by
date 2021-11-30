package com.spy686.fly.flat.ms.realt.by.controllers;

import com.spy686.fly.flat.ms.realt.by.services.RealtByService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Queue;
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
    private static LocalDateTime lastStartTaskDate = LocalDateTime.now();
    private static final int pendingTimeoutMinutes = 15;

    private static final Queue<Runnable> tasksQueue = new LinkedList<>();


    @GetMapping(value = "/realt-by/fetch")
    public void fetchRealtBy() {
        run("/realt-by/fetch", () -> realtByService.fetch());
    }

    @GetMapping(value = "/realt-by/delete-not-actual")
    public void deleteNotActualRealtBy() {
        run("/realt-by/delete-not-actual", () -> realtByService.deleteNotActualRealtBy());
    }

    private void run(String request, Runnable runnable) {
        tasksQueue.add(runnable);
        log.info("Added to Queue [{}]: {}", tasksQueue.size(), request);

        if (LocalDateTime.now().isAfter(lastStartTaskDate.plusMinutes(pendingTimeoutMinutes))) {
            log.info("Last task is pending.");
            controllerLocker = false;
        }

        if (!controllerLocker) {
            controllerLocker = true;
            Executors.newSingleThreadExecutor().submit(() -> {
                while (tasksQueue.peek() != null) {
                    log.info("In Queue: {}", tasksQueue.size());
                    lastStartTaskDate = LocalDateTime.now();
                    System.gc();
                    tasksQueue.poll().run();
                }
                log.info("Queue is empty");
                controllerLocker = false;
            });
        }
    }

    @ResponseStatus(value = HttpStatus.LOCKED, reason = "Process Locked")
    public class OrderNotFoundException extends RuntimeException {
    }
}