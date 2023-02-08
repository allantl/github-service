package com.github.allantl.githubservice.client;

import reactor.core.publisher.Mono;

import java.util.Map;

public interface GithubClient {

    public Mono<Map<String, Object>> findRepo(String owner, String repoName);
}
