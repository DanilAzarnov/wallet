package ru.dazarnov.wallet.dto;

import java.math.BigDecimal;
import java.util.Objects;

public class OperationTO {

    private final long id;
    private final BigDecimal amount;
    private final RefTO fromAccount;
    private final RefTO toAccount;

    public OperationTO(long id, BigDecimal amount, RefTO fromAccount, RefTO toAccount) {
        this.id = id;
        this.amount = amount;
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
    }

    public long getId() {
        return id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public RefTO getFromAccount() {
        return fromAccount;
    }

    public RefTO getToAccount() {
        return toAccount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OperationTO that = (OperationTO) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "OperationTO{" +
                "id=" + id +
                ", amount=" + amount +
                ", fromAccount=" + fromAccount +
                ", toAccount=" + toAccount +
                '}';
    }
}
