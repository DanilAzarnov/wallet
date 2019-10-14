package ru.dazarnov.wallet.config;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StorageConfig {

    @JsonProperty("waitMillis")
    private long waitMillis = DEFAULT_WAIT_MILLIS;

    @JsonProperty("hibernateConfigPath")
    private String hibernateConfigPath = DEFAULT_HIBERNATE_CONFIG_PATH;

    static final long DEFAULT_WAIT_MILLIS = 10;
    static final String DEFAULT_HIBERNATE_CONFIG_PATH = StorageConfig.class.getResource("/hibernate.cfg.xml").getPath();;

    public long getWaitMillis() {
        return waitMillis;
    }

    public String getHibernateConfigPath() {
        return hibernateConfigPath;
    }

    @Override
    public String toString() {
        return "StorageConfig{" +
                "waitMillis=" + waitMillis +
                ", hibernateConfigPath='" + hibernateConfigPath + '\'' +
                '}';
    }
}
