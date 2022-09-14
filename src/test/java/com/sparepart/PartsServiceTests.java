package com.sparepart;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.sparepart.dto.PartsDTO;
import com.sparepart.exception.CanNotUpdateBrandNameException;
import com.sparepart.exception.WrongInputException;
import com.sparepart.model.Machine;
import com.sparepart.model.MachineType;
import com.sparepart.model.Parts;
import com.sparepart.repository.MachineRepo;
import com.sparepart.repository.PartsRepo;
import com.sparepart.service.PartsServiceImpl;

@SpringBootTest
class PartsServiceTests {

	@Autowired
	private PartsServiceImpl service;

	@MockBean
	private PartsRepo repository;
	@MockBean
	private MachineRepo macRepository;

	private Machine mac = new Machine(1, "Name", "Desc", new MachineType(1, "MachintType_name", "MachintType_desc"), null);

	private Parts part1 = new Parts(1, "Part_name", "Part_desc", 10.00, mac);
	private Parts part2 = new Parts(2, "Part_name2", "Part_desc2", 100.00, mac);
	private List<Parts> parts = new ArrayList<>(Arrays.asList(
			part1,part2));
	
	@DisplayName("Parts Service Layer :: getAllParts")
	@Test
	void testGetAllParts() {
		when(repository.findAll()).thenReturn(parts);
		assertEquals(2, service.getAllParts().size());
	}

	@DisplayName("Parts Service Layer :: getMachineById")
	@Test
	void testGetMachineById() {
		int partId = 1;
		PartsDTO newPartsDto = new PartsDTO(1, "Name", "Desc", 10.00, mac.getMachineId());
		Parts newParts1 = new Parts(newPartsDto.getPartId(), newPartsDto.getPartName(), newPartsDto.getPartDesc(),
				newPartsDto.getPartCost(), mac);
		Optional<Parts> optionalParts = Optional.of(newParts1);
		when(repository.findById(partId)).thenReturn(optionalParts);
		
		assertEquals(newParts1, service.getPartById(partId));
	}
	
	@DisplayName("Parts Service Layer :: saveParts")
	@Test
	void testSaveParts() throws WrongInputException {
		PartsDTO newPartsDto = new PartsDTO(1, "Name", "Desc", 10.00, mac.getMachineId());
		Parts newParts1 = new Parts(newPartsDto.getPartId(), newPartsDto.getPartName(), newPartsDto.getPartDesc(),
				newPartsDto.getPartCost(), mac);
		Optional<Machine> optionalMachine = Optional.of(mac);
		when(macRepository.findById(mac.getMachineId())).thenReturn(optionalMachine);

		when(repository.save(any(Parts.class))).thenReturn(newParts1);
		assertEquals(newParts1, service.savePart(newPartsDto));
	}

	@DisplayName("Parts Service Layer :: updateParts")
	@Test
	void testUpdateParts() throws CanNotUpdateBrandNameException {
		int PartsId = 1;
		Optional<Parts> optionalParts = Optional.of(part1);
		when(repository.findById(PartsId)).thenReturn(optionalParts);
		when(repository.save(part1)).thenReturn(part1);
		assertEquals(part1, service.updatePart(part1, PartsId));
	}

	@DisplayName("Parts Service Layer :: deleteParts")
	@Test
	void testDeleteParts() {
		Optional<Parts> optionalParts = Optional.of(part1);
		when(repository.findById(1)).thenReturn(optionalParts);

		service.deletePart(1);
		verify(repository, times(1)).delete(part1);
	}

	@DisplayName("Parts Service Layer :: searchAllParts")
	@Test
	void testSearchAllParts() {
		String token = "par";
		when(repository.findByPartNameContainingOrPartDescContaining(token, token))
				.thenReturn(parts);
		assertEquals(2, service.searchAllParts(token).size());
	}

	@DisplayName("Parts Service Layer :: savePartNullPointerExceptionHandle")
	@Test
	void testNullPointerException() {
		PartsDTO newPartsDto = new PartsDTO(1, "Name", "DEsc", 10.00, 0);
		NullPointerException thrown = assertThrows(NullPointerException.class, () -> {
			Parts newParts1 = new Parts(newPartsDto.getPartId(), newPartsDto.getPartName(), newPartsDto.getPartDesc(),
					newPartsDto.getPartCost(), null);
			repository.save(newParts1);
		}, "partMachineId id marked non null but is null");
		assertTrue(thrown.getMessage().contains("null"));
	}

	@DisplayName("Parts Service Layer :: savePartNoSuchElementExceptionHandle")
	@Test
	void testNoSuchElementException() {
		PartsDTO newPartsDto = new PartsDTO(1, "Name", "DEsc", 10.00, 0);
		NoSuchElementException thrown = assertThrows(NoSuchElementException.class, () -> {
			service.savePart(newPartsDto);
		}, "No value present");
		assertTrue(thrown.getMessage().contains("No"));
	}	
}
