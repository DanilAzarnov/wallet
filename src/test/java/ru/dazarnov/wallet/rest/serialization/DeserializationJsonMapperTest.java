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
    private OperationTO operationTO = new OperationTO(null, BigDecimal.valueOf(100), oleg, german);
    private AccountTO accountTO = new AccountTO(null, "Oleg", BigDecimal.valueOf(100), Set.of(operationTO));

    @Test
    void testDeserializeRefTO() throws IOException {
        RefTO actual = deserializationMapper.readValue(loadFileAsString("sample_refTO.json"), RefTO.class);
        assertRefTOEquals(oleg, actual);
    }

    @Test
    void testDeserializeAccountTO() throws IOException {
        AccountTO actual = deserializationMapper.readValue(loadFileAsString("sample_accountTO.json"), AccountTO.class);
        assertAccountTOEquals(accountTO, actual);
    }

    @Test
    void testDeserializeOperationTO() throws IOException {
        OperationTO actual = deserializationMapper.readValue(loadFileAsString("sample_operationTO.json"), OperationTO.class);
        assertOperationTOEquals(operationTO, actual);
    }
}
