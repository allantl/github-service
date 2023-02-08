package com.github.allantl.githubservice.controller;

import com.github.allantl.githubservice.exception.GithubApiException;
import com.github.allantl.githubservice.exception.GithubServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandlersController {

    @ExceptionHandler(GithubApiException.class)
    public ResponseEntity<Map<String, Object>> serverExceptionHandler(GithubApiException ex) {
        Map<String, Object> resp = new HashMap<>();
        resp.put("message", ex.getErrorMessage());
        resp.put("statusCode", ex.getStatusCode());

        return new ResponseEntity<>(resp, HttpStatus.valueOf(ex.getStatusCode()));
    }

    @ExceptionHandler(GithubServiceException.class)
    public ResponseEntity<Map<String, Object>> serverExceptionHandler(GithubServiceException ex) {
        Map<String, Object> resp = new HashMap<>();
        resp.put("message", ex.getErrorMessage());
        resp.put("statusCode", ex.getStatusCode());

        return new ResponseEntity<>(resp, HttpStatus.valueOf(ex.getStatusCode()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> serverExceptionHandler(Exception ex) {
        Map<String, Object> resp = new HashMap<>();
        resp.put("message", ex.getMessage());
        resp.put("statusCode", 500);

        return new ResponseEntity<>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
