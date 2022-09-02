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
import com.sparepart.model.MachineType;
import com.sparepart.service.MachineTypeService;

@SpringBootTest
@AutoConfigureMockMvc
class MachineTypeControllerTests {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private MachineTypeService service;

	@Test
	@DisplayName("MachineType Controller :: GET `/machinetype` ")
	public void getAllMachineType() throws Exception {
		MachineType firstMachineType = new MachineType(1, "MachineType_name", "MachineType_desc");
		when(service.getAllMachineTypes()).thenReturn(Stream
				.of(firstMachineType, new MachineType(2, "MachineType_name2", "MachineType_desc2"))
				.toList());
		
		mockMvc.perform(get("/machinetype").accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$[0].machine_type_id", is(1)))
			.andExpect(jsonPath("$[0].machine_type_name", is("MachineType_name")))
			.andExpect(jsonPath("$[0].machine_type_desc", is("MachineType_desc")))
			.andExpect(jsonPath("$.length()", is(2)));
	}
	
	@DisplayName("MachineType controller Layer ::GET `/machinetype/1`")
	@Test
    public void getAllCompanysTest() throws Exception {
		MachineType firstMachineType = new MachineType(1, "MachineType_name", "MachineType_desc");
		
		when(service.getMachineType(1)).thenReturn(firstMachineType);

        mockMvc.perform(get("/machinetype/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.machine_type_id", is(1)))
    			.andExpect(jsonPath("$.machine_type_name", is("MachineType_name")))
    			.andExpect(jsonPath("$.machine_type_desc", is("MachineType_desc")));
	}
	
	@DisplayName("MachineType controller Layer :: POST `/machinetype`")
	@Test
    public void testSaveMachineType() throws Exception {
		MachineType newMachineType = new MachineType(1, "MachineType name", "MachineType_desc");

		ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(newMachineType);
        
        when(service.saveMachineType(newMachineType)).thenReturn(newMachineType); 
        
        mockMvc.perform(post("/machinetype")
        		.contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .characterEncoding("utf-8"))
                .andExpect(status().is2xxSuccessful());
    }

	@DisplayName("MachineType controller Layer :: UPDATE `/machinetype/1")
	@Test
    public void testUpdateMachineType() throws Exception {
		MachineType newMachineType = new MachineType(1, "MachineType name", "MachineType_desc");
		
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(newMachineType);
        
        when(service.updateMachineType(newMachineType, 1)).thenReturn(newMachineType); 
        
        mockMvc.perform(put("/machinetype/{id}",1)
        		.contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .characterEncoding("utf-8"))
                .andExpect(status().is2xxSuccessful());
    }

	@DisplayName("MachineType controller Layer :: DELETE `/machinetype/1`")
	@Test
	public void testMachineTypeDelete() throws Exception {
	    mockMvc.perform(delete("/machinetype/{id}",11).accept(MediaType.APPLICATION_JSON)
	            .contentType(MediaType.APPLICATION_JSON))
	            .andExpect(status().isOk());
	}

}
