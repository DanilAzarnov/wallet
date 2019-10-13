package ru.dazarnov.wallet.config;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ApiConfig {

    @JsonProperty("maxThreads")
    private int maxThreads = DEFAULT_MAX_THREADS;

    @JsonProperty("minThreads")
    private int minThreads = DEFAULT_MIN_THREADS;

    @JsonProperty("idleTimeoutMillis")
    private int idleTimeoutMillis = DEFAULT_IDLE_TIMEOUT_MILLIS;

    @JsonProperty("ipAddress")
    private String ipAddress = DEFAULT_IP_ADDRESS;

    @JsonProperty("port")
    private int port = DEFAULT_PORT;

    static final int DEFAULT_PORT = 8080;
    static final int DEFAULT_MAX_THREADS = -1;
    static final int DEFAULT_MIN_THREADS = -1;
    static final int DEFAULT_IDLE_TIMEOUT_MILLIS = -1;
    static final String DEFAULT_IP_ADDRESS = "0.0.0.0";

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
