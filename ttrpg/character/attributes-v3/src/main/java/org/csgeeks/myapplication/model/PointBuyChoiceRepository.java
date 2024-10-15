package org.csgeeks.myapplication.model;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
// import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource(collectionResourceRel="choices", path="choices")
public interface PointBuyChoiceRepository extends JpaRepository<PointBuyChoice, Integer> {

    // hptt://localhost:8080/choices
    @Query("SELECT choice FROM PointBuyChoice choice WHERE choice.id <= 65")
    List<PointBuyChoice> findAll();

    // http://localhost:8080/choices/<id>
    // @Query("SELECT choice FROM PointBuyChoice choice WHERE choice.id = ?1")
    Optional<PointBuyChoice> findById(Integer id);

    @Query("SELECT choice FROM PointBuyChoice choice WHERE choice.id = ?1")
    List<PointBuyChoice> findByIdList(Integer id);

    // http://localhost:8080/choices/search/getStandardArray
    @Query("SELECT choice FROM PointBuyChoice choice WHERE choice.id = 20")
    List<PointBuyChoice> findStandardArray();

    // http://localhost:8080/choices/search/findByAttribute1?attribute1=<attribute1>
    @Query("SELECT choice FROM PointBuyChoice choice WHERE choice.attribute1 = :attribute1 AND choice.id <= 65")
    List<PointBuyChoice> findByAttribute1(@Param("attribute1") Integer attribute1);

    // http://localhost:8080/choices/search/findByAttribute1AndAttribute2?attribute1=<attribute1>&attribute2=<attribute2>
    @Query("SELECT choice FROM PointBuyChoice choice WHERE choice.attribute1 = :attribute1 AND choice.attribute2 = :attribute2 AND choice.id <= 65")
    List<PointBuyChoice> findByAttribute1AndAttribute2(@Param("attribute1") Integer attribute1, @Param("attribute2") Integer attribute2);

    // http://localhost:8080/choices/search/findByAttribute2AndAttribute1Is15?attribute2=<attribute2>
    @Query("SELECT choice FROM PointBuyChoice choice WHERE choice.attribute2 = :attribute2 AND choice.attribute1 = 15 AND choice.id <= 65")
    List<PointBuyChoice> findByAttribute2AndAttribute1Is15(@Param("attribute2") Integer attribute2);

    // Save to the datastore: either insert or update
    PointBuyChoice save(PointBuyChoice pointBuyChoice);
}
