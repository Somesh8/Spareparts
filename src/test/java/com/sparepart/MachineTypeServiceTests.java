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
import com.sparepart.model.MachineType;
import com.sparepart.repository.MachineTypeRepo;
import com.sparepart.service.MachineTypeService;

@SpringBootTest
class MachineTypeServiceTests {

	@Autowired
	private MachineTypeService service;

	@MockBean
	private MachineTypeRepo repository;

	private MachineType machineType1 = new MachineType(1, "MachintType_name", "MachintType_desc");
	private MachineType machineType2 = new MachineType(2, "MachintType_name2", "MachintType_desc2");
	private List<MachineType> machineTypes = new ArrayList<>(Arrays.asList(machineType1, machineType2));

	@DisplayName("MachineType Service Layer :: getAllMachintTypeanies")
	@Test
	void testGetAllMachintTypes() {
		when(repository.findAll()).thenReturn(machineTypes);
		assertEquals(2, service.getAllMachineTypes().size());
	}

	@DisplayName("MachineType Service Layer :: getMachineTypeById")
	@Test
	void testGetMachineTypeById() {
		int machineTypeId = 1;
		Optional<MachineType> optionalCompany = Optional.of(machineType1);
		when(repository.findById(machineTypeId)).thenReturn(optionalCompany);

		assertEquals(machineType1, service.getMachineType(machineTypeId));
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
	void testUpdateMachineType() throws WrongInputException, CanNotUpdateBrandNameException {
		int MachineTypeId = 1;
		Optional<MachineType> optionalMachineType = Optional.of(machineType1);
		when(repository.findById(MachineTypeId)).thenReturn(optionalMachineType);
		when(repository.save(machineType1)).thenReturn(machineType1);
		assertEquals(machineType1, service.updateMachineType(machineType1, MachineTypeId));
	}

	@DisplayName("MachineType Service Layer :: deleteMachineType")
	@Test
	void testDeleteMachineType() {
		Optional<MachineType> optionalMachineType = Optional.of(machineType1);
		when(repository.findById(1)).thenReturn(optionalMachineType);

		service.deleteMachineType(1);
		verify(repository, times(1)).delete(machineType1);
	}

}
