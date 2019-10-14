package ru.dazarnov.wallet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.dazarnov.wallet.config.ApiConfig;
import ru.dazarnov.wallet.config.WalletConfig;
import ru.dazarnov.wallet.converter.AccountConverter;
import ru.dazarnov.wallet.converter.AccountConverterImpl;
import ru.dazarnov.wallet.converter.OperationConverter;
import ru.dazarnov.wallet.converter.OperationConverterImpl;
import ru.dazarnov.wallet.dao.account.AccountDao;
import ru.dazarnov.wallet.dao.account.AccountDaoImpl;
import ru.dazarnov.wallet.dao.operation.OperationDao;
import ru.dazarnov.wallet.dao.operation.OperationDaoImpl;
import ru.dazarnov.wallet.rest.serialization.DeserializationJsonMapper;
import ru.dazarnov.wallet.rest.serialization.DeserializationMapper;
import ru.dazarnov.wallet.rest.serialization.SerializationJsonMapper;
import ru.dazarnov.wallet.rest.serialization.SerializationMapper;
import ru.dazarnov.wallet.service.account.AccountService;
import ru.dazarnov.wallet.service.account.AccountServiceImpl;
import ru.dazarnov.wallet.service.operation.OperationService;
import ru.dazarnov.wallet.service.operation.OperationServiceImpl;
import ru.dazarnov.wallet.system.api.ApiService;
import ru.dazarnov.wallet.system.api.ApiServiceImpl;
import ru.dazarnov.wallet.system.storage.DBService;
import ru.dazarnov.wallet.system.storage.StorageService;

public class Wallet {

    private static final Logger logger = LoggerFactory.getLogger(Wallet.class);

    private StorageService storageService;
    private ApiService apiService;

    public static void main(String[] args) {
        WalletConfig config = WalletConfig.fromJson(System.getProperty("config"));
        try {
            new Wallet().run(config);
        } catch (Exception e) {
            logger.error("Wallet failed! {}", e.getLocalizedMessage());
        }
    }

    private void run(WalletConfig config) {
        logger.info("Running wallet");

        storageService = new DBService(config.getStorageConfig());
        storageService.init();

        OperationConverter operationConverter = new OperationConverterImpl();
        OperationDao operationDao = new OperationDaoImpl(storageService);
        AccountDao accountDao = new AccountDaoImpl(storageService);

        AccountConverter accountConverter = new AccountConverterImpl(operationConverter);
        AccountService accountService = new AccountServiceImpl(accountDao, accountConverter);
        OperationService operationService = new OperationServiceImpl(operationDao, accountService, operationConverter);

        ApiConfig apiConfig = config.getApiConfig();
        DeserializationMapper deserializationMapper = new DeserializationJsonMapper();
        SerializationMapper serializationMapper = new SerializationJsonMapper();

        apiService = new ApiServiceImpl(apiConfig, serializationMapper, deserializationMapper, operationService, accountService);
        apiService.init();

        Runtime.getRuntime().addShutdownHook(new Thread(this::shutdown));

        logger.info("Wallet started with config {}", config);
    }

    private void shutdown() {
        logger.info("Shutting down wallet");

        storageService.shutdown();
        apiService.shutdown();

        logger.info("Shutdown completed");
    }

}
