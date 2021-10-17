package com.spy686.fly.flat.ms.realt.by.services;

import com.spy686.fly.flat.ms.realt.by.models.RentFlat;
import com.spy686.fly.flat.ms.realt.by.services.data.RentFlatDataService;
import com.spy686.fly.flat.ms.realt.by.services.rest.RentFlatService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
@Slf4j
@AllArgsConstructor
public class RealtByService {

    private RentFlatService rentFlatService;
    private RentFlatDataService rentFlatDataService;

    public void fetchRealtBy() {
        List<RentFlat> actualRentFlatList = rentFlatService.getRentFlatList();
        Map<Long, RentFlat> actualRentFlatMap = actualRentFlatList.stream()
                .collect(Collectors.toMap(RentFlat::getObjectId, Function.identity()));

        List<RentFlat> savedRentFlatList = rentFlatDataService.getAll();
        Map<Long, RentFlat> savedRentFlatMap = savedRentFlatList.stream()
                .distinct()
                .collect(Collectors.toMap(RentFlat::getObjectId, Function.identity()));

        List<RentFlat> existedRentFlatList = savedRentFlatList.stream()
                .filter(savedRentFlat -> Optional.ofNullable(actualRentFlatMap.get(savedRentFlat.getObjectId())).isPresent())
                .collect(Collectors.toList());

        // Update actualRentFlatList:
        // actual.PriceUsd = saved.PriceUsd: actual.CreateDate=saved.CreateDate; actual.LastUpdate=saved.LastUpdate
        // actual.PriceUsd != saved.PriceUsd: actual.CreateDate=saved.CreateDate; actual.LastUpdate=now
        // not exists: save got object
        actualRentFlatList.stream()
                .forEach(actualRentFlat -> {
                    RentFlat savedRentFlat = Optional.ofNullable(savedRentFlatMap.get(actualRentFlat.getObjectId())).orElse(null);
                    if (savedRentFlat != null) {
                        System.out.println("savedRentFlat: " + savedRentFlat.getObjectId());
                        System.out.println("savedRentFlat: " + savedRentFlat.getLink());
                        int actualPriceUsd = actualRentFlat.getPriceUsd() == null ? 0 : actualRentFlat.getPriceUsd();
                        int savedPriceUsd = savedRentFlat.getPriceUsd() == null ? 0 : savedRentFlat.getPriceUsd();
                        if (actualPriceUsd == savedPriceUsd) {
                            actualRentFlat.setCreateDate(savedRentFlat.getCreateDate());
                            actualRentFlat.setLastUpdate(savedRentFlat.getLastUpdate());
                        }
                        if (actualPriceUsd != savedPriceUsd) {
                            actualRentFlat.setCreateDate(savedRentFlat.getCreateDate());
                            actualRentFlat.setLastUpdate(LocalDateTime.now());
                        }
                    }
                });

        rentFlatDataService.deleteAll(existedRentFlatList);
        rentFlatDataService.saveAll(actualRentFlatList);
    }
}