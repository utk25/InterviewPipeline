package com.interview.interviewpipeline.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "Interview_table")
public class InterviewDatabaseModel implements Comparable<InterviewDatabaseModel> {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "interview_id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "stage_id")
    private Integer stageId;

    @Column(name = "position")
    private Integer position;

    @Override
    public int compareTo(InterviewDatabaseModel interviewDatabaseModel) {
        return this.getPosition() - interviewDatabaseModel.getPosition();
    }
}


