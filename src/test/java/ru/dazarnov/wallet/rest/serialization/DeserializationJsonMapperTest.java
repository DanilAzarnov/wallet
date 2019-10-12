package ru.dazarnov.wallet.rest.serialization;

import org.junit.jupiter.api.Test;
import ru.dazarnov.wallet.TestClass;
import ru.dazarnov.wallet.dto.AccountTO;
import ru.dazarnov.wallet.dto.OperationTO;
import ru.dazarnov.wallet.dto.RefTO;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Set;

class DeserializationJsonMapperTest extends TestClass {

    private DeserializationMapper deserializationMapper = new DeserializationJsonMapper();

    private RefTO oleg = new RefTO(1L, "Oleg");
    private RefTO german = new RefTO(2L, "German");
    private OperationTO operationTO = new OperationTO(1L, BigDecimal.valueOf(100), oleg, german);
    private AccountTO accountTO = new AccountTO(1L, "Oleg", BigDecimal.valueOf(100), Set.of(operationTO));

    @Test
    void testDeserializeRefTO() throws IOException {
        RefTO actual = deserializationMapper.readValue(loadFileAsString("sample_refTO.json"), RefTO.class);
        assertRefTOEquals(oleg, actual);
    }
}
