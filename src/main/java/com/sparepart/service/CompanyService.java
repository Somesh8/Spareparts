package com.sparepart.service;

import java.util.List;

import com.sparepart.exception.CanNotUpdateBrandNameException;
import com.sparepart.model.Company;

public interface CompanyService {

	public List<Company> getAllCompanies();
	public Company saveCompany(Company company);
	public Company updateCompany(Company company, int id) throws CanNotUpdateBrandNameException;
	public void deleteCompany(int id);
	public Company getCompanyById(int id);
}
