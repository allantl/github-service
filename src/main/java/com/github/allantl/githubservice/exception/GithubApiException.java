package com.github.allantl.githubservice.exception;

public class GithubApiException extends GithubException {

    private String errorMessage;
    private int statusCode;

    public GithubApiException(String errorMessage, int statusCode) {
        super(errorMessage);
        this.statusCode = statusCode;
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
