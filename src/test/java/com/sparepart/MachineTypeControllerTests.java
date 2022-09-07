package com.sparepart;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

	private MachineType machineType1 = new MachineType(1, "MachineType_name", "MachineType_desc");
	private MachineType machineType2 = new MachineType(2, "MachineType_name2", "MachineType_desc2");
	private List<MachineType> machineTypes = new ArrayList<>(Arrays.asList(machineType1, machineType2));

	@Test
	@DisplayName("MachineType Controller :: GET `/machinetype` ")
	public void getAllMachineType() throws Exception {
		when(service.getAllMachineTypes()).thenReturn(machineTypes);

		mockMvc.perform(get("/machinetype").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].machine_type_id", is(1)))
				.andExpect(jsonPath("$[0].machine_type_name", is("MachineType_name")))
				.andExpect(jsonPath("$[0].machine_type_desc", is("MachineType_desc")))
				.andExpect(jsonPath("$.length()", is(2)));
	}

	@DisplayName("MachineType controller Layer ::GET `/machinetype/1`")
	@Test
	public void getAllCompanysTest() throws Exception {
		when(service.getMachineType(1)).thenReturn(machineType1);

		mockMvc.perform(get("/machinetype/1").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.machine_type_id", is(1)))
				.andExpect(jsonPath("$.machine_type_name", is("MachineType_name")))
				.andExpect(jsonPath("$.machine_type_desc", is("MachineType_desc")));
	}

	@DisplayName("MachineType controller Layer :: POST `/machinetype`")
	@Test
	public void testSaveMachineType() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		String json = objectMapper.writeValueAsString(machineType1);

		when(service.saveMachineType(machineType1)).thenReturn(machineType1);

		mockMvc.perform(
				post("/machinetype").contentType(MediaType.APPLICATION_JSON).content(json).characterEncoding("utf-8"))
				.andExpect(status().is2xxSuccessful());
	}

	@DisplayName("MachineType controller Layer :: UPDATE `/machinetype/1")
	@Test
	public void testUpdateMachineType() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		String json = objectMapper.writeValueAsString(machineType1);

		when(service.updateMachineType(machineType1, 1)).thenReturn(machineType1);

		mockMvc.perform(put("/machinetype/{id}", 1).contentType(MediaType.APPLICATION_JSON).content(json)
				.characterEncoding("utf-8")).andExpect(status().is2xxSuccessful());
	}

	@DisplayName("MachineType controller Layer :: DELETE `/machinetype/1`")
	@Test
	public void testMachineTypeDelete() throws Exception {
		mockMvc.perform(delete("/machinetype/{id}", 11).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

}
