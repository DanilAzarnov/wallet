package ru.dazarnov.wallet.rest.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import ru.dazarnov.wallet.dto.RefTO;

import java.io.IOException;

public class RefTOJsonDeserializer extends JsonDeserializer<RefTO> {

    @Override
    public RefTO deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        ObjectCodec codec = jsonParser.getCodec();
        JsonNode jsonNode = codec.readTree(jsonParser);

        Long id = jsonNode.get("id").asLong();
        String name = jsonNode.get("name").asText("");

        return new RefTO(id, name);
    }
}
