package ru.dazarnov.wallet.system.storage;

import org.hibernate.Session;
import ru.dazarnov.wallet.system.SystemService;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public interface StorageService extends SystemService {

    void save(Object object);

    <R> Optional<R> runInSession(Function<Session, R> function);

    void runInSession(Consumer<Session> consumer);

}
