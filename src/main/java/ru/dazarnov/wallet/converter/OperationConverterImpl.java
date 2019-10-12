package ru.dazarnov.wallet.converter;

import ru.dazarnov.wallet.domain.Account;
import ru.dazarnov.wallet.domain.Operation;
import ru.dazarnov.wallet.dto.OperationTO;
import ru.dazarnov.wallet.dto.RefTO;

import java.math.BigDecimal;

public class OperationConverterImpl implements OperationConverter {

    @Override
    public Operation convert(OperationTO operationTO) {
        Operation operation = new Operation();
        operation.setId(operationTO.getId());
        operation.setAmount(operationTO.getAmount());

        RefTO fromAccount = operationTO.getFromAccount();
        operation.setFromAccount(new Account(fromAccount.getId(), fromAccount.getName()));

        RefTO toAccount = operationTO.getToAccount();
        operation.setToAccount(new Account(toAccount.getId(), toAccount.getName()));

        return operation;
    }

    @Override
    public OperationTO convert(Operation operation) {
        long id = operation.getId();
        BigDecimal amount = operation.getAmount();

        Account fromAccount = operation.getFromAccount();
        RefTO fromAccountRefTo = new RefTO(fromAccount.getId(), fromAccount.getName());

        Account toAccount = operation.getToAccount();
        RefTO toAccountRefTo = new RefTO(toAccount.getId(), toAccount.getName());

        return new OperationTO(id, amount, fromAccountRefTo, toAccountRefTo);
    }
}
