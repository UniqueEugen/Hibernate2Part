package org.example.service.serviceImpl;

import org.example.dao.CompanyDao;
import org.example.dao.daoImpl.CompanyDaoImpl;
import org.example.entity.Company;
import org.example.exception.ShowException;
import org.example.service.CompanyService;
import org.hibernate.HibernateError;

import java.util.List;

public class CompanyServiceImpl implements CompanyService {

    CompanyDao companyDao = new CompanyDaoImpl();

    public CompanyServiceImpl() {}

    @Override
    public boolean addCompany(Company company) {
        boolean isAdded = false;
        try {
            companyDao.addCompany(company);
            isAdded = true;
        }
        catch (HibernateError e) {
            ShowException.showNotice(e);
        }
        return isAdded;
    }

    @Override
    public boolean updateCompany(Company company) {
        boolean isUpdated = false;
        try {
            companyDao.updateCompany(company);
            isUpdated = true;
        }
        catch (HibernateError e) {
            ShowException.showNotice(e);
        }
        return isUpdated;
    }

    @Override
    public boolean deleteCompany(int id) {
        boolean isDeleted=false;
        try {
            companyDao.deleteCompany(id);
            isDeleted = true;
        }
        catch (HibernateError e) {
            ShowException.showNotice(e);
        }
        return isDeleted;
    }

    @Override
    public List<Company> showCompanies() {
        try {
            return companyDao.showCompanies();
        }
        catch (HibernateError e) {
            ShowException.showNotice(e);
            return null;
        }
    }

    @Override
    public Company findCompanyById(int id) {
        try {
            return companyDao.findCompanyById(id);
        }
        catch (HibernateError e) {
            ShowException.showNotice(e);
            return null;
        }
    }

    @Override
    public Company findCompanyByName(String name) {
        try {
            return companyDao.findCompanyByName(name);
        }
        catch (HibernateError e) {
            ShowException.showNotice(e);
            return null;
        }
    }
}

