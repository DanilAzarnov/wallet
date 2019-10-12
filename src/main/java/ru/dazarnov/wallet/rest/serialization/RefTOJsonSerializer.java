package ru.dazarnov.wallet.rest.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import ru.dazarnov.wallet.dto.RefTO;

import java.io.IOException;

public class RefTOJsonSerializer extends JsonSerializer<RefTO> {

    @Override
    public void serialize(RefTO refTO, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();

        jsonGenerator.writeNumberField("id", refTO.getId());
        jsonGenerator.writeStringField("name", refTO.getName());

        jsonGenerator.writeEndObject();
    }
}
