package ru.dazarnov.wallet.rest.serialization;

import java.io.IOException;

public interface DeserializationMapper {

    <T> T readValue(String value, Class<T> clazz) throws IOException;

}
