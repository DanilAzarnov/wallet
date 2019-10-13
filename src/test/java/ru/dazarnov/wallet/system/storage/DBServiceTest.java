package ru.dazarnov.wallet.system.storage;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.dazarnov.wallet.TestClass;
import ru.dazarnov.wallet.domain.Account;
import ru.dazarnov.wallet.domain.Operation;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DBServiceTest extends TestClass {

    private DBService dbService;

    @BeforeEach
    void setUp() {
        dbService = new DBService();
        dbService.init();
    }

    @AfterEach
    void tearDown() {
        dbService.shutdown();
    }

    @Test
    void testSaveAccount() {
        Account account = new Account("Oleg", BigDecimal.ONE, Set.of());
        account.setId(1L);

        dbService.save(account);

        Optional<Account> actualAccount = dbService
                .runInSession(session -> {
                    Account loadedAccount = session.get(Account.class, 1L);
                    Hibernate.initialize(loadedAccount.getOperations());
                    return loadedAccount;
                }, 1);

        assertTrue(actualAccount.isPresent());
        assertAccountEquals(account, actualAccount.orElseThrow());
    }

    @Test
    void testSaveOperation() {
        Account oleg = new Account("Oleg", new BigDecimal(100), Set.of());

        Account german = new Account("German", new BigDecimal(100), Set.of());

        Operation operation = new Operation();
        operation.setId(1L);
        operation.setAmount(new BigDecimal(100L));
        operation.setFromAccount(oleg);
        operation.setToAccount(german);

        dbService.runInSession(session -> {
            session.save(oleg);
            session.save(german);
            session.save(operation);
        }, 1);

        Optional<Operation> actualOperation = dbService
                .runInSession((Function<Session, Operation>) session -> session.get(Operation.class, 1L), 1);

        assertTrue(actualOperation.isPresent());
        assertOperationEquals(operation, actualOperation.orElseThrow());

        List<Account> accounts = dbService.runInSession(session -> {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Account> cq = cb.createQuery(Account.class);
            Root<Account> rootEntry = cq.from(Account.class);
            CriteriaQuery<Account> all = cq.select(rootEntry);

            TypedQuery<Account> allQuery = session.createQuery(all);
            return allQuery.getResultList();
        }, 1).orElse(List.of());

        assertEquals(2, accounts.size());
    }
}
