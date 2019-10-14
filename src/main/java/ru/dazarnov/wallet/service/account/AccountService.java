package ru.dazarnov.wallet.service.account;

import ru.dazarnov.wallet.dto.AccountTO;
import ru.dazarnov.wallet.exception.AccountServiceException;

import java.util.Optional;

public interface AccountService {

    void create(AccountTO accountTO) throws AccountServiceException;

    Optional<AccountTO> findById(long id) throws AccountServiceException;

    boolean exists(long id) throws AccountServiceException;

}
