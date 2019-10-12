package ru.dazarnov.wallet.converter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.dazarnov.wallet.TestClass;
import ru.dazarnov.wallet.domain.Account;
import ru.dazarnov.wallet.domain.Operation;
import ru.dazarnov.wallet.dto.OperationTO;
import ru.dazarnov.wallet.dto.RefTO;

import java.math.BigDecimal;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OperationConverterImplTest extends TestClass {

    private OperationConverter operationConverter = new OperationConverterImpl();

    private Operation operation;
    private OperationTO operationTO;

    @BeforeEach
    void setUp() {
        Account oleg = new Account("Oleg", new BigDecimal(100), Set.of());
        oleg.setId(1L);

        Account german = new Account("German", new BigDecimal(100), Set.of());
        german.setId(2L);

        operation = new Operation();
        operation.setId(1L);
        operation.setAmount(new BigDecimal(100));
        operation.setFromAccount(oleg);
        operation.setToAccount(german);

        operationTO = new OperationTO(1L, new BigDecimal(100), new RefTO(1L, "Oleg"), new RefTO(2L, "German"));
    }

    @Test
    void testConvert0() {
        OperationTO actualOperationTO = operationConverter.convert(operation);
        assertOperationTOEquals(operationTO, actualOperationTO);
    }

    @Test
    void testConvert1() {
        Operation actualOperation = operationConverter.convert(operationTO);

        assertEquals(operation.getId(), actualOperation.getId());
        assertEquals(operation.getAmount(), actualOperation.getAmount());

        assertEquals(operation.getFromAccount().getId(), actualOperation.getFromAccount().getId());
        assertEquals(operation.getFromAccount().getName(), actualOperation.getFromAccount().getName());

        assertEquals(operation.getToAccount().getId(), actualOperation.getToAccount().getId());
        assertEquals(operation.getToAccount().getName(), actualOperation.getToAccount().getName());
    }
}
