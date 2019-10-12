package ru.dazarnov.wallet.rest.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import ru.dazarnov.wallet.dto.OperationTO;
import ru.dazarnov.wallet.dto.RefTO;

import java.io.IOException;
import java.math.BigDecimal;

public class OperationTOJsonDeserializer extends JsonDeserializer<OperationTO> {

    @Override
    public OperationTO deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        ObjectCodec codec = jsonParser.getCodec();
        JsonNode jsonNode = codec.readTree(jsonParser);

        BigDecimal amount = jsonNode.get("amount").traverse(codec).readValueAs(BigDecimal.class);
        RefTO fromAccount = jsonNode.get("fromAccount").traverse(codec).readValueAs(RefTO.class);
        RefTO toAccount = jsonNode.get("toAccount").traverse(codec).readValueAs(RefTO.class);

        return new OperationTO(null, amount, fromAccount, toAccount);
    }
}
