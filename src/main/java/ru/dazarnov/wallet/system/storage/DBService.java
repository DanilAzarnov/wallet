package ru.dazarnov.wallet.system.storage;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import ru.dazarnov.wallet.domain.Account;
import ru.dazarnov.wallet.domain.Operation;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public class DBService implements StorageService {

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
        runInSession((Consumer<Session>) session -> session.save(object));
    }

    @Override
    public <R> Optional<R> runInSession(Function<Session, R> function) {
        R result;
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            result = function.apply(session);
            transaction.commit();
        }
        return Optional.ofNullable(result);
    }

    @Override
    public void runInSession(Consumer<Session> consumer) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            consumer.accept(session);
            transaction.commit();
        }
    }

}
