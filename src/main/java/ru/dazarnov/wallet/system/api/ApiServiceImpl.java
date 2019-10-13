package ru.dazarnov.wallet.system.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.dazarnov.wallet.config.ApiConfig;
import ru.dazarnov.wallet.rest.controller.AccountController;
import ru.dazarnov.wallet.rest.controller.Controller;
import ru.dazarnov.wallet.rest.controller.OperationController;
import ru.dazarnov.wallet.rest.serialization.DeserializationMapper;
import ru.dazarnov.wallet.rest.serialization.SerializationMapper;
import ru.dazarnov.wallet.service.account.AccountService;
import ru.dazarnov.wallet.service.operation.OperationService;

import java.util.ArrayList;
import java.util.List;

import static spark.Spark.*;

public class ApiServiceImpl implements ApiService {

    private static final Logger logger = LoggerFactory.getLogger(ApiServiceImpl.class);

    private final List<Controller> controllers = new ArrayList<>();

    private ApiConfig apiConfig;

    public ApiServiceImpl(ApiConfig apiConfig,
                          SerializationMapper serializationMapper,
                          DeserializationMapper deserializationMapper,
                          OperationService operationService,
                          AccountService accountService) {
        this.apiConfig = apiConfig;
        controllers.add(new OperationController(serializationMapper, deserializationMapper, operationService));
        controllers.add(new AccountController(serializationMapper, deserializationMapper, accountService));
    }

    @Override
    public void init() {
        ipAddress(apiConfig.getIpAddress());
        port(apiConfig.getPort());
        threadPool(apiConfig.getMaxThreads(), apiConfig.getMinThreads(), apiConfig.getIdleTimeoutMillis());

        path("/api", () -> {
            before("/*", (request, response) -> logger.info("Received api call"));
            controllers.forEach(controller -> path(controller.getRootPath(), controller.getRouteGroup()));
        });
        awaitInitialization();
    }

    @Override
    public void shutdown() {
        awaitStop();
    }

}
