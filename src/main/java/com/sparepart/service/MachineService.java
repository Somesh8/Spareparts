package com.sparepart.service;

import java.util.List;

import com.sparepart.dto.MachineDTO;
import com.sparepart.exception.WrongInputException;
import com.sparepart.model.Machine;

public interface MachineService {
	
	public List<Machine> getAllMachines();
	
	public Machine saveMachine(MachineDTO machineDto) throws WrongInputException ;
	
	public Machine updateMachine(Machine machine, int id)  throws WrongInputException ;
	
	public void deleteMachine(int id);
}
