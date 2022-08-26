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
import com.sparepart.model.MachineType;
import com.sparepart.repository.MachineTypeRepo;
import com.sparepart.service.MachineTypeService;

@SpringBootTest
class MachineTypeServiceTests {

	@Autowired
	private MachineTypeService service;

	@MockBean
	private MachineTypeRepo repository;

	@DisplayName("MachineType Service Layer :: getAllMachintTypeanies")
	@Test
	void testGetAllMachintTypes() {
		when(repository.findAll()).thenReturn(Stream
				.of(new MachineType(1, "MachintType_name", "MachintType_desc"), new MachineType(2, "MachintType_name2", "MachintType_desc2"))
				.toList());
		assertEquals(2, service.getAllMachineTypes().size());
	}
	
	@DisplayName("MachineType Service Layer :: saveMachineType")
	@Test
	void testSaveMachineType() throws WrongInputException {
		MachineType newMachineType = new MachineType(1, "MachintType name", "MachintType_desc");
		when(repository.save(newMachineType)).thenReturn(newMachineType);
		assertEquals(newMachineType, service.saveMachineType(newMachineType));
	}
	
	@DisplayName("MachineType Service Layer :: updateMachineType")
	@Test
	void testUpdateMachineType() throws WrongInputException {
		int MachineTypeId = 1;
		MachineType newMachineType = new MachineType(MachineTypeId, "MachintType name", "MachintType_desc");
		Optional<MachineType> optionalMachineType = Optional.of(newMachineType);
		when(repository.findById(MachineTypeId)).thenReturn(optionalMachineType);
		when(repository.save(newMachineType)).thenReturn(newMachineType);
		assertEquals(newMachineType, service.updateMachineType(newMachineType, MachineTypeId));
	}
	
	@DisplayName("MachineType Service Layer :: deleteMachineType")
	@Test
	void testDeleteMachineType() {
		MachineType newMachineType = new MachineType(1, "MachintType_name", "MachintType_desc");
		Optional<MachineType> optionalMachineType = Optional.of(newMachineType);
	    when(repository.findById(1)).thenReturn(optionalMachineType);

		service.deleteMachineType(1);
		verify(repository, times(1)).delete(newMachineType);
	}

}
