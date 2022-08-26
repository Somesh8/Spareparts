package com.sparepart.service;

import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sparepart.exception.WrongInputException;
import com.sparepart.model.Company;
import com.sparepart.repository.CompanyRepo;

@Service
@Transactional
public class CompanyServiceImpl implements CompanyService {
	
	@Autowired
	CompanyRepo repository;

	String regex = "^\\s*[A-Za-z]+(?:\\s+[A-Za-z]+)*\\s*$";
	
	@Override
	public List<Company> getAllCompanies() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}

	@Override
	public Company saveCompany(Company company) throws WrongInputException {
		if(!Pattern.compile(regex).matcher(company.getCompanyName()).matches()) {
			throw new WrongInputException("Wrong company name");
		}
		return repository.save(company);
	}

	@Override
	public Company updateCompany(Company company, int id) throws WrongInputException {
		// TODO Auto-generated method stub
		Company companyDB = repository.findById(id).get();

		if (Objects.nonNull(company.getCompanyName())
				&& !"".equalsIgnoreCase(company.getCompanyName())) {
			if(!Pattern.compile(regex).matcher(company.getCompanyName()).matches()) {
				throw new WrongInputException("Wrong company name");
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
		// TODO Auto-generated method stub
		Company companyDB = repository.findById(id).get();
		repository.delete(companyDB);
	}

	@Override
	public Company getCompanyById(int id) {
		// TODO Auto-generated method stub
		return repository.findById(id).get();
	}
}
