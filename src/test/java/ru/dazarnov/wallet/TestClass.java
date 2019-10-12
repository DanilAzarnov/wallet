package ru.dazarnov.wallet;

import ru.dazarnov.wallet.domain.Account;
import ru.dazarnov.wallet.domain.Operation;
import ru.dazarnov.wallet.dto.AccountTO;
import ru.dazarnov.wallet.dto.OperationTO;
import ru.dazarnov.wallet.dto.RefTO;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestClass {

    private static final double DELTA = 1e-6;

    protected void assertAccountEquals(Account expected, Account actual) {
        assertAccountWithoutOperationsEquals(expected, actual);
        assertEquals(expected.getOperations().size(), actual.getOperations().size());
    }

    protected void assertOperationEquals(Operation expected, Operation actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getAmount().doubleValue(), actual.getAmount().doubleValue(), DELTA);
        assertAccountWithoutOperationsEquals(expected.getFromAccount(), actual.getFromAccount());
        assertAccountWithoutOperationsEquals(expected.getToAccount(), actual.getToAccount());
    }

    private void assertAccountWithoutOperationsEquals(Account expected, Account actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getAmount().doubleValue(), actual.getAmount().doubleValue(), DELTA);
    }

    private void assertRefTOEquals(RefTO expected, RefTO actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
    }

    protected void assertOperationTOEquals(OperationTO expected, OperationTO actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getAmount(), actual.getAmount());
        assertRefTOEquals(expected.getFromAccount(), actual.getFromAccount());
        assertRefTOEquals(expected.getToAccount(), actual.getToAccount());
    }

    protected void assertAccountTOEquals(AccountTO expected, AccountTO actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getAmount().doubleValue(), actual.getAmount().doubleValue(), DELTA);
        assertEquals(expected.getOperations().size(), actual.getOperations().size());
    }
}
