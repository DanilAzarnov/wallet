package ru.dazarnov.wallet.converter;

import ru.dazarnov.wallet.domain.Account;
import ru.dazarnov.wallet.domain.Operation;
import ru.dazarnov.wallet.dto.AccountTO;
import ru.dazarnov.wallet.dto.OperationTO;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

public class AccountConverterImpl implements AccountConverter {

    private final OperationConverter operationConverter;

    public AccountConverterImpl(OperationConverter operationConverter) {
        this.operationConverter = operationConverter;
    }

    @Override
    public Account convert(AccountTO accountTO) {
        Account account = new Account();

        account.setId(accountTO.getId());
        account.setName(accountTO.getName());
        account.setAmount(accountTO.getAmount());

        if (accountTO.getOperations() != null) {
            Set<Operation> operations = accountTO.getOperations()
                    .stream()
                    .map(operationConverter::convert)
                    .collect(Collectors.toSet());

            account.setOperations(operations);
        }

        return account;
    }

    @Override
    public AccountTO convert(Account account) {
        long id = account.getId();
        BigDecimal amount = account.getAmount();
        String name = account.getName();
        Set<OperationTO> operations = account.getOperations()
                .stream()
                .map(operationConverter::convert)
                .collect(Collectors.toSet());

        return new AccountTO(id, name, amount, operations);
    }
}
