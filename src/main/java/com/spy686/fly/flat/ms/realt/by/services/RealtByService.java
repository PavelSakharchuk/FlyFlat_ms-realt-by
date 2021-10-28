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
            realtByFetchService.fetch();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteNotActualRealtBy() {
        realtByDeleteNotActualService.deleteNotActualRealtBy();
    }
}