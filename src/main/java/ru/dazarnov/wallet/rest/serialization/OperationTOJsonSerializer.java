package ru.dazarnov.wallet.rest.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import ru.dazarnov.wallet.dto.OperationTO;

import java.io.IOException;

public class OperationTOJsonSerializer extends JsonSerializer<OperationTO> {

    @Override
    public void serialize(OperationTO operationTO, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();

        jsonGenerator.writeNumberField("id", operationTO.getId());
        jsonGenerator.writeObjectField("amount", operationTO.getAmount());
        jsonGenerator.writeObjectField("fromAccount", operationTO.getFromAccount());
        jsonGenerator.writeObjectField("toAccount", operationTO.getToAccount());

        jsonGenerator.writeEndObject();
    }
}
