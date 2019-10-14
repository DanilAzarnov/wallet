package ru.dazarnov.wallet.system.storage;

import org.hibernate.Session;
import ru.dazarnov.wallet.exception.TransactionRejectedException;
import ru.dazarnov.wallet.system.SystemService;

import java.io.Serializable;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public interface StorageService extends SystemService {

    void save(Object object) throws TransactionRejectedException;

    <T> Optional<T> findById(Class<T> entityType, Serializable id) throws TransactionRejectedException;

    <R> Optional<R> runInSession(Function<Session, R> function, int maxAttempts) throws TransactionRejectedException;

    void runInSession(Consumer<Session> consumer, int maxAttempts) throws TransactionRejectedException;

    <R> Optional<R> runInSession(Function<Session, R> function) throws TransactionRejectedException;

    void runInSession(Consumer<Session> consumer) throws TransactionRejectedException;

}
