package com.github.allantl.githubservice.mock;

import com.github.allantl.githubservice.client.GithubClient;
import com.github.allantl.githubservice.utils.TestUtil;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class MockGithubClient implements GithubClient {

    private AtomicInteger hitCounter;

    public MockGithubClient(AtomicInteger hitCounter) {
        this.hitCounter = hitCounter;
    }

    @Override
    public Mono<Map<String, Object>> findRepo(String owner, String repoName) {
        Map<String, Object> map;
        try {
            map = TestUtil.jsonStringAsMap(TestUtil.readFileFromResources("repo.json"));
        } catch (Exception e) {
            map = new HashMap<>();
        }
        hitCounter.incrementAndGet();
        return Mono.just(map);
    }
}
