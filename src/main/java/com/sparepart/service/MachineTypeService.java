package com.sparepart.service;

import java.util.List;

import com.sparepart.exception.WrongInputException;
import com.sparepart.model.MachineType;

public interface MachineTypeService {
	
	public List<MachineType> getAllMachineTypes();
	
	public MachineType saveMachineType(MachineType machineType) throws WrongInputException;
	
	public MachineType updateMachineType(MachineType machineType, int id) throws WrongInputException;
	
	public void deleteMachineType(int id) ;
}
