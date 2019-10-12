package ru.dazarnov.wallet.service.account;

import ru.dazarnov.wallet.dto.AccountTO;

import java.util.Optional;

public interface AccountService {

    void create(AccountTO accountTO);

    Optional<AccountTO> findById(long id);

    boolean exists(long id);

}
