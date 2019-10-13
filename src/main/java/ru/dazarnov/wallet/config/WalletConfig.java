package ru.dazarnov.wallet.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class WalletConfig {

    @JsonProperty("apiConfig")
    private ApiConfig apiConfig = new ApiConfig();

    public ApiConfig getApiConfig() {
        return apiConfig;
    }

    public static WalletConfig fromJson(String path) {
        if (path == null) return new WalletConfig();
        ObjectMapper objectMapper = new ObjectMapper();
        try (InputStream inputStream = Files.newInputStream(Path.of(path))) {
            return objectMapper.readValue(inputStream, WalletConfig.class);
        } catch (Exception e) {
            return new WalletConfig();
        }
    }
}
