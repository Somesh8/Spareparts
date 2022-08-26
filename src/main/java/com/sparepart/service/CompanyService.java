package com.sparepart.service;

import java.util.List;

import com.sparepart.exception.WrongInputException;
import com.sparepart.model.Company;

public interface CompanyService {

	public List<Company> getAllCompanies();
	public Company saveCompany(Company company) throws WrongInputException ;
	public Company updateCompany(Company company, int id) throws WrongInputException;
	public void deleteCompany(int id);
	public Company getCompanyById(int id);
}
