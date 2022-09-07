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
import com.sparepart.dto.MachineDTO;
import com.sparepart.model.Company;
import com.sparepart.model.Machine;
import com.sparepart.model.MachineType;
import com.sparepart.service.MachineService;

@SpringBootTest
@AutoConfigureMockMvc
class MachineControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	MachineService machineService;

	private MachineType mt = new MachineType(1, "Dummy_MT_Name", "Dummy_MT_Desc");

	private Company company = new Company(1, "Dummy Company Name", "Dummy_Company_Desc");

	private Machine firstMachine = new Machine(1, "HP Pavilion 15", "Laptop", mt, null);
	private Machine secondMachine = new Machine(2, "Machine_name2", "Machine_desc2", mt, null);

	private List<Machine> machines = new ArrayList<>(Arrays.asList(firstMachine, secondMachine));

	@DisplayName("Machine controller Layer :: GET `/machine`")
	@Test
	void testGetAllMachines() throws Exception {
		when(machineService.getAllMachines()).thenReturn(machines);

		mockMvc.perform(get("/machine").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].machine_id", is(1)))
				.andExpect(jsonPath("$[0].machine_name", is("HP Pavilion 15")))
				.andExpect(jsonPath("$[0].machine_desc", is("Laptop"))).andExpect(jsonPath("$.length()", is(2)));
	}

	@DisplayName("Company controller Layer ::GET `/machine/1`")
	@Test
	public void getMachineTest() throws Exception {
		when(machineService.getMachine(1)).thenReturn(firstMachine);

		mockMvc.perform(get("/machine/1").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.machine_id", is(1))).andExpect(jsonPath("$.machine_name", is("HP Pavilion 15")))
				.andExpect(jsonPath("$.machine_desc", is("Laptop")));

	}

	@DisplayName("Machine controller Layer :: POST `/machine`")
	@Test
	void testSaveMachine() throws Exception {
		int MachineId = 1;
		Machine newMachine = new Machine(MachineId, "Machine name", "Machine_desc", mt, null);
		MachineDTO newMachineDto = new MachineDTO(1, newMachine.getMachineName(), newMachine.getMachineDesc(),
				mt.getMachineTypeId(), company.getCompanyId());

		ObjectMapper objectMapper = new ObjectMapper();
		String json = objectMapper.writeValueAsString(newMachineDto);

		when(machineService.saveMachine(newMachineDto)).thenReturn(newMachine);

		mockMvc.perform(
				post("/machine").contentType(MediaType.APPLICATION_JSON).content(json).characterEncoding("utf-8"))
				.andExpect(status().is2xxSuccessful());
	}

	@DisplayName("Machine controller Layer :: PUT `/machine/1`")
	@Test
	void testUpdateMachine() throws Exception {
		int MachineId = 1;

		ObjectMapper objectMapper = new ObjectMapper();
		String json = objectMapper.writeValueAsString(firstMachine);

		when(machineService.updateMachine(firstMachine, MachineId)).thenReturn(firstMachine);

		mockMvc.perform(put("/machine/{id}", MachineId).contentType(MediaType.APPLICATION_JSON).content(json)
				.characterEncoding("utf-8")).andExpect(status().is2xxSuccessful());
	}

	@DisplayName("Machine controller Layer :: DELETE `/machine`")
	@Test
	void testDeleteMachine() throws Exception {
		mockMvc.perform(
				delete("/machine/{id}", 1).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

}
