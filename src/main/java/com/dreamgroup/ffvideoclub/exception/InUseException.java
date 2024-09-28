package com.dreamgroup.ffvideoclub.exception;

public class InUseException extends RuntimeException {
    public InUseException(String message) {
        super(message);
    }


//    public InUseException(InUseException exception) {
//        super(exception.getMessage());
//    }
}
