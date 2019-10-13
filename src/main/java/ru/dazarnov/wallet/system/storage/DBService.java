package ru.dazarnov.wallet.system.storage;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.dazarnov.wallet.domain.Account;
import ru.dazarnov.wallet.domain.Operation;

import java.io.Serializable;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

import static java.lang.Thread.sleep;

public class DBService implements StorageService {

    private static final Logger logger = LoggerFactory.getLogger(DBService.class);

    private SessionFactory sessionFactory;

    @Override
    public void init() {
        Configuration configuration = new Configuration().configure();
        configuration.addAnnotatedClass(Account.class);
        configuration.addAnnotatedClass(Operation.class);

        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
        sessionFactory = configuration.buildSessionFactory(builder.build());
    }

    @Override
    public void shutdown() {
        sessionFactory.close();
    }

    @Override
    public void save(Object object) {
        runInSession((Consumer<Session>) session -> session.save(object), 1);
    }

    @Override
    public <T> Optional<T> findById(Class<T> entityType, Serializable id) {
        return runInSession((Function<Session, T>) session -> session.get(entityType, id), 1);
    }

    @Override
    public <R> Optional<R> runInSession(Function<Session, R> function, int maxAttempts) {
        R result = null;
        while (maxAttempts++ < 3) {
            try (Session session = sessionFactory.openSession()) {
                Transaction transaction = session.beginTransaction();
                result = function.apply(session);
                session.flush();
                transaction.commit();
                break;
            } catch (Exception e) {
                logger.warn("transaction failed, will try again");
                try {
                    sleep(10);
                } catch (InterruptedException ex) {
                    logger.error(ex.getLocalizedMessage());
                    Thread.currentThread().interrupt();
                }
            }
        }
        logger.warn("transaction rejected");
        return Optional.ofNullable(result);
    }

    @Override
    public void runInSession(Consumer<Session> consumer, int maxAttempts) {
        while (maxAttempts++ < 3) {
            try (Session session = sessionFactory.openSession()) {
                Transaction transaction = session.beginTransaction();
                consumer.accept(session);
                session.flush();
                transaction.commit();
                return;
            } catch (Exception e) {
                logger.warn("transaction failed, will try again");
                try {
                    sleep(10);
                } catch (InterruptedException ex) {
                    logger.error(ex.getLocalizedMessage());
                    Thread.currentThread().interrupt();
                }
            }
        }
        logger.warn("transaction rejected");
    }

}
