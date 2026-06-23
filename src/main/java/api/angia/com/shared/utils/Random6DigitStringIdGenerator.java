package api.angia.com.shared.utils;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.util.Random;

public class Random6DigitStringIdGenerator implements IdentifierGenerator {

    private static final int MIN_ID = 100000;
    private static final int MAX_ID = 999999;
    private final Random random = new Random();

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) {
        String id = generateRandomId();
        int attempts = 0;

        while (idExists(session, object.getClass(), id) && attempts < 10) {
            id = generateRandomId();
            attempts++;
        }

        if (attempts >= 10) {
            throw new RuntimeException("Could not generate a unique 6-digit ID after 10 attempts.");
        }

        return id;
    }

    private String generateRandomId() {
        int randomId = random.nextInt((MAX_ID - MIN_ID) + 1) + MIN_ID;
        return String.valueOf(randomId);
    }

    private boolean idExists(SharedSessionContractImplementor session, Class<?> entityClass, String id) {
        String entityName = entityClass.getSimpleName();
        String query = String.format("SELECT COUNT(e.id) FROM %s e WHERE e.id = :id", entityName);
        Long count = session.createQuery(query, Long.class)
                .setParameter("id", id)
                .uniqueResult();
        return count != null && count > 0;
    }
}
