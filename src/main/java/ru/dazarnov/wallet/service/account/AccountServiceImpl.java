package ru.dazarnov.wallet.service.account;

import ru.dazarnov.wallet.converter.AccountConverter;
import ru.dazarnov.wallet.dao.account.AccountDao;
import ru.dazarnov.wallet.dto.AccountTO;

import java.util.Optional;

public class AccountServiceImpl implements AccountService {

    private final AccountDao accountDao;
    private final AccountConverter accountConverter;

    public AccountServiceImpl(AccountDao accountDao,
                              AccountConverter accountConverter) {
        this.accountDao = accountDao;
        this.accountConverter = accountConverter;
    }

    @Override
    public void create(AccountTO accountTO) {
        accountDao.save(accountConverter.convert(accountTO));
    }

    @Override
    public Optional<AccountTO> findById(long id) {
        return accountDao.findById(id).map(accountConverter::convert);
    }

    @Override
    public boolean exists(long id) {
        return accountDao.findById(id).isPresent();
    }
}
