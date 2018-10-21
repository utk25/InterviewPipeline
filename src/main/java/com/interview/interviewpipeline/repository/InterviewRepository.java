package com.interview.interviewpipeline.repository;


import com.interview.interviewpipeline.model.InterviewDatabaseModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InterviewRepository extends CrudRepository<InterviewDatabaseModel, Integer> {


    @Query("select distinct idm " +
            "from InterviewDatabaseModel idm " +
            "where idm.stageId = :stageId " +
            "order by idm.position asc")
    List<InterviewDatabaseModel> findInterviewDatabaseModelSortedByPosition(@Param("stageId") int stageId);

    @Query("select distinct idm " +
            "from InterviewDatabaseModel idm " +
            "where idm.stageId = :stageId " +
            "and idm.position >= :start_id and idm.position <= :end_id")
    List<InterviewDatabaseModel> findInterviewDatabaseModelBetweenPositionRange(@Param("start_id") int start_id,
                                                                                @Param("end_id") int end_id,
                                                                                @Param("stageId") int stageId);

    @Query("select distinct idm " +
            "from InterviewDatabaseModel idm " +
            "where idm.stageId = :stageId " +
            "and idm.position = :id")
    InterviewDatabaseModel findInterviewDatabaseModelByPosition(@Param("id") int id, @Param("stageId") int stageId);

    @Query("select distinct idm " +
            "from InterviewDatabaseModel idm " +
            "where idm.stageId = :stageId " +
            "and idm.position > :id")
    List<InterviewDatabaseModel> findInterviewDatabaseModelGreaterThanPosition(@Param("id") int id, @Param("stageId") int stageId);
}
