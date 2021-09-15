package com.fly.flat.ms.realt.by.controller;

import com.fly.flat.ms.realt.by.service.RentRequestService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@AllArgsConstructor
public class RealtByController {

    private RentRequestService rentRequestForRealtBy;

    @GetMapping
    public String getRent(){
        return rentRequestForRealtBy.getFlats();
    }
}
