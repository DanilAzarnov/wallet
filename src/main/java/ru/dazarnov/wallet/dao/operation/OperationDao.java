package ru.dazarnov.wallet.dao.operation;

import ru.dazarnov.wallet.domain.Operation;
import ru.dazarnov.wallet.exception.OperationDaoException;

import java.util.Optional;

public interface OperationDao {

    Optional<Operation> findById(long id) throws OperationDaoException;

    void save(Operation operation) throws OperationDaoException;

}
