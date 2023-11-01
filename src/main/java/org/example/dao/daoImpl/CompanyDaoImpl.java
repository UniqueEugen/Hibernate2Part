package org.example.dao.daoImpl;


import myLibrary.console.Console;
import org.example.dao.CompanyDao;
import org.example.entity.Company;
import org.example.entity.Person;
import org.example.exception.ShowException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.example.sessionFactory.SessionFactoryImpl;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class CompanyDaoImpl implements CompanyDao {

    @Override
    public boolean addCompany(Company company) {
        boolean isAdded = false;
        try {
            Session session = SessionFactoryImpl.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            session.save(company);
            tx.commit();
            session.close();
            isAdded = true;
        }
        catch (NoClassDefFoundError e) {
            Console.log("Exception: " + e);
        }
        return isAdded;
    }

    @Override
    public boolean updateCompany(Company company) {
        boolean isUpdated = false;
        try {
            Session session = SessionFactoryImpl.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            session.update(company);
            tx.commit();
            session.close();
            isUpdated = true;
        }
        catch (NoClassDefFoundError e) {
            Console.log("Exception: " + e);
        }
        return isUpdated;
    }

    @Override
    public boolean deleteCompany(int id) {
        boolean isDeleted = false;
        try {
            Session session = SessionFactoryImpl.getSessionFactory().openSession();
            Company company = session.load(Company.class, id);
            Transaction tx = session.beginTransaction();
            session.delete(company);
            tx.commit();
            session.close();
            isDeleted = true;
        }
        catch (NoClassDefFoundError e) {
            Console.log("Exception: " + e);
        }
        return isDeleted;
    }

    @Override
    public Company findCompanyById(int id) {
        Company company = null;
        try(Session session = SessionFactoryImpl.getSessionFactory().openSession()) {
            /*Session session = SessionFactoryImpl.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Company> cr = cb.createQuery(Company.class);
            Root<Company> root = cr.from(Company.class);
            cr.select(root).where(cb.equal(root.get("personId"), id));
            company = session.createQuery(cr).getSingleResult();
            tx.commit();
            session.close();*/
            return session.get(Company.class, id);
        }
        catch (NoClassDefFoundError e) {
            Console.log("Exception: " + e);
            return company;
        }
    }

    @Override
    public Company findCompanyByName(String name) {
        Company company = null;
        try(Session session = SessionFactoryImpl.getSessionFactory().openSession()) {
            Query query = session.createQuery("from Company where companyName = :name");
            query.setParameter("name", name);
            company = (Company) query.getSingleResult();
            return company;
        }
        catch (NoClassDefFoundError e) {
            Console.log("Exception: " + e);
            return company;
        }
    }

    @Override
    public List<Company> showCompanies() {
        try(Session session = SessionFactoryImpl.getSessionFactory().openSession()){
            return  (List<Company>) session.createQuery("From Company").list();
        }catch (Exception e){
            ShowException.showNotice(e);
            return null;
        }
    }
}
