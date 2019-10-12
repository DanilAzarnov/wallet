package ru.dazarnov.wallet.dao.operation;

import ru.dazarnov.wallet.domain.Operation;

import java.util.Optional;

public interface OperationDao {

    Optional<Operation> findById(long id);

    void save(Operation operation);

}
