package com.interview.interviewpipeline.repository;

import com.interview.interviewpipeline.model.StageDatabaseModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StageRepository extends CrudRepository<StageDatabaseModel, Integer> {

    @Query("select distinct sdm " +
             "from StageDatabaseModel sdm " +
             "order by sdm.position desc")
    List<StageDatabaseModel> findStageDatabaseModelSortedByPosition();

    @Query("select distinct sdm " +
            "from StageDatabaseModel sdm " +
            "where sdm.position >= :start_id and sdm.position <= :end_id")
    List<StageDatabaseModel> findStageDatabaseModelBetweenPositionRange(@Param("start_id") int start_id, @Param("end_id") int end_id);

    @Query("select distinct sdm " +
            "from StageDatabaseModel sdm " +
            "where sdm.position = :id")
    StageDatabaseModel findStageDatabaseModelByPosition(@Param("id") int id);

    @Query("select distinct sdm " +
            "from StageDatabaseModel sdm " +
            "where sdm.position > :id")
    List<StageDatabaseModel> findStageDatabaseModelGreaterThanPosition(@Param("id") int id);
}