package com.spy686.fly.flat.ms.realt.by.services;

import com.spy686.fly.flat.ms.realt.by.models.RentFlat;
import com.spy686.fly.flat.ms.realt.by.services.database.DatabaseRentFlatService;
import com.spy686.fly.flat.ms.realt.by.services.rest.RestRentFlatForLongObjectService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


@Service
@Slf4j
@AllArgsConstructor
public class RealtByDeleteNotActualService {

    private RestRentFlatForLongObjectService restRentFlatForLongObjectService;
    private DatabaseRentFlatService databaseRentFlatService;

    public void deleteNotActualRealtBy() {
        List<RentFlat> savedRentFlatList = databaseRentFlatService.getAll();

        int rentFlatListSize = savedRentFlatList.size();
        AtomicInteger number = new AtomicInteger(1);
        savedRentFlatList.parallelStream()
                .forEach(rentFlat -> {
                    log.info("Fetch {}/{}: {} [{}]", number.getAndIncrement(), rentFlatListSize, rentFlat.getObjectId(), rentFlat.getSource());
                    restRentFlatForLongObjectService.fetchRentFlatData(rentFlat);
                });

        List<RentFlat> notActualRentFlatList = savedRentFlatList.parallelStream()
                .filter(rentFlat -> !rentFlat.isActual()).collect(Collectors.toList());
        List<RentFlat> actualRentFlatList = savedRentFlatList.parallelStream()
                .filter(RentFlat::isActual).collect(Collectors.toList());

        databaseRentFlatService.deleteAll(notActualRentFlatList);
        databaseRentFlatService.saveAll(actualRentFlatList);
    }
}