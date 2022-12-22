package com.sparepart.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.sparepart.dto.MachineDTO;
import com.sparepart.exception.CanNotUpdateBrandNameException;
import com.sparepart.model.Machine;
import com.sparepart.model.Parts;

public interface MachineService {
	
	public List<Machine> getAllMachines();
	
	public Machine saveMachine(MachineDTO machineDto);
	
	public Machine updateMachine(Machine machine, int id) throws CanNotUpdateBrandNameException;
	
	public void deleteMachine(int id);

	public Machine getMachine(int id);

	public List<Parts> getAllPartsForMachine(int machineId);

	public ResponseEntity<Map<String, Object>> getAllMachinesWithPagination(Pageable paging);
}
