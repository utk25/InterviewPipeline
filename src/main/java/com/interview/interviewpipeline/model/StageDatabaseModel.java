package com.interview.interviewpipeline.model;


import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "stage_table")
public class StageDatabaseModel {

    static public Integer currentPosition = 0;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "stage_id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "stage_id")
    private List<InterviewDatabaseModel> interviewDatabaseModel;

    @Column(name = "position")
    private Integer position;

    @Column(name = "interviewCount")
    private Integer interviewCount = 0;

    @Column(name = "permanent")
    private boolean isPermanentStage = false;

}

