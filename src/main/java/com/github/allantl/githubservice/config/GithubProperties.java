package com.github.allantl.githubservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

/**
 * GitHub API Configuration to be used when calling GitHub REST API
 */
@Data
@Validated
@Configuration
@ConfigurationProperties(prefix = "github")
public class GithubProperties {

    @NotBlank
    private String apiUrl;

    @NotBlank
    private String apiVersion;
}
