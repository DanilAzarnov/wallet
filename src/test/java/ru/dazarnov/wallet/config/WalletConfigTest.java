package ru.dazarnov.wallet.config;

import org.junit.jupiter.api.Test;
import ru.dazarnov.wallet.TestClass;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WalletConfigTest extends TestClass {

    @Test
    void testFromJson0() {
        WalletConfig walletConfig = WalletConfig.fromJson(null);

        ApiConfig apiConfig = walletConfig.getApiConfig();

        assertEquals(ApiConfig.DEFAULT_IDLE_TIMEOUT_MILLIS, apiConfig.getIdleTimeoutMillis());
        assertEquals(ApiConfig.DEFAULT_IP_ADDRESS, apiConfig.getIpAddress());
        assertEquals(ApiConfig.DEFAULT_MAX_THREADS, apiConfig.getMaxThreads());
        assertEquals(ApiConfig.DEFAULT_MIN_THREADS, apiConfig.getMinThreads());
        assertEquals(ApiConfig.DEFAULT_PORT, apiConfig.getPort());
    }

    @Test
    void testFromJson1() {
        WalletConfig walletConfig = WalletConfig.fromJson("");

        ApiConfig apiConfig = walletConfig.getApiConfig();

        assertEquals(ApiConfig.DEFAULT_IDLE_TIMEOUT_MILLIS, apiConfig.getIdleTimeoutMillis());
        assertEquals(ApiConfig.DEFAULT_IP_ADDRESS, apiConfig.getIpAddress());
        assertEquals(ApiConfig.DEFAULT_MAX_THREADS, apiConfig.getMaxThreads());
        assertEquals(ApiConfig.DEFAULT_MIN_THREADS, apiConfig.getMinThreads());
        assertEquals(ApiConfig.DEFAULT_PORT, apiConfig.getPort());
    }

    @Test
    void testFromJson2() {
        WalletConfig walletConfig = WalletConfig.fromJson(getResourcePath("wallet_config.json"));

        ApiConfig apiConfig = walletConfig.getApiConfig();

        assertEquals(90, apiConfig.getIdleTimeoutMillis());
        assertEquals("127.0.0.1", apiConfig.getIpAddress());
        assertEquals(1000, apiConfig.getMaxThreads());
        assertEquals(10, apiConfig.getMinThreads());
        assertEquals(9999, apiConfig.getPort());
    }
}
