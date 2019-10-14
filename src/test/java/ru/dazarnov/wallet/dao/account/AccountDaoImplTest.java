package ru.dazarnov.wallet.dao.account;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.dazarnov.wallet.TestClass;
import ru.dazarnov.wallet.domain.Account;
import ru.dazarnov.wallet.exception.AccountDaoException;
import ru.dazarnov.wallet.system.storage.DBService;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

class AccountDaoImplTest extends TestClass {

    private AccountDaoImpl accountDao;

    @BeforeEach
    void setUp() {
        DBService storageService = new DBService();
        storageService.init();
        accountDao = new AccountDaoImpl(storageService);
    }

    @Test
    void testSave() throws AccountDaoException {
        Account expectedAccount = new Account("Oleg", new BigDecimal(100), Set.of());
        expectedAccount.setId(1L);

        accountDao.save(expectedAccount);

        Optional<Account> byId = accountDao.findById(1L);

        assertTrue(byId.isPresent());
        assertAccountEquals(expectedAccount, byId.orElseThrow());
    }

}
