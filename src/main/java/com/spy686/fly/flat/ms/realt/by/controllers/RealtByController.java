package com.spy686.fly.flat.ms.realt.by.controllers;

import com.spy686.fly.flat.ms.realt.by.rest.services.RestRealtByFlatForLongService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Enter point for requests from Telegram bot
 */
@RestController
public class RealtByController {

    @GetMapping(value = "/fetch")
    public String fetchRealtBy() {
        return new RestRealtByFlatForLongService().getRealtByRentFaltList().toString();
    }
}