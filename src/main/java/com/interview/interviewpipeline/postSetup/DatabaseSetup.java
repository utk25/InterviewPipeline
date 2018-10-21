package com.interview.interviewpipeline.postSetup;


import com.interview.interviewpipeline.constants.StageName;
import com.interview.interviewpipeline.model.StageDatabaseModel;
import com.interview.interviewpipeline.service.StageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class DatabaseSetup implements CommandLineRunner {

    @Autowired
    private StageService stageService;

    @Override
    public void run(String...args) throws Exception {
        StageDatabaseModel stageDatabaseModel = new StageDatabaseModel();
        stageDatabaseModel.setName(StageName.LAST_STAGE_NAME);
        stageDatabaseModel.setPermanentStage(true);
        stageDatabaseModel.setPosition(StageDatabaseModel.currentPosition++);

        stageService.addStage(stageDatabaseModel);

        stageDatabaseModel = new StageDatabaseModel();
        stageDatabaseModel.setName(StageName.PENULTIMATE_STAGE_NAME);
        stageDatabaseModel.setPermanentStage(true);
        stageDatabaseModel.setPosition(StageDatabaseModel.currentPosition++);

        stageService.addStage(stageDatabaseModel);
    }
}
