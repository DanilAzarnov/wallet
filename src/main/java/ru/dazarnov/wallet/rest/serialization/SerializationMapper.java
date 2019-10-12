package ru.dazarnov.wallet.rest.serialization;

import java.io.IOException;

public interface SerializationMapper {

    String writeValueAsString(Object value) throws IOException;

}
