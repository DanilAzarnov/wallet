package ru.dazarnov.wallet.rest.serialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import ru.dazarnov.wallet.dto.AccountTO;
import ru.dazarnov.wallet.dto.OperationTO;
import ru.dazarnov.wallet.dto.RefTO;

import java.io.IOException;

public class DeserializationJsonMapper implements DeserializationMapper {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public DeserializationJsonMapper() {
        SimpleModule module = new SimpleModule();

        module.addDeserializer(RefTO.class, new RefTOJsonDeserializer())
                .addDeserializer(AccountTO.class, new AccountTOJsonDeserializer())
                .addDeserializer(OperationTO.class, new OperationTOJsonDeserializer());

        objectMapper.registerModule(module);
    }

    @Override
    public <T> T readValue(String value, Class<T> valueType) throws IOException {
        return objectMapper.readValue(value, valueType);
    }
}
