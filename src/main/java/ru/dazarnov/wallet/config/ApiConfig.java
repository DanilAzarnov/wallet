package ru.dazarnov.wallet.config;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ApiConfig {

    @JsonProperty("maxThreads")
    private int maxThreads = -1;

    @JsonProperty("minThreads")
    private int minThreads = -1;

    @JsonProperty("idleTimeoutMillis")
    private int idleTimeoutMillis = -1;

    @JsonProperty("ipAddress")
    private String ipAddress = "0.0.0.0";

    @JsonProperty("port")
    private int port = 8080;

    public String getIpAddress() {
        return ipAddress;
    }

    public int getPort() {
        return port;
    }

    public int getMaxThreads() {
        return maxThreads;
    }

    public int getMinThreads() {
        return minThreads;
    }

    public int getIdleTimeoutMillis() {
        return idleTimeoutMillis;
    }
}
