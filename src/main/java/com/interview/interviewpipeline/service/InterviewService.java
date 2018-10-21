package com.interview.interviewpipeline.service;


import com.interview.interviewpipeline.model.InterviewDatabaseModel;
import com.interview.interviewpipeline.repository.InterviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InterviewService {

    @Autowired
    private InterviewRepository interviewRepository;

    public List<InterviewDatabaseModel> getAllInterviews(int stageId) {
        List<InterviewDatabaseModel> InterviewDatabaseModel;
        InterviewDatabaseModel =  interviewRepository.findInterviewDatabaseModelSortedByPosition(stageId);
        return InterviewDatabaseModel;
    }

    public InterviewDatabaseModel addInterview(InterviewDatabaseModel InterviewDatabaseModel) {
        return interviewRepository.save(InterviewDatabaseModel);
    }

    public Optional<InterviewDatabaseModel> getInterview(int id) {
        return interviewRepository.findById(id);
    }

    public void updateInterview(InterviewDatabaseModel InterviewDatabaseModel) {
        interviewRepository.save(InterviewDatabaseModel);
    }

    public InterviewDatabaseModel findInterviewDatabaseModelByPosition(int position, int stageId) {
        return interviewRepository.findInterviewDatabaseModelByPosition(position, stageId);
    }

    public List<InterviewDatabaseModel> findInterviewDatabaseModelBetweenPositionRange(int start_id, int end_id, int stageId) {
        return interviewRepository.findInterviewDatabaseModelBetweenPositionRange(start_id, end_id, stageId);
    }

    public List<InterviewDatabaseModel> findInterviewDatabaseModelGreaterThanPosition(int position, int stageId) {
        return interviewRepository.findInterviewDatabaseModelGreaterThanPosition(position, stageId);
    }

    public void deleteInterview(int interviewId) {
        interviewRepository.deleteById(interviewId);
    }
}
