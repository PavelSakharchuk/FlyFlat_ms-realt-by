package com.spy686.fly.flat.ms.realt.by.services.database;

import com.spy686.fly.flat.ms.realt.by.models.RentFlat;
import com.spy686.fly.flat.ms.realt.by.repository.RentFlatRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
@AllArgsConstructor
public class DatabaseRentFlatService {

    private final RentFlatRepository rentFlatRepository;


    public void deleteAll(List<RentFlat> rentFlats) {
        List<Long> rentFlatObjectIds = rentFlats.stream()
                .map(RentFlat::getObjectId)
                .collect(Collectors.toList());

        log.info("Delete {}: {}", rentFlats.size(), rentFlatObjectIds);
        rentFlatRepository.deleteAll(rentFlats);
        log.info("Deleted {}: {}", rentFlats.size(), rentFlatObjectIds);
    }

    public void saveAll(List<RentFlat> rentFlats) {
        log.info("Save: " + rentFlats.size());
        rentFlatRepository.saveAll(rentFlats);
        log.info("Saved: " + rentFlats.size());
    }

    public List<RentFlat> getAll() {
        log.info("Get All: " + RentFlat.Source.REALT_BY);
        List<RentFlat> rentFlats = rentFlatRepository.findAllBySource(RentFlat.Source.REALT_BY);
        log.info("Got All: " + RentFlat.Source.REALT_BY);
        return rentFlats;
    }
}