package com.neothedeveloper.taskflow_server.exception;

public class TaskNotFoundException extends  RuntimeException{

    public TaskNotFoundException(String message){
        super(message);
    } 
}
