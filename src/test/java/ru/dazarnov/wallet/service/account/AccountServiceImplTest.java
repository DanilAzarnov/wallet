package ru.dazarnov.wallet.service.account;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.dazarnov.wallet.TestClass;
import ru.dazarnov.wallet.converter.AccountConverterImpl;
import ru.dazarnov.wallet.converter.OperationConverter;
import ru.dazarnov.wallet.converter.OperationConverterImpl;
import ru.dazarnov.wallet.dao.account.AccountDao;
import ru.dazarnov.wallet.domain.Account;
import ru.dazarnov.wallet.dto.AccountTO;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AccountServiceImplTest extends TestClass {

    private AccountService accountService;

    private AccountDao accountDao = new AccountDao() {

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

    @BeforeEach
    void setUp() {
        OperationConverter operationConverter = new OperationConverterImpl();
        accountService = new AccountServiceImpl(accountDao, new AccountConverterImpl(operationConverter));
    }

    @Test
    void testCreate() {
        assertFalse(accountService.exists(1L));
        assertFalse(accountService.findById(1L).isPresent());

        AccountTO accountTO = new AccountTO(1L, "Oleg", new BigDecimal(100), Set.of());

        accountService.create(accountTO);

        assertTrue(accountService.exists(1L));
        Optional<AccountTO> actualAccountTO = accountService.findById(1L);
        assertTrue(actualAccountTO.isPresent());
        assertAccountTOEquals(accountTO, actualAccountTO.orElseThrow());
    }
}
