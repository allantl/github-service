package com.github.allantl.githubservice.controller;

import com.github.allantl.githubservice.exception.GithubException;
import com.github.allantl.githubservice.exception.GithubServiceException;
import com.github.allantl.githubservice.model.Repo;
import com.github.allantl.githubservice.api.service.GithubApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/repositories")
public class ApiController {

    private GithubApiService githubApiService;

    @Autowired
    public ApiController(GithubApiService githubApiService) {
        this.githubApiService = githubApiService;
    }

    private Throwable handleError(Throwable ex, String message) {
        if (ex instanceof GithubException) {
            return ex;
        } else {
            return new GithubServiceException(message, 500, ex);
        }
    }

    @GetMapping("/{owner}/{repository-name}")
    public Mono<ResponseEntity<Repo>> fetchGithubRepo(@PathVariable("owner") String owner,
                                                      @PathVariable("repository-name") String repoName) {
        return githubApiService
                .findRepo(owner, repoName)
                .onErrorMap(throwable -> {
                    log.error("Unable to get github repo details", throwable);
                    return handleError(throwable, String.format("Unable to get github repo details for owner %s and repository name %s", owner, repoName));
                })
                .map(ResponseEntity::ok);
    }
}
