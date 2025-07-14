package app.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
//this class gives us connection to the database using JPA

public class DBUtil {
    private static final EntityManagerFactory emf =
        Persistence.createEntityManagerFactory("gsBillingPU");

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}
