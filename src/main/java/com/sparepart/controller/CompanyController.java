package com.sparepart.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sparepart.exception.CanNotUpdateBrandNameException;
import com.sparepart.model.Company;
import com.sparepart.service.CompanyService;


@RestController
@RequestMapping("/company")
public class CompanyController {
	
	@Autowired
	CompanyService service;
	
	@GetMapping
	public List<Company> getAllCompanies() {
		return service.getAllCompanies();
	}
	
	@GetMapping(value="/pagination")
	public  ResponseEntity<Map<String, Object>> getAllCompaniesWithPagination(@RequestParam(defaultValue = "1", required = false, value="page") int page,
		      @RequestParam(defaultValue = "3", required = false, value="size") int size) {
		Pageable paging = PageRequest.of(--page, size);
		return service.getAllCompaniesWithPagination1(paging);
	}
	
	@GetMapping(value="/{id}")
	public Company getCompanyById(@PathVariable("id") int id) {
		return service.getCompanyById(id);
	}
	
	@GetMapping(value="/testme")
	public int testme() {
		int a ,b;
		a=5;
		b=9;
		
		return a+b;
	}
	
	@PostMapping
	public Company saveCompany(@RequestBody Company Company) {
		return service.saveCompany(Company);
	}
	
	@PutMapping(value="/{id}")
	public Company updateCompany(@PathVariable("id") int id,@RequestBody Company Company) throws CanNotUpdateBrandNameException {
		return service.updateCompany(Company, id);
	}
	
	@DeleteMapping(value="/{id}")
	public String deleteCompany(@PathVariable("id") int id) {
		service.deleteCompany( id);
		return "Deleted Successfully";
	}
}
