package com.spy686.fly.flat.ms.realt.by.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Enter point for requests from Telegram bot
 */
@RestController
public class WebHookController {

    @GetMapping(value = "/fetch")
    public String fetchRealtBy() {
        return "fetchRealtBy";
    }
}