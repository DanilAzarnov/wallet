package ru.dazarnov.wallet.exception;

public class TransactionRejectedException extends Exception {

    public TransactionRejectedException(String message) {
        super(message);
    }
}
