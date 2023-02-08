package com.github.allantl.githubservice.impl.service;

import com.github.allantl.githubservice.api.service.GithubApiService;
import com.github.allantl.githubservice.api.store.RepoStore;
import com.github.allantl.githubservice.client.GithubClient;
import com.github.allantl.githubservice.model.Repo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Map;

@Slf4j
@Component
public class GithubApiServiceImpl implements GithubApiService {

    private RepoStore repoStore;
    private GithubClient githubClient;

    @Autowired
    public GithubApiServiceImpl(RepoStore repoStore, GithubClient githubClient) {
        this.repoStore = repoStore;
        this.githubClient = githubClient;
    }

    private Repo constructRepoFromJson(Map<String, Object> json) {
        return new Repo(
            (String) json.get("full_name"),
            (String) json.get("description"),
            (String) json.get("clone_url"),
            (int) json.get("stargazers_count"),
            (String) json.get("created_at")
        );
    }

    private Mono<Map<String, Object>> fetchAndCache(String owner, String name) {
        return githubClient
            .findRepo(owner, name)
            .doOnNext(c -> repoStore.saveRepoDetails(owner, name, c).subscribe()); // fire and forget
    }

    @Override
    public Mono<Repo> findRepo(String owner, String name) {
        return repoStore
            .getRepoDetails(owner, name)
            .flatMap(r -> {
                if (r.isPresent()) {
                    log.info("Fetching from cache: {}:{}", owner, name);
                    // return from cache
                    return Mono.just(r.get());
                } else {
                    log.info("Getting from API: {}:{}", owner, name);
                    return fetchAndCache(owner, name);
                }
            })
            .map(this::constructRepoFromJson);
    }
}
