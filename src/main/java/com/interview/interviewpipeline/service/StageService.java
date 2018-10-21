package com.interview.interviewpipeline.service;

import com.interview.interviewpipeline.model.StageDatabaseModel;
import com.interview.interviewpipeline.repository.StageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class StageService {

    @Autowired
    private StageRepository stageRepository;

    public List<StageDatabaseModel> getAllStages() {
        List<StageDatabaseModel> stageDatabaseModels = new ArrayList<>();
        stageDatabaseModels =  stageRepository.findStageDatabaseModelSortedByPosition();
        for (StageDatabaseModel currentStageDatabaseModel : stageDatabaseModels) {
            Collections.sort(currentStageDatabaseModel.getInterviewDatabaseModel());
        }
        return stageDatabaseModels;
    }

    public StageDatabaseModel addStage(StageDatabaseModel stageDatabaseModel) {
        return stageRepository.save(stageDatabaseModel);
    }

    public Optional<StageDatabaseModel> getStage(int id) {
        return stageRepository.findById(id);
    }

    public void updateStage(StageDatabaseModel stageDatabaseModel) {
        stageRepository.save(stageDatabaseModel);
    }


    public StageDatabaseModel findStageDatabaseModelByPosition(int position) {
        return stageRepository.findStageDatabaseModelByPosition(position);
    }

    public List<StageDatabaseModel> findStageDatabaseModelBetweenPositionRange(int start_id, int end_id) {
        return stageRepository.findStageDatabaseModelBetweenPositionRange(start_id, end_id);
    }

    public List<StageDatabaseModel> findStageDatabaseModelGreaterThanPosition(int position) {
        return stageRepository.findStageDatabaseModelGreaterThanPosition(position);
    }

    public void deleteStage(int stageId) {
        stageRepository.deleteById(stageId);
    }

}
