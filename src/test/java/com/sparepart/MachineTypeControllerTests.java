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

import com.sparepart.controller.MachineTypeController;
import com.sparepart.exception.WrongInputException;
import com.sparepart.model.MachineType;
import com.sparepart.repository.MachineTypeRepo;

@SpringBootTest
class MachineTypeControllerTests {

	@Autowired
	private MachineTypeController controller;

	@MockBean
	private MachineTypeRepo repository;

	@DisplayName("MachineType controller Layer :: getAllmachineTypes")
	@Test
	void testGetAllMachineTypes() {
		MachineType firstMachineType = new MachineType(1, "MachineType_name", "MachineType_desc");
		when(repository.findAll()).thenReturn(Stream
				.of(firstMachineType, new MachineType(2, "MachineType_name2", "MachineType_desc2"))
				.toList());
		List<MachineType> result = controller.getAllMachineTypes();
		assertEquals(2, result.size());
		assertEquals(firstMachineType, result.get(0));
	}
	
	@DisplayName("MachineType controller Layer :: saveMachineType")
	@Test
	void testSaveMachineType() throws WrongInputException {
		MachineType newMachineType = new MachineType(1, "MachineType name", "MachineType_desc");
		when(repository.save(newMachineType)).thenReturn(newMachineType);
		assertEquals(newMachineType, controller.saveMachineType(newMachineType));
	}
	
	@DisplayName("MachineType controller Layer :: updateMachineType")
	@Test
	void testUpdateMachineType() throws WrongInputException {
		int MachineTypeId = 1;
		MachineType newMachineType = new MachineType(MachineTypeId, "MachineType name", "MachineType_desc");
		Optional<MachineType> optionalMachineType = Optional.of(newMachineType);
		when(repository.findById(MachineTypeId)).thenReturn(optionalMachineType);
		when(repository.save(newMachineType)).thenReturn(newMachineType);
		assertEquals(newMachineType, controller.updateMachineType(MachineTypeId, newMachineType));
	}
	
	@DisplayName("MachineType controller Layer :: deleteMachineType")
	@Test
	void testDeleteMachineType() {
		MachineType newMachineType = new MachineType(1, "MachineType_name", "MachineType_desc");
		Optional<MachineType> optionalMachineType = Optional.of(newMachineType);
	    when(repository.findById(1)).thenReturn(optionalMachineType);

		controller.deleteMachineType(1);
		verify(repository, times(1)).delete(newMachineType);
	}

}
