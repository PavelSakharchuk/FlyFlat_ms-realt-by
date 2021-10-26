package com.spy686.fly.flat.ms.realt.by.services;

import com.spy686.fly.flat.ms.realt.by.models.RentFlat;
import com.spy686.fly.flat.ms.realt.by.services.database.DatabaseRentFlatService;
import com.spy686.fly.flat.ms.realt.by.services.rest.RestRentFlatForLongService;
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
public class RealtByFetchService {

    private RestRentFlatForLongService restRentFlatForLongService;
    private DatabaseRentFlatService databaseRentFlatService;

    public void fetch() {
        List<RentFlat> savedRentFlatList = databaseRentFlatService.getAll();
        Map<Long, RentFlat> savedRentFlatMap = savedRentFlatList.stream()
                .distinct()
                .collect(Collectors.toMap(RentFlat::getObjectId, Function.identity()));
        List<RentFlat> actualRentFlatList = restRentFlatForLongService.getRentFlatList();

        // Update actualRentFlatList:
        // actual.PriceUsd = saved.PriceUsd: actual.CreateDate=saved.CreateDate; actual.LastUpdate=saved.LastUpdate
        // actual.PriceUsd != saved.PriceUsd: actual.CreateDate=saved.CreateDate; actual.LastUpdate=now
        // not exists: save got object
        // TODO: 17.10.2021: p.sakharchuk: Need to catch some Exceptions
        actualRentFlatList.parallelStream()
                .forEach(actualRentFlat -> {
                    RentFlat savedRentFlat = Optional.ofNullable(savedRentFlatMap.get(actualRentFlat.getObjectId())).orElse(null);
                    if (savedRentFlat != null) {
                        actualRentFlat.setId(savedRentFlat.getId());
                        actualRentFlat.setCreateDate(savedRentFlat.getCreateDate());

                        int actualPriceUsd = actualRentFlat.getPriceUsd() == null ? 0 : actualRentFlat.getPriceUsd();
                        int savedPriceUsd = savedRentFlat.getPriceUsd() == null ? 0 : savedRentFlat.getPriceUsd();

                        if (actualPriceUsd == savedPriceUsd) {
                            actualRentFlat.setLastUpdate(savedRentFlat.getLastUpdate());
                        }
                        if (actualPriceUsd != savedPriceUsd) {
                            actualRentFlat.setLastUpdate(LocalDateTime.now());
                        }
                    }
                });

        databaseRentFlatService.saveAll(actualRentFlatList);
    }
}