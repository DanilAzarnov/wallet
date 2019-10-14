package ru.dazarnov.wallet.dao.account;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.dazarnov.wallet.domain.Account;
import ru.dazarnov.wallet.exception.AccountDaoException;
import ru.dazarnov.wallet.exception.TransactionRejectedException;
import ru.dazarnov.wallet.system.storage.StorageService;

import java.util.Optional;

public class AccountDaoImpl implements AccountDao {

    private static final Logger logger = LoggerFactory.getLogger(AccountDaoImpl.class);

    private final StorageService storageService;

    public AccountDaoImpl(StorageService storageService) {
        this.storageService = storageService;
    }

    @Override
    public void save(Account account) throws AccountDaoException {
        try {
            storageService.save(account);
        } catch (TransactionRejectedException e) {
            logger.warn(e.getLocalizedMessage());
            throw new AccountDaoException();
        }
    }

    @Override
    public Optional<Account> findById(long id) throws AccountDaoException {
        try {
            return storageService.runInSession(session -> {
                Account account = session.get(Account.class, id);
                Hibernate.initialize(account.getOperations());

                return account;
            });
        } catch (TransactionRejectedException e) {
            logger.warn(e.getLocalizedMessage());
            throw new AccountDaoException();
        }
    }

}
