package ru.dazarnov.wallet;

import ru.dazarnov.wallet.domain.Account;
import ru.dazarnov.wallet.domain.Operation;

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
}
