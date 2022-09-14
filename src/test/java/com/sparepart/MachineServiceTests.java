package com.sparepart;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
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

import com.sparepart.dto.MachineDTO;
import com.sparepart.exception.CanNotUpdateBrandNameException;
import com.sparepart.exception.WrongInputException;
import com.sparepart.model.Company;
import com.sparepart.model.Machine;
import com.sparepart.model.MachineType;
import com.sparepart.model.Parts;
import com.sparepart.repository.CompanyRepo;
import com.sparepart.repository.MachineRepo;
import com.sparepart.repository.MachineTypeRepo;
import com.sparepart.repository.PartsRepo;
import com.sparepart.service.MachineService;

@SpringBootTest
class MachineServiceTests {

	@Autowired
	private MachineService service;

	@MockBean
	private MachineRepo repository;

	@MockBean
	private MachineTypeRepo mtRepository;
	
	@MockBean
	private PartsRepo partRepository;

	@MockBean
	private CompanyRepo cmpRepository;

	private MachineType mt = new MachineType(1, "Dummy_MT_Name", "Dummy_MT_Desc");

	private Company company = new Company(1, "Dummy Company Name", "Dummy_Company_Desc");

	private Machine firstMachine = new Machine(1, "HP Pavilion 15", "Laptop", mt, null);
	private Machine secondMachine = new Machine(2, "Machine_name2", "Machine_desc2", mt, null);

	private List<Machine> machines = new ArrayList<>(Arrays.asList(firstMachine, secondMachine));

	@DisplayName("Machine Service Layer :: getAllMachines")
	@Test
	void testGetAllMachines() {
		when(repository.findAll()).thenReturn(machines);
		assertEquals(2, service.getAllMachines().size());
	}

	@DisplayName("Machine Service Layer :: getMachineById")
	@Test
	void testGetMachineById() {
		int MachineId = 1;
		Optional<Machine> optionalMachine = Optional.of(firstMachine);
		when(repository.findById(MachineId)).thenReturn(optionalMachine);

		assertEquals(firstMachine, service.getMachine(MachineId));
	}

	@DisplayName("Machine Service Layer :: saveMachine")
	@Test
	void testSaveMachine() throws WrongInputException {
		int MachineId = 1;
		MachineDTO newMachineDto = new MachineDTO(MachineId, firstMachine.getMachineName(),
				firstMachine.getMachineDesc(), mt.getMachineTypeId(), company.getCompanyId());

		Optional<Company> optionalCompany = Optional.of(company);

		Optional<MachineType> optionalMt = Optional.of(mt);

		when(cmpRepository.findById(newMachineDto.getCompanyId())).thenReturn(optionalCompany);
		when(mtRepository.findById(newMachineDto.getMachineTypeId())).thenReturn(optionalMt);

		when(repository.save(any(Machine.class))).thenReturn(firstMachine);
		assertEquals(firstMachine, service.saveMachine(newMachineDto));
	}

	@DisplayName("Machine Service Layer :: updateMachine")
	@Test
	void testUpdateMachine() throws WrongInputException, CanNotUpdateBrandNameException {
		int MachineId = 1;
		Optional<Machine> optionalMachine = Optional.of(firstMachine);
		when(repository.findById(MachineId)).thenReturn(optionalMachine);
		when(repository.save(firstMachine)).thenReturn(firstMachine);
		assertEquals(firstMachine, service.updateMachine(firstMachine, MachineId));
	}

	@DisplayName("Machine Service Layer :: deleteMachine")
	@Test
	void testDeleteMachine() {
		Optional<Machine> optionalMachine = Optional.of(firstMachine);
		when(repository.findById(1)).thenReturn(optionalMachine);

		service.deleteMachine(1);
		verify(repository, times(1)).delete(firstMachine);
	}

	@DisplayName("Machine Service Layer :: getAllPartsForMachine")
	@Test
	void testGetPartsByMachine() {
		Parts part1 = new Parts(1, "Part_name", "Part_desc", 10.00, firstMachine);
		Parts part2 = new Parts(2, "Part_name2", "Part_desc2", 100.00, firstMachine);
		List<Parts> parts = new ArrayList<>(Arrays.asList(
				part1,part2));
		
		Optional<Machine> optionalMachine = Optional.of(firstMachine);
		when(repository.findById(firstMachine.getMachineId())).thenReturn(optionalMachine);

		when(partRepository.findByPartMachineId(firstMachine)).thenReturn(parts);
		assertEquals(2, service.getAllPartsForMachine(firstMachine.getMachineId()).size());

	}
}
