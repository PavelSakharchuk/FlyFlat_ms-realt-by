package com.spy686.fly.flat.ms.realt.by.services.data;

import com.spy686.fly.flat.ms.realt.by.models.RentFlat;
import com.spy686.fly.flat.ms.realt.by.models.Source;
import com.spy686.fly.flat.ms.realt.by.repository.RentFlatRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
@AllArgsConstructor
public class RentFlatDataService {

    private final RentFlatRepository accountRepository;


    public void deleteAll(List<RentFlat> rentFlats) {
        log.info("Delete: " + rentFlats.size());
        accountRepository.deleteAll(rentFlats);
    }

    public void saveAll(List<RentFlat> rentFlats) {
        log.info("Save: " + rentFlats.size());
        accountRepository.saveAll(rentFlats);
    }

    public List<RentFlat> getAll() {
        log.info("Get All: " + Source.REALT_BY);
        return accountRepository.findAllBySource(Source.REALT_BY);
    }
}