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
import com.sparepart.dto.PartsDTO;
import com.sparepart.model.Company;
import com.sparepart.model.Machine;
import com.sparepart.model.MachineType;
import com.sparepart.model.Parts;
import com.sparepart.service.PartsService;

@SpringBootTest
@AutoConfigureMockMvc
class PartsControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	PartsService partsService;

	private Company company = new Company(1, "Dummy Company Name", "Dummy_Company_Desc");
	private Machine mac = new Machine(1, "Name", "Desc", new MachineType(1, "MachintType_name", "MachintType_desc"),
			company);

	@DisplayName("Parts controller Layer :: GET `/parts`")
	@Test
	void testGetAllMachines() throws Exception {
		when(partsService.getAllParts()).thenReturn(Stream.of(new Parts(1, "Part_name", "Part_desc", 10.00, mac),
				new Parts(2, "Part_name2", "Part_desc2", 100.00, mac)).toList());

		mockMvc.perform(get("/parts").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].part_id", is(1))).andExpect(jsonPath("$[0].part_name", is("Part_name")))
				.andExpect(jsonPath("$[0].part_desc", is("Part_desc"))).andExpect(jsonPath("$.length()", is(2)));
	}

	@DisplayName("Parts controller Layer :: GET `/parts/1`")
	@Test
	public void getMachineTest() throws Exception {
		PartsDTO newPartsDto = new PartsDTO(1, "Name", "Desc", 10.00, mac.getMachineId());
		Parts newParts1 = new Parts(newPartsDto.getPartId(), newPartsDto.getPartName(), newPartsDto.getPartDesc(),
				newPartsDto.getPartCost(), mac);
		when(partsService.getPartById(1)).thenReturn(newParts1);

		mockMvc.perform(get("/parts/1").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.part_id", is(1))).andExpect(jsonPath("$.part_name", is("Name")))
				.andExpect(jsonPath("$.part_desc", is("Desc")));

	}

	@DisplayName("Parts controller Layer :: POST `/parts`")
	@Test
	void testSaveMachine() throws Exception {
		PartsDTO newPartsDto = new PartsDTO(1, "Name", "DEsc", 10.00, mac.getMachineId());
		Parts newParts1 = new Parts(newPartsDto.getPartId(), newPartsDto.getPartName(), newPartsDto.getPartDesc(),
				newPartsDto.getPartCost(), mac);
		ObjectMapper objectMapper = new ObjectMapper();
		String json = objectMapper.writeValueAsString(newPartsDto);

		when(partsService.savePart(newPartsDto)).thenReturn(newParts1);

		mockMvc.perform(post("/parts").contentType(MediaType.APPLICATION_JSON).content(json).characterEncoding("utf-8"))
				.andExpect(status().is2xxSuccessful());
	}

	@DisplayName("Parts controller Layer :: PUT `/parts/1`")
	@Test
	void testUpdateMachine() throws Exception {
		PartsDTO newPartsDto = new PartsDTO(1, "Name", "DEsc", 10.00, mac.getMachineId());
		Parts newParts1 = new Parts(newPartsDto.getPartId(), newPartsDto.getPartName(), newPartsDto.getPartDesc(),
				newPartsDto.getPartCost(), mac);
		ObjectMapper objectMapper = new ObjectMapper();
		String json = objectMapper.writeValueAsString(newPartsDto);

		when(partsService.updatePart(newParts1, 1)).thenReturn(newParts1);

		mockMvc.perform(
				put("/parts/{id}", 1).contentType(MediaType.APPLICATION_JSON).content(json).characterEncoding("utf-8"))
				.andExpect(status().is2xxSuccessful());
	}

	@DisplayName("Parts controller Layer :: DELETE `/parts`")
	@Test
	void testDeleteMachine() throws Exception {
		mockMvc.perform(
				delete("/parts/{id}", 1).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@DisplayName("Parts controller Layer :: GET `/parts/machine/1`")
	@Test
	public void getPartByMachineTest() throws Exception {
//		PartsDTO newPartsDto = new PartsDTO(1, "Name", "Desc", 10.00, mac.getMachineId());
//		Parts newParts1 = new Parts(newPartsDto.getPartId(), newPartsDto.getPartName(), newPartsDto.getPartDesc(),
//				newPartsDto.getPartCost(), mac);
		Parts part1 = new Parts(1, "Part_name1", "Part_desc1", 100.00, mac);
		Parts part2 = new Parts(2, "Part_name2", "Part_desc2", 120.00, mac);

		when(partsService.getAllPartsForMachine(1)).thenReturn(Stream.of(part1, part2).toList());

		mockMvc.perform(get("/parts/machine/1").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].part_id", is(1))).andExpect(jsonPath("$[0].part_name", is("Part_name1")))
				.andExpect(jsonPath("$[0].part_desc", is("Part_desc1")));

	}

	@DisplayName("Parts Controller Layer :: GET  `/parts/search?token=par`")
	@Test
	void testSearchAllParts() throws Exception {
		String token = "par";
		Parts part1 = new Parts(1, "Part_name1", "Part_desc1", 100.00, mac);
		Parts part2 = new Parts(2, "Part_name2", "Part_desc2", 120.00, mac);

		when(partsService.searchAllParts(token)).thenReturn(Stream.of(part1, part2).toList());

		mockMvc.perform(get("/parts/search?token="+token).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].part_id", is(1)))
				.andExpect(jsonPath("$[0].part_name", is("Part_name1")))
				.andExpect(jsonPath("$[0].part_desc", is("Part_desc1")));
	}

}
