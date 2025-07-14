package app.dao;

import app.entity.Credit;
import app.util.DBUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

// this method saves a credit entry to  database using JPA  management
public class CreditDAO {

    public void insertCredit(Credit credit) {
        EntityManager em = DBUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(credit);
            tx.commit();
            System.out.println(" उधारी यशस्वीपणे नोंदवली.");
        } catch (Exception e) {
            tx.rollback();
            System.out.println(" उधारी नोंदवताना त्रुटी: " + e.getMessage());
        } finally {
            em.close();
        }
    }
}
