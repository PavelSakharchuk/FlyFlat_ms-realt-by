package com.spy686.fly.flat.ms.realt.by.repository;

import com.spy686.fly.flat.ms.realt.by.models.RentFlat;
import com.spy686.fly.flat.ms.realt.by.models.Source;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RentFlatRepository extends MongoRepository<RentFlat, Long> {
    @Override
    void deleteAll(Iterable<? extends RentFlat> entities);
    @Override
    <S extends RentFlat> List<S> saveAll(Iterable<S> entities);

    <S extends RentFlat> List<S> findAllBySource(Source source);

}