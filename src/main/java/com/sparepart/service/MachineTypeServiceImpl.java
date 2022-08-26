package com.sparepart.service;

import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sparepart.exception.WrongInputException;
import com.sparepart.model.MachineType;
import com.sparepart.repository.MachineTypeRepo;

@Service
@Transactional
public class MachineTypeServiceImpl implements MachineTypeService {

	@Autowired
	MachineTypeRepo repository;

	String regex = "^\\s*[A-Za-z]+(?:\\s+[A-Za-z]+)*\\s*$";
	
	@Override
	public List<MachineType> getAllMachineTypes() {
		return repository.findAll();
	}

	@Override
	public MachineType saveMachineType(MachineType machineType) throws WrongInputException {
		if(!Pattern.compile(regex).matcher(machineType.getMachineTypeName()).matches()) {
			throw new WrongInputException("Wrong machine type name");
		}
		return repository.save(machineType);
	}

	@Override
	public MachineType updateMachineType(MachineType machineType, int id) throws WrongInputException {
		MachineType machineTypeDB = repository.findById(id).get();

		if (Objects.nonNull(machineType.getMachineTypeName())
				&& !"".equalsIgnoreCase(machineType.getMachineTypeName())) {
			if(!Pattern.compile(regex).matcher(machineType.getMachineTypeName()).matches()) {
				throw new WrongInputException("Wrong machine type name");
			}
			machineTypeDB.setMachineTypeName(machineType.getMachineTypeName());
		}

		if (Objects.nonNull(machineType.getMachineTypeDesc())
				&& !"".equalsIgnoreCase(machineType.getMachineTypeDesc())) {
			machineTypeDB.setMachineTypeDesc(machineType.getMachineTypeDesc());
		}
		return repository.save(machineTypeDB);
	}

	@Override
	public void deleteMachineType(int id) {
		MachineType machineTypeDB = repository.findById(id).get();
		repository.delete(machineTypeDB);
	}
}
