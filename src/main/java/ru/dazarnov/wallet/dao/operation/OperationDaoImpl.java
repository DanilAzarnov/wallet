package ru.dazarnov.wallet.dao.operation;

import ru.dazarnov.wallet.domain.Account;
import ru.dazarnov.wallet.domain.Operation;
import ru.dazarnov.wallet.system.storage.StorageService;

import java.math.BigDecimal;
import java.util.Optional;

public class OperationDaoImpl implements OperationDao {

    private final StorageService storageService;

    public OperationDaoImpl(StorageService storageService) {
        this.storageService = storageService;
    }

    @Override
    public Optional<Operation> findById(long id) {
        return storageService.findById(Operation.class, id);
    }

    @Override
    public void save(Operation operation) {
        storageService.runInSession(session -> {
            Account fromAccount = session.get(Account.class, operation.getFromAccount().getId());
            Account toAccount = session.get(Account.class, operation.getToAccount().getId());

            BigDecimal amount = operation.getAmount();

            BigDecimal newFromAmount = fromAccount.getAmount().subtract(amount);
            fromAccount.setAmount(newFromAmount);

            BigDecimal newToAmount = toAccount.getAmount().add(amount);
            toAccount.setAmount(newToAmount);

            operation.setFromAccount(fromAccount);
            operation.setToAccount(toAccount);

            session.save(operation);
        });
    }
}
