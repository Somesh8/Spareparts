package com.sparepart;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.sparepart.exception.CanNotUpdateBrandNameException;
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

	private Company firstCompany = new Company(1, "Comp_name", "Comp_desc");
	private Company secondCompany = new Company(2, "Comp_name2", "Comp_desc2");

	private List<Company> companies = new ArrayList<>(Arrays.asList(firstCompany, secondCompany));

	@DisplayName("Company Service Layer :: getAllCompanies")
	@Test
	void testGetAllCompanies() {
		when(repository.findAll()).thenReturn(companies);
		assertEquals(2, service.getAllCompanies().size());
	}

	@DisplayName("Company Service Layer :: getCompanyById")
	@Test
	void testGetCompanyById() {
		int companyId = 1;
		Optional<Company> optionalCompany = Optional.of(firstCompany);
		when(repository.findById(companyId)).thenReturn(optionalCompany);

		assertEquals(firstCompany, service.getCompanyById(companyId));
	}

	@DisplayName("Company Service Layer :: saveCompany")
	@Test
	void testSaveCompany() throws WrongInputException {
		when(repository.save(firstCompany)).thenReturn(firstCompany);
		assertEquals(firstCompany, service.saveCompany(firstCompany));
	}

	@DisplayName("Company Service Layer :: updateCompany")
	@Test
	void testUpdateCompany() throws WrongInputException, CanNotUpdateBrandNameException {
		int companyId = 1;
		Optional<Company> optionalCompany = Optional.of(firstCompany);
		when(repository.findById(companyId)).thenReturn(optionalCompany);
		when(repository.save(firstCompany)).thenReturn(firstCompany);
		assertEquals(firstCompany, service.updateCompany(firstCompany, companyId));
	}

	@DisplayName("Company Service Layer :: deleteCompany")
	@Test
	void testDeleteCompany() {
		Optional<Company> optionalCompany = Optional.of(firstCompany);
		when(repository.findById(1)).thenReturn(optionalCompany);

		service.deleteCompany(1);
		verify(repository, times(1)).delete(firstCompany);
	}

}
