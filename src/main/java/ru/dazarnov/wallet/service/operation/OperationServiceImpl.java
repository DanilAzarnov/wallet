package ru.dazarnov.wallet.service.operation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.dazarnov.wallet.converter.OperationConverter;
import ru.dazarnov.wallet.dao.operation.OperationDao;
import ru.dazarnov.wallet.dto.OperationTO;
import ru.dazarnov.wallet.exception.AccountServiceException;
import ru.dazarnov.wallet.exception.OperationDaoException;
import ru.dazarnov.wallet.exception.OperationServiceException;
import ru.dazarnov.wallet.exception.UnknownAccountException;
import ru.dazarnov.wallet.service.account.AccountService;

import java.util.Optional;

public class OperationServiceImpl implements OperationService {

    private static final Logger logger = LoggerFactory.getLogger(OperationServiceImpl.class);

    private final OperationDao operationDao;
    private final AccountService accountService;
    private final OperationConverter operationConverter;

    public OperationServiceImpl(OperationDao operationDao,
                                AccountService accountService,
                                OperationConverter operationConverter) {
        this.operationDao = operationDao;
        this.accountService = accountService;
        this.operationConverter = operationConverter;
    }

    @Override
    public void create(OperationTO operationTO) throws UnknownAccountException, OperationServiceException {
        try {
            if (!accountService.exists(operationTO.getFromAccount().getId())) {
                throw new UnknownAccountException("Unknown account");
            }
            if (!accountService.exists(operationTO.getToAccount().getId())) {
                throw new UnknownAccountException("Unknown account");
            }
            operationDao.save(operationConverter.convert(operationTO));
        } catch (AccountServiceException | OperationDaoException e) {
            logger.warn(e.getLocalizedMessage());
            throw new OperationServiceException();
        }
    }

    @Override
    public Optional<OperationTO> findById(long id) throws OperationServiceException {
        try {
            return operationDao.findById(id).map(operationConverter::convert);
        } catch (OperationDaoException e) {
            logger.warn(e.getLocalizedMessage());
            throw new OperationServiceException();
        }
    }
}
