package ru.dazarnov.wallet.service.operation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.dazarnov.wallet.TestClass;
import ru.dazarnov.wallet.converter.OperationConverter;
import ru.dazarnov.wallet.converter.OperationConverterImpl;
import ru.dazarnov.wallet.dao.operation.OperationDao;
import ru.dazarnov.wallet.domain.Operation;
import ru.dazarnov.wallet.dto.AccountTO;
import ru.dazarnov.wallet.dto.OperationTO;
import ru.dazarnov.wallet.dto.RefTO;
import ru.dazarnov.wallet.exception.AccountServiceException;
import ru.dazarnov.wallet.exception.OperationServiceException;
import ru.dazarnov.wallet.exception.UnknownAccountException;
import ru.dazarnov.wallet.service.account.AccountService;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class OperationServiceImplTest extends TestClass {

    private OperationService operationService;

    private AccountService accountService = new AccountService() {

        private final Map<Long, AccountTO> accounts = new HashMap<>();

        @Override
        public void create(AccountTO accountTO) {
            accounts.put(accountTO.getId(), accountTO);
        }

        @Override
        public Optional<AccountTO> findById(long id) {
            return Optional.ofNullable(accounts.get(id));
        }

        @Override
        public boolean exists(long id) {
            return accounts.containsKey(id);
        }
    };

    private OperationDao operationDao = new OperationDao() {

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

    @BeforeEach
    void setUp() {
        OperationConverter operationConverter = new OperationConverterImpl();
        operationService = new OperationServiceImpl(operationDao, accountService, operationConverter);
    }

    @Test
    void testCreate0() throws UnknownAccountException, OperationServiceException, AccountServiceException {
        assertFalse(operationService.findById(1L).isPresent());

        OperationTO operationTO = new OperationTO(1L, new BigDecimal(100), new RefTO(1L, "Oleg"), new RefTO(2L, "German"));

        assertThrows(UnknownAccountException.class, () -> operationService.create(operationTO));

        AccountTO oleg = new AccountTO(1L, "Oleg", new BigDecimal(100), Set.of());

        accountService.create(oleg);

        assertThrows(UnknownAccountException.class, () -> operationService.create(operationTO));

        AccountTO german = new AccountTO(2L, "German", new BigDecimal(100), Set.of());

        accountService.create(german);

        operationService.create(operationTO);

        Optional<OperationTO> actualOperationTO = operationService.findById(1L);

        assertTrue(actualOperationTO.isPresent());
        assertOperationTOEquals(operationTO, actualOperationTO.orElseThrow());
    }
}
