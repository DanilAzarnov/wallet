package ru.dazarnov.wallet.rest.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import ru.dazarnov.wallet.dto.AccountTO;
import ru.dazarnov.wallet.dto.OperationTO;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Set;

public class AccountTOJsonDeserializer extends JsonDeserializer<AccountTO> {

    private final TypeReference<Set<OperationTO>> operationTypeRef = new TypeReference<>() {
    };

    @Override
    public AccountTO deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        ObjectCodec codec = jsonParser.getCodec();
        JsonNode jsonNode = codec.readTree(jsonParser);

        String name = jsonNode.get("name").asText("");

        JsonNode amountNode = jsonNode.get("amount");
        BigDecimal amount = amountNode == null ? BigDecimal.ZERO : amountNode.traverse(codec).readValueAs(BigDecimal.class);

        JsonNode operationsNode = jsonNode.get("operations");
        Set<OperationTO> operations = operationsNode == null ? Set.of() : operationsNode.traverse(codec).readValueAs(operationTypeRef);

        return new AccountTO(null, name, amount, operations);
    }
}
