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

import com.sparepart.dto.MachineDTO;
import com.sparepart.exception.WrongInputException;
import com.sparepart.model.Machine;
import com.sparepart.service.MachineService;

@RestController
public class MachineController {
	@Autowired
	MachineService service;
	
	@GetMapping(value="/machines")
	public List<Machine> getAllMachines() {
		return service.getAllMachines();
	}
	
	@PostMapping(value="/machine")
	public Machine saveMachine(@RequestBody MachineDTO machineDto) throws WrongInputException {
		return service.saveMachine(machineDto);
	}

	@PutMapping(value="/machine/{id}")
	public Machine updateMachine(@PathVariable("id") int id,@RequestBody Machine machine) throws WrongInputException {
		if(machine == null) {
			throw new WrongInputException("Machine required must not be null");
		}
		return service.updateMachine(machine, id);
	}
	
	@DeleteMapping(value="/machine/{id}")
	public String deleteMachine(@PathVariable("id") int id) {
		service.deleteMachine( id);
		return "Deleted Successfully";
	}
}
