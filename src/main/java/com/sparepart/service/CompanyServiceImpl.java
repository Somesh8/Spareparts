package com.sparepart.service;

import java.util.List;
import java.util.Objects;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sparepart.exception.CanNotUpdateBrandNameException;
import com.sparepart.model.Company;
import com.sparepart.repository.CompanyRepo;

@Service
@Transactional
public class CompanyServiceImpl implements CompanyService {
	
	@Autowired
	CompanyRepo repository;
	
	@Override
	public List<Company> getAllCompanies() {
		return repository.findAll();
	}

	@Override
	public Company saveCompany(Company company) {
		return repository.save(company);
	}

	@Override
	public Company updateCompany(Company company, int id) throws CanNotUpdateBrandNameException {
		Company companyDB = repository.findById(id).get();

		if (Objects.nonNull(company.getCompanyName())
				&& !"".equalsIgnoreCase(company.getCompanyName())) {
			if (!company.getCompanyName().contains(companyDB.getCompanyName())) {
				throw new CanNotUpdateBrandNameException("Can not update brand name");
			}
			companyDB.setCompanyName(company.getCompanyName());
		}

		if (Objects.nonNull(company.getCompanyDesc())
				&& !"".equalsIgnoreCase(company.getCompanyDesc())) {
			companyDB.setCompanyDesc(company.getCompanyDesc());
		}
		return repository.save(companyDB);
	}

	@Override
	public void deleteCompany(int id) {
		Company companyDB = repository.findById(id).get();
		repository.delete(companyDB);
	}

	@Override
	public Company getCompanyById(int id) {
		return repository.findById(id).get();
	}
}
