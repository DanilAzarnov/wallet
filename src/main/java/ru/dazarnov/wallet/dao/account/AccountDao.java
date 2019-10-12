package ru.dazarnov.wallet.dao.account;

import ru.dazarnov.wallet.domain.Account;

import java.util.Optional;

public interface AccountDao {

    void save(Account account);

    Optional<Account> findById(long id);

}
