package com.interview.interviewpipeline.controller;

import com.interview.interviewpipeline.adapter.StageAdapter;
import com.interview.interviewpipeline.exception.SimultaneousEditException;
import com.interview.interviewpipeline.model.StageDatabaseModel;
import com.interview.interviewpipeline.model.NewStageRequestModel;
import com.interview.interviewpipeline.model.UpdateStageRequestModel;
import com.interview.interviewpipeline.service.StageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
public class StageController {

    @Autowired
    private StageService stageService;

    @Autowired
    private StageAdapter stageAdapter;

    @RequestMapping("/interviewPipeline/stages/")
    public List<StageDatabaseModel> getAllStages() {
        return stageService.getAllStages();
    }

    @RequestMapping("/interviewPipeline/stages/{stageId}")
    public Optional<StageDatabaseModel> getStage(@PathVariable int stageId) {
        return stageService.getStage(stageId);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/interviewPipeline/stages")
    public void addStage(@RequestBody NewStageRequestModel newStageRequestModel) {

        StageDatabaseModel stageDatabaseModel = stageAdapter.convertToDatabaseModel(newStageRequestModel);
        stageService.addStage(stageDatabaseModel);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/interviewPipeline/stages/{stageId}")
    public void updateStage(@RequestBody UpdateStageRequestModel updateStageRequestModel, @PathVariable Integer stageId) {

        if(updateStageRequestModel.getNewName() != null && updateStageRequestModel.getNewPosition() != null) {
            throw new SimultaneousEditException("Cannot edit two properties simultaneously");
        }
        if(updateStageRequestModel.getNewPosition() != null) {
            stageAdapter.changeStagePosition(updateStageRequestModel, stageId);
        }
        else {
            stageAdapter.changeStageName(updateStageRequestModel, stageId);
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/interviewPipeline/stages/{stageId}")
    public void deleteStage(@PathVariable int stageId) {
        stageAdapter.deleteStage(stageId);
    }
}