package com.interview.interviewpipeline.controller;

import com.interview.interviewpipeline.adapter.InterviewAdapter;
import com.interview.interviewpipeline.exception.SimultaneousEditException;
import com.interview.interviewpipeline.model.InterviewDatabaseModel;
import com.interview.interviewpipeline.model.NewInterviewRequestModel;
import com.interview.interviewpipeline.model.UpdateInterviewRequestModel;
import com.interview.interviewpipeline.service.InterviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class InterviewController {

    @Autowired
    private InterviewService interviewService;

    @Autowired
    private InterviewAdapter interviewAdapter;

    @RequestMapping("/interviewPipeline/stages/{stageId}/interviews/")
    public List<InterviewDatabaseModel> getAllInterviews(@PathVariable Integer stageId) {
        return interviewService.getAllInterviews(stageId);
    }

    @RequestMapping("/interviewPipeline/stages/{stageId}/interviews/{interviewId}")
    public Optional<InterviewDatabaseModel> getInterview(@PathVariable Integer interviewId) {
        return interviewService.getInterview(interviewId);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/interviewPipeline/stages/{stageId}/interviews")
    public void addInterview(@RequestBody NewInterviewRequestModel newInterviewRequestModel, @PathVariable Integer stageId) {

        InterviewDatabaseModel interviewDatabaseModel = interviewAdapter.convertToDatabaseModel(newInterviewRequestModel, stageId);
        interviewService.addInterview(interviewDatabaseModel);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/interviewPipeline/stages/{stageId}/interviews/{interviewId}")
    public void updateInterview(@RequestBody UpdateInterviewRequestModel updateInterviewRequestModel, @PathVariable Integer interviewId, @PathVariable Integer stageId) {
        if(updateInterviewRequestModel.getNewName() != null && updateInterviewRequestModel.getNewPosition() != null) {
            throw new SimultaneousEditException("Cannot edit two properties simultaneously");
        }
        if(updateInterviewRequestModel.getNewPosition() != null) {
            interviewAdapter.changeInterviewPosition(updateInterviewRequestModel, interviewId, stageId);
        }
        else {
            interviewAdapter.changeInterviewName(updateInterviewRequestModel, interviewId);
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/interviewPipeline/stages/{stageId}/interviews/{interviewId}")
    public void deleteInterview(@PathVariable Integer interviewId, @PathVariable Integer stageId) {
        interviewAdapter.deleteInterview(interviewId, stageId);
    }

}