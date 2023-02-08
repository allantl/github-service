package com.github.allantl.githubservice.impl.service;

import com.github.allantl.githubservice.impl.store.RepoStoreImpl;
import com.github.allantl.githubservice.mock.MockGithubClient;
import com.github.allantl.githubservice.model.Repo;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GithubApiServiceImplTest {

    @Test
    public void testFindRepo() {
        AtomicInteger hitCounter = new AtomicInteger();

        GithubApiServiceImpl githubApiService = new GithubApiServiceImpl(
                new RepoStoreImpl(), new MockGithubClient(hitCounter));

        Repo repo = githubApiService.findRepo("allan.leong", "github-service").block();

        // Should fetch from client
        assertEquals(1, hitCounter.get());

        assertEquals("allantl/jira4s", repo.getFullName());
        assertEquals("Jira client for Scala", repo.getDescription());
        assertEquals("https://github.com/allantl/jira4s.git", repo.getCloneUrl());
        assertEquals(5, repo.getStars());
        assertEquals("2018-10-14T13:49:16Z", repo.getCreatedAt());

        Repo repo2 = githubApiService.findRepo("allan.leong", "github-service").block();

        // Hit counter shouldn't increase since data is fetched from cache
        assertEquals(1, hitCounter.get());

        assertEquals("allantl/jira4s", repo2.getFullName());
        assertEquals("Jira client for Scala", repo2.getDescription());
        assertEquals("https://github.com/allantl/jira4s.git", repo2.getCloneUrl());
        assertEquals(5, repo2.getStars());
        assertEquals("2018-10-14T13:49:16Z", repo2.getCreatedAt());

        githubApiService.findRepo("allan.leong2", "github-service").block();

        // Fetching from different owner should increase the hit counter
        assertEquals(2, hitCounter.get());
    }
}
