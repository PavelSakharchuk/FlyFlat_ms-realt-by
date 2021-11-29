package com.spy686.fly.flat.ms.realt.by.services;

import com.spy686.fly.flat.ms.realt.by.models.RentFlat;
import com.spy686.fly.flat.ms.realt.by.services.database.DatabaseRentFlatService;
import com.spy686.fly.flat.ms.realt.by.services.rest.RestRentFlatForLongObjectService;
import com.spy686.fly.flat.ms.realt.by.services.rest.RestRentFlatForLongService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
@Slf4j
@AllArgsConstructor
public class RealtByFetchService {

    private RestRentFlatForLongService restRentFlatForLongService;
    private RestRentFlatForLongObjectService restRentFlatForLongObjectService;
    private DatabaseRentFlatService databaseRentFlatService;

    public void fetch() throws IOException {
        final List<RentFlat> savedRentFlatList = databaseRentFlatService.getAll();
        final Map<Long, RentFlat> savedRentFlatMap = savedRentFlatList.stream()
                .distinct()
                .collect(Collectors.toMap(RentFlat::getObjectId, Function.identity()));
        final List<RentFlat> actualRentFlatList = restRentFlatForLongService.getRentFlatList();

        // Update actualRentFlatList:
        // actual.PriceUsd = saved.PriceUsd: actual.CreateDate=saved.CreateDate; actual.LastUpdate=saved.LastUpdate
        // actual.PriceUsd != saved.PriceUsd: actual.CreateDate=saved.CreateDate; actual.LastUpdate=now
        // not exists: save got object
        // TODO: 17.10.2021: p.sakharchuk: Need to catch some Exceptions
        AtomicInteger notSavedRentFlatsCount = new AtomicInteger(0);
        AtomicInteger updatedPriceRentFlatsCount = new AtomicInteger(0);
        AtomicInteger withoutUpdatedPriceRentFlatsCount = new AtomicInteger(0);
        actualRentFlatList.parallelStream()
                .forEach(actualRentFlat -> {
                    final RentFlat savedRentFlat = Optional.ofNullable(savedRentFlatMap.get(actualRentFlat.getObjectId())).orElse(null);
                    if (null == savedRentFlat) {
                        restRentFlatForLongObjectService.fetchRentFlatData(actualRentFlat);
                        notSavedRentFlatsCount.getAndIncrement();
                    } else {
                        actualRentFlat.setId(savedRentFlat.getId());
                        actualRentFlat.setCreateDate(savedRentFlat.getCreateDate());

                        // Update data got by 'rent/flat-for-long/object/[id]/':
                        // RestRentFlatForLongObjectService#fetchRentFlatData
                        actualRentFlat.setImageLink(savedRentFlat.getImageLink());
                        actualRentFlat.setSellerPhones(savedRentFlat.getSellerPhones());
                        actualRentFlat.setSellerName(savedRentFlat.getSellerName());

                        final int actualPriceUsd = actualRentFlat.getPriceUsd() == null ? 0 : actualRentFlat.getPriceUsd();
                        final int savedPriceUsd = savedRentFlat.getPriceUsd() == null ? 0 : savedRentFlat.getPriceUsd();

                        if (actualPriceUsd == savedPriceUsd) {
                            actualRentFlat.setLastUpdate(savedRentFlat.getLastUpdate());
                            withoutUpdatedPriceRentFlatsCount.getAndIncrement();
                        }
                        if (actualPriceUsd != savedPriceUsd) {
                            actualRentFlat.setLastUpdate(LocalDateTime.now());
                            updatedPriceRentFlatsCount.getAndIncrement();
                        }
                    }
                });

        log.info("Get: {} [{} - new; {} - updated price; {} - without updated price]",
                actualRentFlatList.size(), notSavedRentFlatsCount, updatedPriceRentFlatsCount, withoutUpdatedPriceRentFlatsCount);
        databaseRentFlatService.saveAll(actualRentFlatList);
    }
}