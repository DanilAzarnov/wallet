package ru.dazarnov.wallet.dto;

import java.math.BigDecimal;
import java.util.Set;

public class AccountTO extends RefTO {

    private final BigDecimal amount;
    private final Set<OperationTO> operations;

    public AccountTO(Long id, String name, BigDecimal amount, Set<OperationTO> operations) {
        super(id, name);
        this.amount = amount;
        this.operations = operations;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Set<OperationTO> getOperations() {
        return operations;
    }

    @Override
    public String toString() {
        return "AccountTO{" +
                "amount=" + amount +
                ", operations=" + operations +
                '}';
    }

}
