package ru.dazarnov.wallet.system.storage;

import org.hibernate.Session;
import ru.dazarnov.wallet.system.SystemService;

import java.io.Serializable;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public interface StorageService extends SystemService {

    void save(Object object);

    <T> Optional<T> findById(Class<T> entityType, Serializable id);

    <R> Optional<R> runInSession(Function<Session, R> function, int maxAttempts);

    void runInSession(Consumer<Session> consumer, int maxAttempts);

}
