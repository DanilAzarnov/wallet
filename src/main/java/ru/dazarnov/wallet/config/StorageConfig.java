package ru.dazarnov.wallet.config;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class StorageConfig {

    @JsonProperty("waitMillis")
    private long waitMillis = DEFAULT_WAIT_MILLIS;

    @JsonProperty("hibernateProperties")
    private Map<String, String> hibernateProperties = DEFAULT_HIBERNATE_PROPERTIES;

    static final long DEFAULT_WAIT_MILLIS = 10;

    static final Map<String, String> DEFAULT_HIBERNATE_PROPERTIES = Map.of(
            "hibernate.connection.driver_class", "org.postgresql.Driver",
            "hibernate.connection.url", "jdbc:postgresql://localhost:5432/example",
            "hibernate.connection.username", "example",
            "hibernate.connection.password", "example",
            "hibernate.connection.autocommit", "false",
            "hibernate.connection.isolation", "4",

            "hibernate.dialect", "org.hibernate.dialect.PostgreSQL82Dialect",

            "show_sql", "true",

            "hibernate.hbm2ddl.auto", "create"
    );

    public long getWaitMillis() {
        return waitMillis;
    }

    public Map<String, String> getHibernateProperties() {
        return hibernateProperties;
    }

    @Override
    public String toString() {
        return "StorageConfig{" +
                "waitMillis=" + waitMillis +
                ", hibernateProperties=" + hibernateProperties +
                '}';
    }
}
