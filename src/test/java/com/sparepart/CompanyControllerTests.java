package com.sparepart;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.sparepart.controller.CompanyController;
import com.sparepart.exception.WrongInputException;
import com.sparepart.model.Company;
import com.sparepart.repository.CompanyRepo;

@SpringBootTest
class CompanyControllerTests {

	@Autowired
	private CompanyController controller;

	@MockBean
	private CompanyRepo repository;

	@DisplayName("Company controller Layer :: getAllCompanies")
	@Test
	void testGetAllCompanies() {
		Company firstCompany = new Company(1, "Comp_name", "Comp_desc");
		when(repository.findAll()).thenReturn(Stream
				.of(firstCompany, new Company(2, "Comp_name2", "Comp_desc2"))
				.toList());
		List<Company> result = controller.getAllCompanies();
		assertEquals(2, result.size());
		assertEquals(firstCompany, result.get(0));
	}
	
	@DisplayName("Company controller Layer :: saveCompany")
	@Test
	void testSaveCompany() throws WrongInputException {
		Company newCompany = new Company(1, "Comp name", "Comp_desc");
		when(repository.save(newCompany)).thenReturn(newCompany);
		assertEquals(newCompany, controller.saveCompany(newCompany));
	}
	
	@DisplayName("Company controller Layer :: updateCompany")
	@Test
	void testUpdateCompany() throws WrongInputException {
		int companyId = 1;
		Company newCompany = new Company(companyId, "Comp name", "Comp_desc");
		Optional<Company> optionalCompany = Optional.of(newCompany);
		when(repository.findById(companyId)).thenReturn(optionalCompany);
		when(repository.save(newCompany)).thenReturn(newCompany);
		assertEquals(newCompany, controller.updateCompany(companyId, newCompany));
	}
	
	@DisplayName("Company controller Layer :: deleteCompany")
	@Test
	void testDeleteCompany() {
		Company newCompany = new Company(1, "Comp_name", "Comp_desc");
		Optional<Company> optionalCompany = Optional.of(newCompany);
	    when(repository.findById(1)).thenReturn(optionalCompany);

		controller.deleteCompany(1);
		verify(repository, times(1)).delete(newCompany);
	}

}
