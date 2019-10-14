package ru.dazarnov.wallet.dao.operation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.dazarnov.wallet.domain.Account;
import ru.dazarnov.wallet.domain.Operation;
import ru.dazarnov.wallet.exception.OperationDaoException;
import ru.dazarnov.wallet.exception.TransactionRejectedException;
import ru.dazarnov.wallet.system.storage.StorageService;

import java.math.BigDecimal;
import java.util.Optional;

public class OperationDaoImpl implements OperationDao {

    private static final Logger logger = LoggerFactory.getLogger(OperationDaoImpl.class);

    private final StorageService storageService;

    public OperationDaoImpl(StorageService storageService) {
        this.storageService = storageService;
    }

    @Override
    public Optional<Operation> findById(long id) throws OperationDaoException {
        try {
            return storageService.findById(Operation.class, id);
        } catch (TransactionRejectedException e) {
            logger.warn(e.getLocalizedMessage());
            throw new OperationDaoException();
        }
    }

    @Override
    public void save(Operation operation) throws OperationDaoException {
        try {
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
            }, 3);
        } catch (TransactionRejectedException e) {
            logger.warn(e.getLocalizedMessage());
            throw new OperationDaoException();
        }
    }
}
