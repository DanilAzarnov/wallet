package ru.dazarnov.wallet.service.account;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.dazarnov.wallet.converter.AccountConverter;
import ru.dazarnov.wallet.dao.account.AccountDao;
import ru.dazarnov.wallet.dto.AccountTO;
import ru.dazarnov.wallet.exception.AccountDaoException;
import ru.dazarnov.wallet.exception.AccountServiceException;

import java.util.Optional;

public class AccountServiceImpl implements AccountService {

    private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    private final AccountDao accountDao;
    private final AccountConverter accountConverter;

    public AccountServiceImpl(AccountDao accountDao,
                              AccountConverter accountConverter) {
        this.accountDao = accountDao;
        this.accountConverter = accountConverter;
    }

    @Override
    public void create(AccountTO accountTO) throws AccountServiceException {
        try {
            accountDao.save(accountConverter.convert(accountTO));
        } catch (AccountDaoException e) {
            logger.warn(e.getLocalizedMessage());
            throw new AccountServiceException();
        }
    }

    @Override
    public Optional<AccountTO> findById(long id) throws AccountServiceException {
        try {
            return accountDao.findById(id).map(accountConverter::convert);
        } catch (AccountDaoException e) {
            logger.warn(e.getLocalizedMessage());
            throw new AccountServiceException();
        }
    }

    @Override
    public boolean exists(long id) throws AccountServiceException {
        try {
            return accountDao.findById(id).isPresent();
        } catch (AccountDaoException e) {
            logger.warn(e.getLocalizedMessage());
            throw new AccountServiceException();
        }
    }
}
