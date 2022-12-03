package com.sparepart.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.sparepart.exception.CanNotUpdateBrandNameException;
import com.sparepart.model.Company;
import com.sparepart.repository.CompanyRepo;

@Service
@Transactional
public class CompanyServiceImpl implements CompanyService {

	@Autowired
	CompanyRepo repository;

	Pageable pageable = PageRequest.of(0, 3);

	public static Map<String, Object> getResponse(List<Company> companyList, int pageNumber, long totalElements,
			int totalPages) {
		Map<String, Object> response = new HashMap<>();
		response.put("data", companyList);
		response.put("currentPage", ++pageNumber);
		response.put("totalItems", totalElements);
		response.put("totalPages", totalPages);
		return response;
	}

	@Override
	public ResponseEntity<Map<String, Object>> getAllCompaniesWithPagination1(Pageable paging) {
		List<Company> companyList = new ArrayList<Company>();
		try {
			Page<Company> page = repository.findAll(paging);
			if (page.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			companyList = page.getContent();
			Map<String, Object> response = new HashMap<>();
			response = getResponse(companyList, page.getNumber(), page.getTotalElements(), page.getTotalPages());
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public Page<Company> getAllCompaniesWithPagination() {
		// TODO Auto-generated method stub
		Page<Company> page = repository.findAll(pageable);
		return page;
	}

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

		if (Objects.nonNull(company.getCompanyName()) && !"".equalsIgnoreCase(company.getCompanyName())) {
			if (!company.getCompanyName().contains(companyDB.getCompanyName())) {
				throw new CanNotUpdateBrandNameException("Can not update brand name");
			}
			companyDB.setCompanyName(company.getCompanyName());
		}

		if (Objects.nonNull(company.getCompanyDesc()) && !"".equalsIgnoreCase(company.getCompanyDesc())) {
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
