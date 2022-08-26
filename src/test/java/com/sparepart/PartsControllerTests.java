package com.sparepart;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
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

import com.sparepart.controller.PartsController;
import com.sparepart.dto.PartsDTO;
import com.sparepart.exception.ResourceNotFoundException;
import com.sparepart.exception.WrongInputException;
import com.sparepart.model.Machine;
import com.sparepart.model.MachineType;
import com.sparepart.model.Parts;
import com.sparepart.repository.MachineRepo;
import com.sparepart.repository.PartsRepo;

@SpringBootTest
class PartsControllerTests {

	@Autowired
	private PartsController controller;

	@MockBean
	private PartsRepo repository;
	@MockBean
	private MachineRepo macRepository;
	
	Machine mac = new Machine(1, "Name", "Desc", new MachineType(1, "MachintType_name", "MachintType_desc"), null);

	@DisplayName("Parts controller Layer :: getAllParts")
	@Test
	void testGetAllParts() {
		when(repository.findAll()).thenReturn(Stream
				.of(new Parts(1, "Part_name", "Part_desc", 10.00, mac), new Parts(2, "Part_name2", "Part_desc2", 100.00, mac))
				.toList());
		assertEquals(2, controller.getAllParts().size());
	}
	
	@DisplayName("Parts controller Layer :: saveParts")
	@Test
	void testSaveParts() throws WrongInputException {
		PartsDTO newPartsDto = new PartsDTO(1, "Name", "DEsc", 10.00, mac.getMachineId());
		Parts newParts1 = new Parts(newPartsDto.getPartId(), newPartsDto.getPartName(), newPartsDto.getPartDesc(),
				newPartsDto.getPartCost(), mac);
		Optional<Machine> optionalMachine = Optional.of(mac);
		when(macRepository.findById(mac.getMachineId())).thenReturn(optionalMachine);

		when(repository.save(any(Parts.class))).thenReturn(newParts1);
		assertEquals(newParts1, controller.savePart(newPartsDto));
	}
	
	@DisplayName("Parts controller Layer :: updateParts")
	@Test
	void testUpdateParts() throws WrongInputException {
		int PartsId = 1;
		Parts newParts = new Parts(PartsId, "Part name", "Part_desc", 10.00, mac);
		Optional<Parts> optionalParts = Optional.of(newParts);
		when(repository.findById(PartsId)).thenReturn(optionalParts);
		when(repository.save(newParts)).thenReturn(newParts);
		assertEquals(newParts, controller.updatePart(PartsId, newParts));
	}
	
	@DisplayName("Parts controller Layer :: deleteParts")
	@Test
	void testDeleteParts() {
		Parts newParts = new Parts(1, "Part_name", "Part_desc", 10.00, mac);
		Optional<Parts> optionalParts = Optional.of(newParts);
	    when(repository.findById(1)).thenReturn(optionalParts);

		controller.deletePart(1);
		verify(repository, times(1)).delete(newParts);
	}
	@DisplayName("Parts Controller Layer :: getPartsByMachine")
	@Test
	void testGetPartsByMachine() throws ResourceNotFoundException {
		Parts part1 = new Parts(1, "Part_name1", "Part_desc1", 100.00, mac);
		Parts part2 = new Parts(2, "Part_name2", "Part_desc2", 120.00, mac);

		Optional<Machine> optionalMachine = Optional.of(mac);
		when(macRepository.findById(mac.getMachineId())).thenReturn(optionalMachine);
		
		when(repository.findByPartMachineId(mac)).thenReturn(Stream.of(part1, part2).toList());
		assertEquals(2, controller.getAllPartsForMachine(mac.getMachineId()).size());

	}
	
	@DisplayName("Parts Controller Layer :: searchAllParts")
	@Test
	void testSearchAllParts() throws ResourceNotFoundException {
		String token ="par";
		Parts part1 = new Parts(1, "Part_name1", "Part_desc1", 100.00, mac);
		Parts part2 = new Parts(2, "Part_name2", "Part_desc2", 120.00, mac);
		
		when(repository.findByPartNameContainingOrPartDescContaining(token, token)).thenReturn(Stream.of(part1, part2).toList());
		assertEquals(2, controller.searchAllParts(token).size());

	}
}
