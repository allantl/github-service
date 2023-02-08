package com.github.allantl.githubservice.client;

import com.github.allantl.githubservice.exception.GithubApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Slf4j
public class GithubClientImpl implements GithubClient {

    private WebClient webClient;

    public GithubClientImpl(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public Mono<Map<String, Object>> findRepo(String owner, String repoName) {
        return webClient
                .get()
                .uri(String.format("/repos/%s/%s", owner, repoName))
                .exchangeToMono(r -> {
                    if (r.statusCode().isError()) {
                        return r.bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                            .map(s -> new GithubApiException((String) s.get("message"), r.statusCode().value()))
                            .flatMap(Mono::error);
                    } else {
                        return r.bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {});
                    }
                });
    }
}
