package ru.dazarnov.wallet.rest.serialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import ru.dazarnov.wallet.dto.AccountTO;
import ru.dazarnov.wallet.dto.OperationTO;
import ru.dazarnov.wallet.dto.RefTO;

import java.io.IOException;

public class SerializationJsonMapper implements SerializationMapper {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public SerializationJsonMapper() {
        SimpleModule module = new SimpleModule();

        module.addSerializer(RefTO.class, new RefTOJsonSerializer())
                .addSerializer(AccountTO.class, new AccountTOJsonSerializer())
                .addSerializer(OperationTO.class, new OperationTOJsonSerializer());

        objectMapper.registerModule(module);
    }

    @Override
    public String writeValueAsString(Object value) throws IOException {
        return objectMapper.writeValueAsString(value);
    }
}
