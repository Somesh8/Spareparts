package com.sparepart;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparepart.model.Company;
import com.sparepart.service.CompanyService;

@SpringBootTest
@AutoConfigureMockMvc
class CompanyControllerTests {

    @Autowired
	private MockMvc mockMvc;
    
	@MockBean
	private CompanyService cmpService;
       
	@Test
	@DisplayName("Company Controller :: GET `/company` ")
	public void getAllCompany() throws Exception {
		Company firstCompany = new Company(1, "Comp_name", "Comp_desc");
		
		when(cmpService.getAllCompanies()).thenReturn(Stream
				.of(firstCompany, new Company(2, "Comp_name2", "Comp_desc2"))
				.toList());
		
		mockMvc.perform(get("/company").accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$[0].company_id", is(1)))
			.andExpect(jsonPath("$[0].company_name", is("Comp_name")))
			.andExpect(jsonPath("$[0].company_desc", is("Comp_desc")))
			.andExpect(jsonPath("$.length()", is(2)));
	}
	
	@DisplayName("Company controller Layer ::GET `/company/1`")
	@Test
    public void getAllCompanysTest() throws Exception {
        Company company1 = new Company(1, "HP Inc", "Best Computer & Laptops");
        
		when(cmpService.getCompanyById(1)).thenReturn(company1);

        mockMvc.perform(get("/company/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.company_id", is(1)))
                .andExpect(jsonPath("$.company_name", is("HP Inc")))
                .andExpect(jsonPath("$.company_desc", is("Best Computer & Laptops")));

	}
	
	@DisplayName("Company controller Layer :: DELETE `/company/1`")
	@Test
	public void testCompanyDelete() throws Exception {
	    mockMvc.perform(delete("/company/{id}",11).accept(MediaType.APPLICATION_JSON)
	            .contentType(MediaType.APPLICATION_JSON))
	            .andExpect(status().isOk());
	}
	
	@DisplayName("Company controller Layer :: POST `/company`")
	@Test
    public void testSaveCompany() throws Exception {
        Company company1 = new Company(1, "HPInc", "BestComputer&Laptops");
        
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(company1);
        
        when(cmpService.saveCompany(company1)).thenReturn(company1); 
        
        mockMvc.perform(post("/company")
        		.contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .characterEncoding("utf-8"))
                .andExpect(status().is2xxSuccessful());
    }
	
	@DisplayName("Company controller Layer :: UPDATE `/company/1")
	@Test
    public void testUpdateCompany() throws Exception {
        Company company1 = new Company(1, "HPInc", "BestComputer&Laptops");
        
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(company1);
        
        when(cmpService.updateCompany(company1, 1)).thenReturn(company1); 
        
        mockMvc.perform(put("/company/{id}",11)
        		.contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .characterEncoding("utf-8"))
                .andExpect(status().is2xxSuccessful());
    }

}
