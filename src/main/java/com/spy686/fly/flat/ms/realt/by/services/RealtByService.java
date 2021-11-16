package com.spy686.fly.flat.ms.realt.by.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;


@Service
@Slf4j
@AllArgsConstructor
public class RealtByService {

    private RealtByFetchService realtByFetchService;
    private RealtByDeleteNotActualService realtByDeleteNotActualService;

    public void fetch() {
        try {
            log.info("START: {}", "fetch");
            realtByFetchService.fetch();
            log.info("END: {}", "fetch");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteNotActualRealtBy() {
        log.info("START: {}", "deleteNotActualRealtBy");
        realtByDeleteNotActualService.deleteNotActualRealtBy();
        log.info("END: {}", "deleteNotActualRealtBy");
    }
}