package com.fly.flat.ms.realt.by.service;

import org.springframework.stereotype.Service;


@Service
public class RentRequestForRealtByImpl implements RentRequestService{
    @Override
    public String getFlats() {
        return "realt.by";
    }
}
