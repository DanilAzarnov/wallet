package ru.dazarnov.wallet.dao.operation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.dazarnov.wallet.TestClass;
import ru.dazarnov.wallet.dao.account.AccountDao;
import ru.dazarnov.wallet.dao.account.AccountDaoImpl;
import ru.dazarnov.wallet.domain.Account;
import ru.dazarnov.wallet.domain.Operation;
import ru.dazarnov.wallet.exception.AccountDaoException;
import ru.dazarnov.wallet.exception.OperationDaoException;
import ru.dazarnov.wallet.system.storage.DBService;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

class OperationDaoImplTest extends TestClass {

    private AccountDao accountDao;
    private OperationDao operationDao;

    @BeforeEach
    void setUp() {
        DBService storageService = new DBService();
        storageService.init();

        accountDao = new AccountDaoImpl(storageService);
        operationDao = new OperationDaoImpl(storageService);
    }

    @Test
    void testSave() throws AccountDaoException, OperationDaoException {
        Account oleg = new Account("Oleg", new BigDecimal(100), Set.of());
        oleg.setId(1L);

        accountDao.save(oleg);

        Account german = new Account("German", new BigDecimal(100), Set.of());
        german.setId(2L);

        accountDao.save(german);

        Operation expectedOperation = new Operation();
        expectedOperation.setId(1L);
        expectedOperation.setAmount(new BigDecimal(100));
        expectedOperation.setFromAccount(oleg);
        expectedOperation.setToAccount(german);

        operationDao.save(expectedOperation);

        Optional<Operation> byId = operationDao.findById(1L);

        assertTrue(byId.isPresent());

        oleg.setAmount(new BigDecimal(0));
        german.setAmount(new BigDecimal(200));

        assertOperationEquals(expectedOperation, byId.orElseThrow());
    }
}
