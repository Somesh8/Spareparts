package com.sparepart.service;

import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sparepart.dto.MachineDTO;
import com.sparepart.exception.WrongInputException;
import com.sparepart.model.Company;
import com.sparepart.model.Machine;
import com.sparepart.model.MachineType;
import com.sparepart.repository.CompanyRepo;
import com.sparepart.repository.MachineRepo;
import com.sparepart.repository.MachineTypeRepo;

@Service
@Transactional
public class MachineServiceImpl implements MachineService {
	
	@Autowired
	MachineRepo repository;
	@Autowired
	MachineTypeRepo mtRepository;
	@Autowired
	CompanyRepo cmpRepository;
	
	String regex = "^\\s*[A-Za-z]+(?:\\s+[A-Za-z]+)*\\s*$";

	@Override
	public List<Machine> getAllMachines() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}

	@Override
	public Machine saveMachine(MachineDTO machineDto) throws WrongInputException {
		// TODO Auto-generated method stub
		if(!Pattern.compile(regex).matcher(machineDto.getMachineName()).matches()) {
			throw new WrongInputException("Wrong machine name");
		}
		
		Machine machine= new Machine();
		MachineType mt = mtRepository.findById(machineDto.getMachineTypeId()).get();
		
		Company cmp= cmpRepository.findById(machineDto.getCompanyId()).get();
		machine.setMachineDesc(machineDto.getMachineDesc());
		machine.setMachineName(machineDto.getMachineName());
		machine.setCompany(cmp);
		machine.setMachineType(mt);
		return repository.save(machine);
	}

	@Override
	public Machine updateMachine(Machine machine, int id) throws WrongInputException {
		// TODO Auto-generated method stub
		if(!Pattern.compile(regex).matcher(machine.getMachineName()).matches()) {
			throw new WrongInputException("Wrong machine name");
		}
		
		Machine machineDB = repository.findById(id).get();

		if (Objects.nonNull(machine.getMachineName())
				&& !"".equalsIgnoreCase(machine.getMachineName())) {
			machineDB.setMachineName(machine.getMachineName());
		}

		if (Objects.nonNull(machine.getMachineDesc())
				&& !"".equalsIgnoreCase(machine.getMachineDesc())) {
			machineDB.setMachineDesc(machine.getMachineDesc());
		}
		return repository.save(machineDB);
	}

	@Override
	public void deleteMachine(int id) {
		// TODO Auto-generated method stub
		Machine machineDB = repository.findById(id).get();
		repository.delete(machineDB);
	}
}
