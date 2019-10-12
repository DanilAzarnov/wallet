package ru.dazarnov.wallet.converter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.dazarnov.wallet.TestClass;
import ru.dazarnov.wallet.domain.Account;
import ru.dazarnov.wallet.dto.AccountTO;

import java.math.BigDecimal;
import java.util.Set;

class AccountConverterImplTest extends TestClass {

    private AccountConverter accountConverter;

    private Account account;
    private AccountTO accountTO;

    @BeforeEach
    void setUp() {
        OperationConverter operationConverter = new OperationConverterImpl();
        accountConverter = new AccountConverterImpl(operationConverter);

        account = new Account("Oleg", new BigDecimal(100), Set.of());
        account.setId(1L);

        accountTO = new AccountTO(1L, "Oleg", new BigDecimal(100), Set.of());
    }

    @Test
    void testConvert0() {
        AccountTO actualAccountTO = accountConverter.convert(account);
        assertAccountTOEquals(accountTO, actualAccountTO);
    }

    @Test
    void testConvert1() {
        Account actualAccount = accountConverter.convert(accountTO);
        assertAccountEquals(account, actualAccount);
    }
}
