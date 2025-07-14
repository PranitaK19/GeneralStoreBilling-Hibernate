package app.dao;

import app.entity.Payment;
import app.util.DBUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class PaymentDAO {

	// it save payment record to database using JPA transaction handling

    public void insertPayment(Payment payment) {
        EntityManager em = DBUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(payment);
            tx.commit();
            System.out.println("पेमेंट यशस्वीपणे नोंदवले.");
        } catch (Exception e) {
            tx.rollback();
            System.out.println("पेमेंट नोंदवताना त्रुटी: " + e.getMessage());
        } finally {
            em.close();
        }
    }
}
