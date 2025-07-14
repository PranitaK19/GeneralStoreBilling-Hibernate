package app.dao;

import app.entity.Customer;
import app.util.DBUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;

public class CustomerDAO {
	// this method persists new customer record & return stored result
    public Customer insertCustomer(Customer customer) {
        EntityManager em = DBUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(customer);
            tx.commit();
            System.out.println("ग्राहक यशस्वीपणे नोंदवला गेला..");
            return customer;
        } catch (Exception e) {
            tx.rollback();
            System.out.println("ग्राहक नोंदवताना त्रुटी: " + e.getMessage());
            return null;
        } finally {
            em.close();
        }
    }

    public Customer getCustomerByName(String name) {
        EntityManager em = DBUtil.getEntityManager();
        try {
        	// this finds  customers by name..and returns null if no match is found...

            List<Customer> list = em.createQuery("FROM Customer c WHERE c.name = :name", Customer.class)
                                    .setParameter("name", name)
                                    .getResultList();
            if (list.isEmpty()) return null;
            return list.get(0);
        } finally {
            em.close();
        }
    }

 //  used to retrieve all Customer records from the database using list

    public List<Customer> getAllCustomers() {
        EntityManager em = DBUtil.getEntityManager();
        try {
            return em.createQuery("FROM Customer", Customer.class).getResultList();
        } finally {
            em.close();
        }
    }
}
