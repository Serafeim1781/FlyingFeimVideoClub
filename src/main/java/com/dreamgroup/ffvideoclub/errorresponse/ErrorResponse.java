package com.dreamgroup.ffvideoclub.errorresponse;

import java.util.List;


public record ErrorResponse(org.springframework.http.HttpStatus status, String message, List<String> details) {
}