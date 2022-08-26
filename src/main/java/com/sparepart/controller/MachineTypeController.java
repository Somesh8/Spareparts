package com.sparepart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sparepart.exception.WrongInputException;
import com.sparepart.model.MachineType;
import com.sparepart.service.MachineTypeService;

@RestController
public class MachineTypeController {
	@Autowired
	MachineTypeService service;
	
	@GetMapping(value="/machinetypes")
	public List<MachineType> getAllMachineTypes() {
		return service.getAllMachineTypes();
	}
	
	@PostMapping(value="/machinetype")
	public MachineType saveMachineType(@RequestBody MachineType machineType) throws WrongInputException {
		return service.saveMachineType(machineType);
	}
	

	@PutMapping(value="/machinetype/{id}")
	public MachineType updateMachineType(@PathVariable("id") int id,@RequestBody MachineType machineType) throws WrongInputException {
		return service.updateMachineType(machineType, id);
	}
	
	@DeleteMapping(value="/machinetype/{id}")
	public String deleteMachineType(@PathVariable("id") int id) {
		service.deleteMachineType( id);
		return "Deleted Successfully";
	}
}
