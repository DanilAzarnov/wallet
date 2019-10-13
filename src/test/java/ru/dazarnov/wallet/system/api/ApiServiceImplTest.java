package ru.dazarnov.wallet.system.api;

import org.junit.jupiter.api.Test;
import ru.dazarnov.wallet.TestClass;
import ru.dazarnov.wallet.config.ApiConfig;
import ru.dazarnov.wallet.converter.AccountConverter;
import ru.dazarnov.wallet.converter.AccountConverterImpl;
import ru.dazarnov.wallet.converter.OperationConverter;
import ru.dazarnov.wallet.converter.OperationConverterImpl;
import ru.dazarnov.wallet.dao.account.AccountDao;
import ru.dazarnov.wallet.dao.operation.OperationDao;
import ru.dazarnov.wallet.domain.Account;
import ru.dazarnov.wallet.domain.Operation;
import ru.dazarnov.wallet.rest.serialization.DeserializationJsonMapper;
import ru.dazarnov.wallet.rest.serialization.DeserializationMapper;
import ru.dazarnov.wallet.rest.serialization.SerializationJsonMapper;
import ru.dazarnov.wallet.rest.serialization.SerializationMapper;
import ru.dazarnov.wallet.service.account.AccountService;
import ru.dazarnov.wallet.service.account.AccountServiceImpl;
import ru.dazarnov.wallet.service.operation.OperationService;
import ru.dazarnov.wallet.service.operation.OperationServiceImpl;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.dazarnov.wallet.rest.util.UtilMessage.NOT_FOUND_MESSAGE;

class ApiServiceImplTest extends TestClass {

    private static AccountDao accountDao = new AccountDao() {

        private final Map<Long, Account> accounts = new HashMap<>();

        @Override
        public void save(Account account) {
            accounts.put(account.getId(), account);
        }

        @Override
        public Optional<Account> findById(long id) {
            return Optional.ofNullable(accounts.get(id));
        }
    };

    private static OperationDao operationDao = new OperationDao() {

        private final Map<Long, Operation> operations = new HashMap<>();

        @Override
        public Optional<Operation> findById(long id) {
            return Optional.ofNullable(operations.get(id));
        }

        @Override
        public void save(Operation operation) {
            operations.put(operation.getId(), operation);
        }
    };

    private HttpClient httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1).build();

    static {
        ApiConfig apiConfig = new ApiConfig();

        SerializationMapper serializationMapper = new SerializationJsonMapper();
        DeserializationMapper deserializationMapper = new DeserializationJsonMapper();

        OperationConverter operationConverter = new OperationConverterImpl();
        AccountConverter accountConverter = new AccountConverterImpl(operationConverter);

        AccountService accountService = new AccountServiceImpl(accountDao, accountConverter);

        OperationService operationService = new OperationServiceImpl(operationDao, accountService, operationConverter);

        ApiService apiService = new ApiServiceImpl(apiConfig, serializationMapper, deserializationMapper, operationService, accountService);
        apiService.init();
    }

    @Test
    void testShowAccount0() throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/account/show/1"))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(NOT_FOUND_MESSAGE, response.body());
    }

    @Test
    void testShowOperation0() throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/operation/show/1"))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(NOT_FOUND_MESSAGE, response.body());
    }

}
