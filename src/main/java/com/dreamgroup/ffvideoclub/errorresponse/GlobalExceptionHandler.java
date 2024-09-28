package com.dreamgroup.ffvideoclub.errorresponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.dreamgroup.ffvideoclub.exception.*;

@ControllerAdvice
public class GlobalExceptionHandler  {

    private static final Map<Class<? extends Exception>, HttpStatus> EXCEPTION_STATUS_MAP = Map.of(
            ResourceNotFoundException.class, HttpStatus.NOT_FOUND,
            InUseException.class, HttpStatus.CONFLICT,
            InvalidResourceException.class, HttpStatus.BAD_REQUEST,
            InsufficientPaymentException.class, HttpStatus.BAD_REQUEST
    );

    @ExceptionHandler({
            ResourceNotFoundException.class,
            InUseException.class,
            InvalidResourceException.class,
            InsufficientPaymentException.class
    })
    public final ResponseEntity<ErrorResponse> handleCustomExceptions(Exception ex) {
        HttpStatus httpStatus = EXCEPTION_STATUS_MAP.get(ex.getClass());

        List<String> errorDetails = new ArrayList<>();
        String errorMessage = ex.getMessage();

        ErrorResponse errorResponse = new ErrorResponse(httpStatus, errorMessage, errorDetails);
        return new ResponseEntity<>(errorResponse, httpStatus);
    }
}

////// old code with individual handlers ///////
//    @ExceptionHandler(ResourceNotFoundException.class)
//    public final ResponseEntity<ErrorResponse> handleResourceNotFoundException(
//            ResourceNotFoundException ex) {
//
//        List<String> details = new ArrayList<>();
//        String message = ex.getMessage();
//
//        ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND, message, details);
//        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
//    }
//
//    @ExceptionHandler(InUseException.class)
//    public final ResponseEntity<ErrorResponse> handleInUseException(
//            InUseException ex) {
//
//        List<String> details = new ArrayList<>();
//        String message = ex.getMessage();
//
//        ErrorResponse error = new ErrorResponse(HttpStatus.CONFLICT, message, details);
//        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
//    }
//    .......
//}