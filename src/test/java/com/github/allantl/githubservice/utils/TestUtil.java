package com.github.allantl.githubservice.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class TestUtil {

    private TestUtil() {}

    public static String readFileFromResources(String filename) throws URISyntaxException, IOException {
        URL resource = TestUtil.class.getClassLoader().getResource(filename);
        byte[] bytes = Files.readAllBytes(Paths.get(resource.toURI()));
        return new String(bytes);
    }

    public static Map<String, Object> jsonStringAsMap(String json) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = mapper.readValue(json, Map.class);
        return map;
    }
}
