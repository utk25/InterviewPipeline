package com.interview.interviewpipeline.exception;

public class SimultaneousEditException extends RuntimeException {

    public  SimultaneousEditException(String message)
    {
        super(message);
    }
}
