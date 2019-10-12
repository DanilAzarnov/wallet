package ru.dazarnov.wallet.converter;

import ru.dazarnov.wallet.domain.Operation;
import ru.dazarnov.wallet.dto.OperationTO;

public interface OperationConverter {

    Operation convert(OperationTO operationTO);

    OperationTO convert(Operation operation);

}
