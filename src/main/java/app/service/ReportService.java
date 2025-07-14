package app.service;

import app.entity.Customer;
import app.util.DBUtil;

import jakarta.persistence.EntityManager;
import java.util.List;

public class ReportService {

    public void showPendingDues() {
        EntityManager em = DBUtil.getEntityManager();
        try {
            List<Customer> customers = em.createQuery("FROM Customer", Customer.class).getResultList();
         // COALESCE is used here because it returns 0 if the SUm result is null (i.e... no matching records)

            for (Customer c : customers) {
                double totalCredit = em.createQuery("SELECT COALESCE(SUM(c.amount), 0) FROM Credit c WHERE c.customer.id = :cid", Double.class)
                        .setParameter("cid", c.getId())
                        .getSingleResult();

                double totalPayment = em.createQuery("SELECT COALESCE(SUM(p.amount), 0) FROM Payment p WHERE p.customer.id = :cid", Double.class)
                        .setParameter("cid", c.getId())
                        .getSingleResult();

                double pending = totalCredit - totalPayment;

                System.out.println("ग्राहक: " + c.getName() + " | मोबाईल: " + c.getMobile() + " | शिल्लक रक्कम: ₹" + String.format("%.2f", pending));

            }
        } finally {
            em.close();
        }
    }

   
    public void showPendingDueByCustomerName(String name) {
        EntityManager em = DBUtil.getEntityManager();
        try {
            List<Customer> list = em.createQuery("FROM Customer c WHERE c.name = :name", Customer.class)
                    .setParameter("name", name)
                    .getResultList();

            if (list.isEmpty()) {
                System.out.println("ग्राहक सापडला नाही.");
                return;
            }

            Customer c = list.get(0); // assume 1 match
            double totalCredit = em.createQuery("SELECT COALESCE(SUM(c.amount), 0) FROM Credit c WHERE c.customer.id = :cid", Double.class)
                    .setParameter("cid", c.getId())
                    .getSingleResult();

            double totalPayment = em.createQuery("SELECT COALESCE(SUM(p.amount), 0) FROM Payment p WHERE p.customer.id = :cid", Double.class)
                    .setParameter("cid", c.getId())
                    .getSingleResult();

            double pending = totalCredit - totalPayment;

            System.out.printf("🧾 ग्राहक: %s | मोबाईल: %s | प्रलंबित रक्कम: ₹%.2f\n",
                    c.getName(), c.getMobile(), pending);
        } finally {
            em.close();
        }
    }
}
