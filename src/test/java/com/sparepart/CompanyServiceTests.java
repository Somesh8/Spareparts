package com.sparepart;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.sparepart.exception.WrongInputException;
import com.sparepart.model.Company;
import com.sparepart.repository.CompanyRepo;
import com.sparepart.service.CompanyService;

@SpringBootTest
class CompanyServiceTests {

	@Autowired
	private CompanyService service;

	@MockBean
	private CompanyRepo repository;

	@DisplayName("Company Service Layer :: getAllCompanies")
	@Test
	void testGetAllCompanies() {
		when(repository.findAll()).thenReturn(Stream
				.of(new Company(1, "Comp_name", "Comp_desc"), new Company(2, "Comp_name2", "Comp_desc2"))
				.toList());
		assertEquals(2, service.getAllCompanies().size());
	}
	
	@DisplayName("Company Service Layer :: getCompanyById")
	@Test
	void testGetCompanyById() {
		int companyId = 1;
		Company newCompany = new Company(1, "Comp_name", "Comp_desc");
		Optional<Company> optionalCompany = Optional.of(newCompany);
		when(repository.findById(companyId)).thenReturn(optionalCompany);
		
		assertEquals(newCompany, service.getCompanyById(companyId));
	}
	@DisplayName("Company Service Layer :: saveCompany")
	@Test
	void testSaveCompany() throws WrongInputException {
		Company newCompany = new Company(1, "Comp name", "Comp_desc");
		when(repository.save(newCompany)).thenReturn(newCompany);
		assertEquals(newCompany, service.saveCompany(newCompany));
	}
	
	@DisplayName("Company Service Layer :: updateCompany")
	@Test
	void testUpdateCompany() throws WrongInputException {
		int companyId = 1;
		Company newCompany = new Company(companyId, "Comp name", "Comp_desc");
		Optional<Company> optionalCompany = Optional.of(newCompany);
		when(repository.findById(companyId)).thenReturn(optionalCompany);
		when(repository.save(newCompany)).thenReturn(newCompany);
		assertEquals(newCompany, service.updateCompany(newCompany, companyId));
	}
	
	@DisplayName("Company Service Layer :: deleteCompany")
	@Test
	void testDeleteCompany() {
		Company newCompany = new Company(1, "Comp_name", "Comp_desc");
		Optional<Company> optionalCompany = Optional.of(newCompany);
	    when(repository.findById(1)).thenReturn(optionalCompany);

		service.deleteCompany(1);
		verify(repository, times(1)).delete(newCompany);
	}

}
