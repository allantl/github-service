package com.github.allantl.githubservice.exception;

/**
 * Parent class which extends all custom exception
 */
public abstract class GithubException extends Exception {

    public GithubException(String errorMessage) {
        super(errorMessage);
    }

    public GithubException(String errorMessage, Throwable throwable) {
        super(errorMessage, throwable);
    }
}
