package ru.dazarnov.wallet.dto;

import java.math.BigDecimal;
import java.util.Set;

public class AccountTO {

    private final Long id;
    private final String name;
    private final BigDecimal amount;
    private final Set<OperationTO> operations;

    public AccountTO(Long id, String name, BigDecimal amount, Set<OperationTO> operations) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.operations = operations;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
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
