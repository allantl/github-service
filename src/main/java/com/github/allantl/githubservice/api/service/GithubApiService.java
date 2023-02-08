package com.github.allantl.githubservice.api.service;

import com.github.allantl.githubservice.model.Repo;
import reactor.core.publisher.Mono;

public interface GithubApiService {

    public Mono<Repo> findRepo(String owner, String name);
}
