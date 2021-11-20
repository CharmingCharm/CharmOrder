package com.charmbit.CharmOrder.dao;

import com.charmbit.CharmOrder.entity.Authorities;
import com.charmbit.CharmOrder.entity.Customer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerDao {

    @Autowired
    @Qualifier("sessionFactory")
    private SessionFactory sessionFactory;

    public void signUp(Customer customer) {
        Authorities authorities = new Authorities();
        authorities.setEmail(customer.getEmail());
        authorities.setAuthorities("ROLE_USER");

        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.save(authorities);
            session.save(customer);
            session.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            if(session != null) {
                session.getTransaction().rollback();
            }
        } finally {
            if(session != null) {
                session.close();
            }
        }
    }

    public Customer getCustomer(String email) {
        Customer customer = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            customer = session.get(Customer.class, email);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if(session != null) {
                session.close();
            }
        }
        return customer;
    }
}
