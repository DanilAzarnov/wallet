package ru.dazarnov.wallet.rest.serialization;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import ru.dazarnov.wallet.TestClass;
import ru.dazarnov.wallet.dto.AccountTO;
import ru.dazarnov.wallet.dto.OperationTO;
import ru.dazarnov.wallet.dto.RefTO;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Set;

import static org.skyscreamer.jsonassert.JSONCompareMode.NON_EXTENSIBLE;

class SerializationJsonMapperTest extends TestClass {

    private SerializationMapper serializationMapper = new SerializationJsonMapper();

    private RefTO oleg = new RefTO(1L, "Oleg");
    private RefTO german = new RefTO(2L, "German");
    private OperationTO operationTO = new OperationTO(1L, BigDecimal.valueOf(100), oleg, german);
    private AccountTO accountTO = new AccountTO(1L, "Oleg", BigDecimal.valueOf(100), Set.of(operationTO));

    @Test
    void testSerializeRefTO() throws IOException, JSONException {
        JSONAssert.assertEquals(loadFileAsString("sample_refTO.json"), serializationMapper.writeValueAsString(oleg), NON_EXTENSIBLE);
    }

    @Test
    void testSerializeAccountTO() throws IOException, JSONException {
        JSONAssert.assertEquals(loadFileAsString("sample_accountTO.json"), serializationMapper.writeValueAsString(accountTO), NON_EXTENSIBLE);
    }

    @Test
    void testSerializeOperationTO() throws IOException, JSONException {
        JSONAssert.assertEquals(loadFileAsString("sample_operationTO.json"), serializationMapper.writeValueAsString(operationTO), NON_EXTENSIBLE);
    }
}
