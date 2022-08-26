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

import com.sparepart.controller.MachineController;
import com.sparepart.dto.MachineDTO;
import com.sparepart.exception.WrongInputException;
import com.sparepart.model.Company;
import com.sparepart.model.Machine;
import com.sparepart.model.MachineType;
import com.sparepart.repository.CompanyRepo;
import com.sparepart.repository.MachineRepo;
import com.sparepart.repository.MachineTypeRepo;

@SpringBootTest
class MachineControllerTests {

	@Autowired
	private MachineController controller;

	@MockBean
	private MachineRepo repository;
	
	@MockBean
	private MachineTypeRepo mtRepository;
	
	@MockBean
	private CompanyRepo cmpRepository;

	private MachineType mt = new MachineType(1, "Dummy_MT_Name", "Dummy_MT_Desc");

	private Company company = new Company(1, "Dummy Company Name", "Dummy_Company_Desc");

	@DisplayName("Machine controller Layer :: getAllMachines")
	@Test
	void testGetAllMachines() {
		when(repository.findAll()).thenReturn(Stream.of(new Machine(1, "Machine_name", "Machine_desc", mt, null),
				new Machine(2, "Machine_name2", "Machine_desc2", mt, null)).toList());
		assertEquals(2, controller.getAllMachines().size());
	}

	@DisplayName("Machine controller Layer :: saveMachine")
	@Test
	void testSaveMachine() throws WrongInputException {
		int MachineId = 1;
		Machine newMachine = new Machine(MachineId, "Machine name", "Machine_desc", mt, null);
		MachineDTO newMachineDto = new MachineDTO(1,"nm", "desc", mt.getMachineTypeId(), company.getCompanyId());
		
		Optional<Machine> optionalMachine= Optional.of(newMachine);
		when(repository.findById(MachineId)).thenReturn(optionalMachine);
		
		Company newCompany = new Company(1, "Comp name", "Comp_desc");
		Optional<Company> optionalCompany = Optional.of(newCompany);
		
		MachineType newMt = new MachineType(1, "MT_Name", "MT_Desc");
		Optional<MachineType> optionalMt = Optional.of(newMt);
		
		when(cmpRepository.findById(newMachineDto.getCompanyId())).thenReturn(optionalCompany);
		when(mtRepository.findById(newMachineDto.getMachineTypeId())).thenReturn(optionalMt);
		
		when(repository.save(any(Machine.class))).thenReturn(newMachine);
		assertEquals(newMachine, controller.saveMachine(newMachineDto));
	}

	@DisplayName("Machine controller Layer :: updateMachine")
	@Test
	void testUpdateMachine() throws WrongInputException {
		int MachineId = 1;
		Machine newMachine = new Machine(MachineId, "Machine name", "Machine_desc", mt, null);
		Optional<Machine> optionalMachine = Optional.of(newMachine);
		when(repository.findById(MachineId)).thenReturn(optionalMachine);
		when(repository.save(newMachine)).thenReturn(newMachine);
		assertEquals(newMachine, controller.updateMachine(MachineId, newMachine));
	}

	@DisplayName("Machine controller Layer :: deleteMachine")
	@Test
	void testDeleteMachine() {
		Machine newMachine = new Machine(1, "Machine_name", "Machine_desc", mt, null);
		Optional<Machine> optionalMachine = Optional.of(newMachine);
		when(repository.findById(1)).thenReturn(optionalMachine);

		controller.deleteMachine(1);
		verify(repository, times(1)).delete(newMachine);
	}

}
