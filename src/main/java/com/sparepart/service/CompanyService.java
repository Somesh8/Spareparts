package com.sparepart.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.sparepart.exception.CanNotUpdateBrandNameException;
import com.sparepart.model.Company;

public interface CompanyService {

	public List<Company> getAllCompanies();
	public Page<Company> getAllCompaniesWithPagination();
	public Company saveCompany(Company company);
	public Company updateCompany(Company company, int id) throws CanNotUpdateBrandNameException;
	public void deleteCompany(int id);
	public Company getCompanyById(int id);
	public ResponseEntity<Map<String, Object>> getAllCompaniesWithPagination1(Pageable paging);
}
