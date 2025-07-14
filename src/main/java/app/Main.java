package app;

import app.dao.*;
import app.entity.*;
import app.service.ReportService;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        CustomerDAO customerDAO = new CustomerDAO();
        CreditDAO creditDAO = new CreditDAO();
        PaymentDAO paymentDAO = new PaymentDAO();
        ReportService reportService = new ReportService();

        while (true) {
            System.out.println("\n=====> जनरल स्टोअर बिलिंग सिस्टम <=====");
            System.out.println("1. नवीन ग्राहक नोंदवा");
            System.out.println("2. उधारी नोंदवा ");
            System.out.println("3. पेमेंट नोंदवा ");
            System.out.println("4. सर्व ग्राहक पहा");
            System.out.println("5. उधारी असलेल्या ग्राहकांची यादी");
            System.out.println("6. एका ग्राहकाचा बिल हिशोब");
            System.out.println("0. बाहेर पडा");
            System.out.print("आपला पर्याय निवडा: ");
            int ch = sc.nextInt();
            sc.nextLine(); 

            switch (ch) {
                case 1: {
                    System.out.print("ग्राहकाचे नाव: ");
                    String name = sc.nextLine();
                    System.out.print("मोबाईल क्रमांक: ");
                    String mobile = sc.nextLine();

                    Customer c = new Customer();
                    c.setName(name);
                    c.setMobile(mobile);
                    Customer saved = customerDAO.insertCustomer(c);
                    if (saved != null) {
                        System.out.println("ग्राहक ID: " + saved.getId());
                    }
                    break;
                }

                case 2: {
                    System.out.print("ग्राहकाचे नाव: ");
                    String cname = sc.nextLine();
                    Customer customer = customerDAO.getCustomerByName(cname);
                    if (customer != null) {
                        System.out.print("उधारी रक्कम: ");
                        double amt = sc.nextDouble();
                        Credit credit = new Credit();
                        credit.setAmount(amt);
                        credit.setDate(LocalDate.now());
                        credit.setCustomer(customer);
                        creditDAO.insertCredit(credit);
                    } else {
                        System.out.println(" ग्राहक सापडला नाही.");
                    }
                    break;
                }

                case 3: {
                    System.out.print("ग्राहकाचे नाव: ");
                    String cname = sc.nextLine();
                    Customer customer = customerDAO.getCustomerByName(cname);
                    if (customer != null) {
                        System.out.print("पेमेंट रक्कम: ");
                        double amt = sc.nextDouble();
                        Payment payment = new Payment();
                        payment.setAmount(amt);
                        payment.setDate(LocalDate.now());
                        payment.setCustomer(customer);
                        paymentDAO.insertPayment(payment);
                    } else {
                        System.out.println(" ग्राहक सापडला नाही.");
                    }
                    break;
                }

                case 4: {
                    List<Customer> list = customerDAO.getAllCustomers();
                    for (Customer c : list) {
                        System.out.println("ID: " + c.getId() + ", नाव: " + c.getName() + ", मोबाईल: " + c.getMobile());
                    }
                    break;
                }

                case 5: {
                    reportService.showPendingDues();
                    break;
                }
                case 6: {
                    System.out.print("ग्राहकाचे नाव: ");
                    String cname = sc.nextLine();
                    reportService.showPendingDueByCustomerName(cname);
                    break;
                }


                case 0: {
                    System.out.println("धन्यवाद! बाहेर पडलं जात आहे...");
                    System.exit(0);
                }

                default:
                    System.out.println("चुकीचा पर्याय!");
            }
        }
    }
}
