package com.sparepart.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.sparepart.dto.MachineDTO;
import com.sparepart.exception.CanNotUpdateBrandNameException;
import com.sparepart.model.Company;
import com.sparepart.model.Machine;
import com.sparepart.model.MachineType;
import com.sparepart.model.Parts;
import com.sparepart.repository.CompanyRepo;
import com.sparepart.repository.MachineRepo;
import com.sparepart.repository.MachineTypeRepo;
import com.sparepart.repository.PartsRepo;

@Service
@Transactional
public class MachineServiceImpl implements MachineService {

	@Autowired
	MachineRepo repository;
	@Autowired
	MachineTypeRepo mtRepository;
	@Autowired
	CompanyRepo cmpRepository;
	@Autowired
	private PartsRepo partRepository;
	
	Pageable pageable = PageRequest.of(0, 3);

	public static Map<String, Object> getResponse(List<Machine> companyList, int pageNumber, long totalElements,
			int totalPages) {
		Map<String, Object> response = new HashMap<>();
		response.put("data", companyList);
		response.put("currentPage", ++pageNumber);
		response.put("totalItems", totalElements);
		response.put("totalPages", totalPages);
		return response;
	}
	
	@Override
	public List<Machine> getAllMachines() {
		return repository.findAll();
	}

	@Override
	public Machine saveMachine(MachineDTO machineDto) {
		Machine machine = new Machine();
		MachineType mt = mtRepository.findById(machineDto.getMachineTypeId()).get();

		Company cmp = cmpRepository.findById(machineDto.getCompanyId()).get();
		machine.setMachineDesc(machineDto.getMachineDesc());
		machine.setMachineName(machineDto.getMachineName());
		machine.setCompany(cmp);
		machine.setMachineType(mt);
		return repository.save(machine);
	}

	@Override
	public Machine updateMachine(Machine machine, int id) throws CanNotUpdateBrandNameException {
		Machine machineDB = repository.findById(id).get();

		if (Objects.nonNull(machine.getMachineName()) && !"".equalsIgnoreCase(machine.getMachineName())) {
			if (!machine.getMachineName().contains(machineDB.getMachineName())) {
				throw new CanNotUpdateBrandNameException("Can not update brand name");
			}
			machineDB.setMachineName(machine.getMachineName());
		}

		if (Objects.nonNull(machine.getMachineDesc()) && !"".equalsIgnoreCase(machine.getMachineDesc())) {
			machineDB.setMachineDesc(machine.getMachineDesc());
		}
		return repository.save(machineDB);
	}

	@Override
	public void deleteMachine(int id) {
		Machine machineDB = repository.findById(id).get();
		repository.delete(machineDB);
	}

	@Override
	public Machine getMachine(int id) {
		return repository.findById(id).get();
	}

	@Override
	public List<Parts> getAllPartsForMachine(int machineId) {
		Machine m = repository.findById(machineId).get();
		return partRepository.findByPartMachineId(m);
//		return null;
	}

	@Override
	public ResponseEntity<Map<String, Object>> getAllMachinesWithPagination(Pageable paging) {
		List<Machine> machineList = new ArrayList<Machine>();
		try {
			Page<Machine> page = repository.findAll(paging);
			if (page.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			machineList = page.getContent();
			Map<String, Object> response = new HashMap<>();
			response = getResponse(machineList, page.getNumber(), page.getTotalElements(), page.getTotalPages());
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
