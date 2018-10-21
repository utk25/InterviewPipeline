package com.interview.interviewpipeline.adapter;

import com.interview.interviewpipeline.constants.StageName;
import com.interview.interviewpipeline.exception.PermanentStageModificationException;
import com.interview.interviewpipeline.model.StageDatabaseModel;
import com.interview.interviewpipeline.model.NewStageRequestModel;
import com.interview.interviewpipeline.model.UpdateStageRequestModel;
import com.interview.interviewpipeline.service.StageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class StageAdapter {

    @Autowired
    private StageService stageService;

    public StageDatabaseModel convertToDatabaseModel(NewStageRequestModel newStageRequestModel) {
        //Performs ORM
        StageDatabaseModel stageDatabaseModel = new StageDatabaseModel();
        stageDatabaseModel.setName(newStageRequestModel.getStageName());
        stageDatabaseModel.setInterviewDatabaseModel(new ArrayList<>());
        stageDatabaseModel.setPosition(StageDatabaseModel.currentPosition++);
        return stageDatabaseModel;
    }

    public void changeStageName(UpdateStageRequestModel updateStageRequestModel, Integer stageId) {

        Optional<StageDatabaseModel> maybeStageDatabaseModel = stageService.getStage(stageId);

        if(maybeStageDatabaseModel.isPresent()) {
            StageDatabaseModel stageDatabaseModel = maybeStageDatabaseModel.get();

            if (stageDatabaseModel.isPermanentStage()) {
                throw new PermanentStageModificationException("This stage name can't be modified");
            }

            stageDatabaseModel.setName(updateStageRequestModel.getNewName());
            stageService.updateStage(stageDatabaseModel);
        }

    }

    public void changeStagePosition(UpdateStageRequestModel updateStageRequestModel, Integer stageId) {
        /* Gets the position of current Interview/Stage and target Interview/Stage and
            increments/decrements all stages position in between
         */
        Optional<StageDatabaseModel> maybeStageDatabaseModel = stageService.getStage(stageId);
        if(!maybeStageDatabaseModel.isPresent()) {
            return;
        }
        StageDatabaseModel stageDatabaseModel = maybeStageDatabaseModel.get();

        if (stageDatabaseModel.isPermanentStage()) {
            throw new PermanentStageModificationException("This stage position can't be modified");
        }

        int currentPosition = stageDatabaseModel.getPosition();
        int newPosition = updateStageRequestModel.getNewPosition();
        StageDatabaseModel targetStageDatabaseModel = stageService.findStageDatabaseModelByPosition(newPosition);

        if (currentPosition > newPosition) {
            List<StageDatabaseModel> intermediateStageDatabaseModels = stageService.
                    findStageDatabaseModelBetweenPositionRange(newPosition + 1, currentPosition - 1);

            if(!intermediateStageDatabaseModels.isEmpty()) {
                for (StageDatabaseModel currentStageDatabaseModel : intermediateStageDatabaseModels) {
                    currentStageDatabaseModel.setPosition(currentStageDatabaseModel.getPosition() + 1);
                    stageService.addStage(currentStageDatabaseModel);
                }
            }

            targetStageDatabaseModel.setPosition(targetStageDatabaseModel.getPosition() + 1);
        } else {
            List<StageDatabaseModel> intermediateStageDatabaseModels = stageService.
                    findStageDatabaseModelBetweenPositionRange(currentPosition + 1, newPosition - 1);

            if(!intermediateStageDatabaseModels.isEmpty()) {
                for (StageDatabaseModel currentStageDatabaseModel : intermediateStageDatabaseModels) {
                    currentStageDatabaseModel.setPosition(currentStageDatabaseModel.getPosition() - 1);
                    stageService.addStage(currentStageDatabaseModel);
                }
            }

            targetStageDatabaseModel.setPosition(targetStageDatabaseModel.getPosition()- 1);
        }
        stageDatabaseModel.setPosition(newPosition);
        stageService.addStage(stageDatabaseModel);
        stageService.addStage(targetStageDatabaseModel);
    }

    public void deleteStage(Integer stageId) {
        /* Gets the position of current Interview/Stage
            increments/decrements all stages position above it
         */
        Optional<StageDatabaseModel> maybeStageDatabaseModel = stageService.getStage(stageId);
        if(!maybeStageDatabaseModel.isPresent()) {
            return;
        }
        StageDatabaseModel stageDatabaseModel = maybeStageDatabaseModel.get();

        if (stageDatabaseModel.isPermanentStage()) {
            throw new PermanentStageModificationException("This stage can't be deleted");
        }

        List<StageDatabaseModel> stageDatabaseModels = stageService.
                findStageDatabaseModelGreaterThanPosition(stageDatabaseModel.getPosition());

        if(!stageDatabaseModels.isEmpty()) {
            for (StageDatabaseModel currentStageDatabaseModel : stageDatabaseModels) {
                currentStageDatabaseModel.setPosition(currentStageDatabaseModel.getPosition() - 1);
                stageService.addStage(currentStageDatabaseModel);
            }
        }

        stageService.deleteStage(stageId);
        StageDatabaseModel.currentPosition--;
    }
}
