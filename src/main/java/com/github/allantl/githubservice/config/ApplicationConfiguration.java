package com.github.allantl.githubservice.config;

import com.github.allantl.githubservice.client.GithubClient;
import com.github.allantl.githubservice.client.GithubClientImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Class for initializing beans when application starts
 */
@Slf4j
@Configuration
public class ApplicationConfiguration {

    @Autowired
    private GithubProperties githubProperties;

    @Bean
    public GithubClient initGithubClient() {
        log.info("Initializing Github client with base url: [{}] and github api version: [{}]",
                githubProperties.getApiUrl(), githubProperties.getApiVersion());

        WebClient webClient = WebClient.builder()
                .baseUrl(githubProperties.getApiUrl())
                .defaultHeader("Accept", "application/vnd.github+json")
                .defaultHeader("X-GitHub-Api-Version", githubProperties.getApiVersion())
                .build();

        return new GithubClientImpl(webClient);
    }
}
