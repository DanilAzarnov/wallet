package ru.dazarnov.wallet;

import ru.dazarnov.wallet.domain.Account;
import ru.dazarnov.wallet.domain.Operation;
import ru.dazarnov.wallet.dto.AccountTO;
import ru.dazarnov.wallet.dto.OperationTO;
import ru.dazarnov.wallet.dto.RefTO;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.StringJoiner;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestClass {

    private static final double DELTA = 1e-6;

    protected void assertAccountEquals(Account expected, Account actual) {
        assertAccountWithoutOperationsEquals(expected, actual);
        assertEquals(expected.getOperations().size(), actual.getOperations().size());
    }

    protected void assertOperationEquals(Operation expected, Operation actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getAmount().doubleValue(), actual.getAmount().doubleValue(), DELTA);
        assertAccountWithoutOperationsEquals(expected.getFromAccount(), actual.getFromAccount());
        assertAccountWithoutOperationsEquals(expected.getToAccount(), actual.getToAccount());
    }

    private void assertAccountWithoutOperationsEquals(Account expected, Account actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getAmount().doubleValue(), actual.getAmount().doubleValue(), DELTA);
    }

    protected void assertRefTOEquals(RefTO expected, RefTO actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
    }

    protected void assertOperationTOEquals(OperationTO expected, OperationTO actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getAmount(), actual.getAmount());
        assertRefTOEquals(expected.getFromAccount(), actual.getFromAccount());
        assertRefTOEquals(expected.getToAccount(), actual.getToAccount());
    }

    protected void assertAccountTOEquals(AccountTO expected, AccountTO actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getAmount().doubleValue(), actual.getAmount().doubleValue(), DELTA);
        assertEquals(expected.getOperations().size(), actual.getOperations().size());
    }

    protected String loadFileAsString(String fileName) throws IOException {
        Path path = Paths.get(this.getClass().getResource(File.separator + this.getClass().getName().replace(".", "/") + File.separator + fileName).getPath());

        StringJoiner joiner = new StringJoiner("");

        try (Stream<String> lines = Files.lines(path, Charset.defaultCharset())) {
            lines.forEach(joiner::add);
        }

        return joiner.toString();
    }
}
