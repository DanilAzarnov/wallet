package ru.dazarnov.wallet.converter;

import ru.dazarnov.wallet.domain.Account;
import ru.dazarnov.wallet.dto.AccountTO;

public interface AccountConverter {

    Account convert(AccountTO accountTO);

    AccountTO convert(Account account);
}
