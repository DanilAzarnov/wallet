package ru.dazarnov.wallet.system.storage;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.dazarnov.wallet.config.StorageConfig;
import ru.dazarnov.wallet.domain.Account;
import ru.dazarnov.wallet.domain.Operation;
import ru.dazarnov.wallet.exception.TransactionRejectedException;

import java.io.Serializable;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

import static java.lang.Thread.sleep;

public class DBService implements StorageService {

    private static final Logger logger = LoggerFactory.getLogger(DBService.class);
    private final StorageConfig storageConfig;

    private SessionFactory sessionFactory;

    public DBService(StorageConfig storageConfig) {
        this.storageConfig = storageConfig;
    }

    @Override
    public void init() {
        Configuration configuration = new Configuration().configure();
        configuration.addAnnotatedClass(Account.class);
        configuration.addAnnotatedClass(Operation.class);

        storageConfig.getHibernateProperties().forEach(configuration::setProperty);

        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
        sessionFactory = configuration.buildSessionFactory(builder.build());
    }

    @Override
    public void shutdown() {
        sessionFactory.close();
    }

    @Override
    public void save(Object object) throws TransactionRejectedException {
        runInSession((Consumer<Session>) session -> session.save(object));
    }

    @Override
    public <T> Optional<T> findById(Class<T> entityType, Serializable id) throws TransactionRejectedException {
        return runInSession((Function<Session, T>) session -> session.get(entityType, id));
    }

    @Override
    public <R> Optional<R> runInSession(Function<Session, R> function, int maxAttempts) throws TransactionRejectedException {
        int attempts = 0;
        do {
            try (Session session = sessionFactory.openSession()) {
                Transaction transaction = session.beginTransaction();
                R result = function.apply(session);
                session.flush();
                transaction.commit();
                return Optional.ofNullable(result);
            } catch (Exception e) {
                logger.warn("Transaction failed, will try again. {}", e.getLocalizedMessage());
                try {
                    sleep(storageConfig.getWaitMillis());
                } catch (InterruptedException ex) {
                    logger.error(ex.getLocalizedMessage());
                    Thread.currentThread().interrupt();
                }
            }
        } while (attempts++ < maxAttempts);

        throw new TransactionRejectedException("Transaction rejected");
    }

    @Override
    public void runInSession(Consumer<Session> consumer, int maxAttempts) throws TransactionRejectedException {
        runInSession(session -> {
            consumer.accept(session);
            return Void.TYPE;
        }, maxAttempts);
    }

    @Override
    public <R> Optional<R> runInSession(Function<Session, R> function) throws TransactionRejectedException {
        return runInSession(function, 0);
    }

    @Override
    public void runInSession(Consumer<Session> consumer) throws TransactionRejectedException {
        runInSession(consumer, 0);
    }

}
