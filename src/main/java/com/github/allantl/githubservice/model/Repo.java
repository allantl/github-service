package com.github.allantl.githubservice.model;

import lombok.Value;

@Value
public class Repo {

    private String fullName;
    private String description;
    private String cloneUrl;
    private int stars;
    private String createdAt;
}
