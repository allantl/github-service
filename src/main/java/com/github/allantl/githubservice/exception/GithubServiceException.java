package com.github.allantl.githubservice.exception;

public class GithubServiceException extends GithubException {

    private String errorMessage;
    private int statusCode;

    public GithubServiceException(String errorMessage, int statusCode, Throwable throwable) {
        super(errorMessage, throwable);
        this.errorMessage = errorMessage;
        this.statusCode = statusCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
