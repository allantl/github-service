package com.github.allantl.githubservice.impl.store;

import com.github.allantl.githubservice.api.store.RepoStore;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * In memory storage implementation
 */
@Repository
public class RepoStoreImpl implements RepoStore {

    /**
     * Since this is an in memory cache, maximum size needs to specified.
     * Acts as LRU Cache if buffer is full.
     */
    Cache<String, Map<String, Object>> cache = Caffeine.newBuilder()
        .expireAfterAccess(60, TimeUnit.MINUTES)
        .maximumSize(32768)
        .build();

    private String constructKey(String owner, String name) {
        return String.format("repo:%s:%s", owner, name);
    }

    @Override
    public Mono<Optional<Map<String, Object>>> getRepoDetails(String owner, String name) {
        return Mono.fromCallable(() -> {
            Map<String, Object> obj = cache.getIfPresent(constructKey(owner, name));
            if (obj == null) return Optional.empty();
            return Optional.of(obj);
        });
    }

    @Override
    public Mono<Void> saveRepoDetails(String owner, String name, Map<String, Object> content) {
        return Mono.fromCallable(() -> {
            String key = constructKey(owner, name);
            cache.put(key, content);
            return null;
        });
    }
}
