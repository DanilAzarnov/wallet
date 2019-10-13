package ru.dazarnov.wallet.dao.account;

import org.hibernate.Hibernate;
import ru.dazarnov.wallet.domain.Account;
import ru.dazarnov.wallet.system.storage.StorageService;

import java.util.Optional;

public class AccountDaoImpl implements AccountDao {

    private final StorageService storageService;

    public AccountDaoImpl(StorageService storageService) {
        this.storageService = storageService;
    }

    @Override
    public void save(Account account) {
        storageService.save(account);
    }

    @Override
    public Optional<Account> findById(long id) {
        return storageService.runInSession(session -> {
            Account account = session.get(Account.class, id);
            Hibernate.initialize(account.getOperations());

            return account;
        }, 1);
    }

}
