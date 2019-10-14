package ru.dazarnov.wallet.dao.account;

import ru.dazarnov.wallet.domain.Account;
import ru.dazarnov.wallet.exception.AccountDaoException;

import java.util.Optional;

public interface AccountDao {

    void save(Account account) throws AccountDaoException;

    Optional<Account> findById(long id) throws AccountDaoException;

}
