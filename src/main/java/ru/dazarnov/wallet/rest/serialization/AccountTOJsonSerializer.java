package ru.dazarnov.wallet.rest.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import ru.dazarnov.wallet.dto.AccountTO;

import java.io.IOException;

public class AccountTOJsonSerializer extends JsonSerializer<AccountTO> {

    @Override
    public void serialize(AccountTO accountTO, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();

        jsonGenerator.writeNumberField("id", accountTO.getId());
        jsonGenerator.writeStringField("name", accountTO.getName());
        jsonGenerator.writeObjectField("amount", accountTO.getAmount());
        jsonGenerator.writeObjectField("operations", accountTO.getOperations());

        jsonGenerator.writeEndObject();
    }
}
