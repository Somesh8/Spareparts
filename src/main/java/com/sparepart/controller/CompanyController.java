package com.sparepart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sparepart.exception.WrongInputException;
import com.sparepart.model.Company;
import com.sparepart.service.CompanyService;

@RestController
public class CompanyController {
	
	@Autowired
	CompanyService service;
	
	@GetMapping(value="/companies")
	public List<Company> getAllCompanies() {
		return service.getAllCompanies();
	}
	
	@GetMapping(value="/company/{id}")
	public Company getCompanyById(@PathVariable("id") int id) {
		return service.getCompanyById(id);
	}
	
	@PostMapping(value="/company")
	public Company saveCompany(@RequestBody Company Company) throws WrongInputException {
		return service.saveCompany(Company);
	}
	

	@PutMapping(value="/company/{id}")
	public Company updateCompany(@PathVariable("id") int id,@RequestBody Company Company) throws WrongInputException {
		return service.updateCompany(Company, id);
	}
	
	@DeleteMapping(value="/company/{id}")
	public String deleteCompany(@PathVariable("id") int id) {
		service.deleteCompany( id);
		return "Deleted Successfully";
	}
}
