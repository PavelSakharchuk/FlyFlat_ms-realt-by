package com.spy686.fly.flat.ms.realt.by.services.database;

import com.spy686.fly.flat.ms.realt.by.models.RentFlat;
import com.spy686.fly.flat.ms.realt.by.repository.RentFlatRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
@AllArgsConstructor
public class DatabaseRentFlatService {

    private final RentFlatRepository rentFlatRepository;


    public void deleteAll(List<RentFlat> rentFlats) {
        log.info("Delete: " + rentFlats.size());
        rentFlatRepository.deleteAll(rentFlats);
    }

    public void saveAll(List<RentFlat> rentFlats) {
        log.info("Save: " + rentFlats.size());
        rentFlatRepository.saveAll(rentFlats);
    }

    public List<RentFlat> getAll() {
        log.info("Get All: " + RentFlat.Source.REALT_BY);
        return rentFlatRepository.findAllBySource(RentFlat.Source.REALT_BY);
    }
}