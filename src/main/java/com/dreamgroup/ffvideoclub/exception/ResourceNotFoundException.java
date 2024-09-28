package com.dreamgroup.ffvideoclub.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }

//    public ResourceNotFoundException(ResourceNotFoundException exception) {
//        super(exception.getMessage());
//    }
}
