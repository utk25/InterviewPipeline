package com.interview.interviewpipeline.adapter;

import com.interview.interviewpipeline.model.*;
import com.interview.interviewpipeline.service.InterviewService;
import com.interview.interviewpipeline.service.StageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class InterviewAdapter {

    @Autowired
    private StageService stageService;
    
    @Autowired
    private InterviewService interviewService;

    public InterviewDatabaseModel convertToDatabaseModel(NewInterviewRequestModel newInterviewRequestModel, Integer stageId) {
        //Performs ORM
        InterviewDatabaseModel interviewDatabaseModel = new InterviewDatabaseModel();
        interviewDatabaseModel.setName(newInterviewRequestModel.getInterviewName());

        Optional<StageDatabaseModel> maybeStageDatabaseModel = stageService.getStage(stageId);

        if (!maybeStageDatabaseModel.isPresent()) {
            return null;
        }

        StageDatabaseModel stageDatabaseModel = maybeStageDatabaseModel.get();
        int interviewCount = stageDatabaseModel.getInterviewCount();
        stageDatabaseModel.setInterviewCount(interviewCount + 1);

        interviewDatabaseModel.setPosition(interviewCount);
        interviewDatabaseModel.setStageId(stageId);

        return interviewDatabaseModel;
    }

    public void changeInterviewName(UpdateInterviewRequestModel updateInterviewRequestModel, Integer interviewId) {
        Optional<InterviewDatabaseModel> maybeInterviewDatabaseModel = interviewService.getInterview(interviewId);

        if(maybeInterviewDatabaseModel.isPresent()) {
            InterviewDatabaseModel interviewDatabaseModel = maybeInterviewDatabaseModel.get();
            interviewDatabaseModel.setName(updateInterviewRequestModel.getNewName());
            interviewService.updateInterview(interviewDatabaseModel);
        }

    }

    public void changeInterviewPosition(UpdateInterviewRequestModel updateInterviewRequestModel, Integer interviewId, Integer stageId) {
        /* Gets the position of current Interview/Stage and target Interview/Stage and
            increments/decrements all stages position in between
         */
        Optional<InterviewDatabaseModel> maybeInterviewDatabaseModel = interviewService.getInterview(interviewId);
        if(!maybeInterviewDatabaseModel.isPresent()) {
            return;
        }
        InterviewDatabaseModel interviewDatabaseModel = maybeInterviewDatabaseModel.get();
        int currentPosition = interviewDatabaseModel.getPosition();
        int newPosition = updateInterviewRequestModel.getNewPosition();
        InterviewDatabaseModel targetInterviewDatabaseModel = interviewService.findInterviewDatabaseModelByPosition(newPosition, stageId);

        if (currentPosition > newPosition) {
            List<InterviewDatabaseModel> intermediateInterviewDatabaseModels = interviewService.
                    findInterviewDatabaseModelBetweenPositionRange(newPosition + 1, currentPosition - 1, stageId);

            if(!intermediateInterviewDatabaseModels.isEmpty()) {
                for (InterviewDatabaseModel currentInterviewDatabaseModel : intermediateInterviewDatabaseModels) {
                    currentInterviewDatabaseModel.setPosition(currentInterviewDatabaseModel.getPosition() + 1);
                    interviewService.addInterview(currentInterviewDatabaseModel);
                }
            }

            targetInterviewDatabaseModel.setPosition(targetInterviewDatabaseModel.getPosition() + 1);
        } else {
            List<InterviewDatabaseModel> intermediateInterviewDatabaseModels = interviewService.
                    findInterviewDatabaseModelBetweenPositionRange(currentPosition + 1, newPosition - 1, stageId);

            if(!intermediateInterviewDatabaseModels.isEmpty()) {
                for (InterviewDatabaseModel currentInterviewDatabaseModel : intermediateInterviewDatabaseModels) {
                    currentInterviewDatabaseModel.setPosition(currentInterviewDatabaseModel.getPosition() - 1);
                    interviewService.addInterview(currentInterviewDatabaseModel);
                }
            }

            targetInterviewDatabaseModel.setPosition(targetInterviewDatabaseModel.getPosition()- 1);
        }
        interviewDatabaseModel.setPosition(newPosition);
        interviewService.addInterview(interviewDatabaseModel);
        interviewService.addInterview(targetInterviewDatabaseModel);
    }

    public void deleteInterview(Integer interviewId, Integer stageId) {
        /* Gets the position of current Interview/Stage
            increments/decrements all stages position above it
         */
        Optional<InterviewDatabaseModel> maybeInterviewDatabaseModel = interviewService.getInterview(interviewId);
        if(!maybeInterviewDatabaseModel.isPresent()) {
            return;
        }
        InterviewDatabaseModel interviewDatabaseModel = maybeInterviewDatabaseModel.get();
        List<InterviewDatabaseModel> interviewDatabaseModels = interviewService.
                findInterviewDatabaseModelGreaterThanPosition(interviewDatabaseModel.getPosition(), stageId);

        if(!interviewDatabaseModels.isEmpty()) {
            for (InterviewDatabaseModel currentInterviewDatabaseModel : interviewDatabaseModels) {
                currentInterviewDatabaseModel.setPosition(currentInterviewDatabaseModel.getPosition() - 1);
                interviewService.addInterview(currentInterviewDatabaseModel);
            }
        }

        interviewService.deleteInterview(interviewId);
        Optional<StageDatabaseModel> maybeStageDatabaseModel = stageService.getStage(stageId);
        if(!maybeStageDatabaseModel.isPresent()) {
            return;
        }
        StageDatabaseModel stageDatabaseModel = maybeStageDatabaseModel.get();
        stageDatabaseModel.setInterviewCount(stageDatabaseModel.getInterviewCount() - 1);
    }
}
