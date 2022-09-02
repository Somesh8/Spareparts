package com.sparepart.service;

import java.util.List;
import java.util.Objects;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sparepart.exception.CanNotUpdateBrandNameException;
import com.sparepart.model.MachineType;
import com.sparepart.repository.MachineTypeRepo;

@Service
@Transactional
public class MachineTypeServiceImpl implements MachineTypeService {

	@Autowired
	MachineTypeRepo repository;
	
	@Override
	public List<MachineType> getAllMachineTypes() {
		return repository.findAll();
	}

	@Override
	public MachineType getMachineType(int id) {
		return repository.findById(id).get();
	}

	@Override
	public MachineType saveMachineType(MachineType machineType) {
		return repository.save(machineType);
	}

	@Override
	public MachineType updateMachineType(MachineType machineType, int id) throws CanNotUpdateBrandNameException {
		MachineType machineTypeDB = repository.findById(id).get();

		if (Objects.nonNull(machineType.getMachineTypeName())
				&& !"".equalsIgnoreCase(machineType.getMachineTypeName())) {
			if (!machineType.getMachineTypeName().contains(machineTypeDB.getMachineTypeName())) {
				throw new CanNotUpdateBrandNameException("Can not update brand name");
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
