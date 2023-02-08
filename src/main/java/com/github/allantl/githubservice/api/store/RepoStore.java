package com.github.allantl.githubservice.api.store;

import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Optional;

public interface RepoStore {

    public Mono<Optional<Map<String, Object>>> getRepoDetails(String owner, String name);

    public Mono<Void> saveRepoDetails(String owner, String name, Map<String, Object> content);
}
