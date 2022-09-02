package com.sparepart.service;

import java.util.List;

import com.sparepart.exception.CanNotUpdateBrandNameException;
import com.sparepart.model.MachineType;

public interface MachineTypeService {
	
	public List<MachineType> getAllMachineTypes();
	
	public MachineType saveMachineType(MachineType machineType);
	
	public MachineType updateMachineType(MachineType machineType, int id) throws CanNotUpdateBrandNameException;
	
	public void deleteMachineType(int id) ;

	public MachineType getMachineType(int id);
}
