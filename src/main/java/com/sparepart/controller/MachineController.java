package com.sparepart.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sparepart.dto.MachineDTO;
import com.sparepart.exception.CanNotUpdateBrandNameException;
import com.sparepart.model.Machine;
import com.sparepart.model.Parts;
import com.sparepart.service.MachineService;

@RestController
@RequestMapping("/machine")
public class MachineController {
	@Autowired
	MachineService service;
	
	@GetMapping
	public List<Machine> getAllMachines() {
		return service.getAllMachines();
	}
	
	@GetMapping(value="/{id}")
	public Machine getMachine(@PathVariable("id") int id) {
		return service.getMachine( id);
	}
	
	@GetMapping(value="/pagination")
	public  ResponseEntity<Map<String, Object>> getAllMachinesWithPagination(@RequestParam(defaultValue = "1", required = false, value="page") int page,
		      @RequestParam(defaultValue = "3", required = false, value="size") int size) {
		Pageable paging = PageRequest.of(--page, size);
		return service.getAllMachinesWithPagination(paging);
	}
	
	@GetMapping(value = "/parts/{machineId}")
	public List<Parts> getAllPartsForMachine(@PathVariable("machineId") int machineId) {
		return service.getAllPartsForMachine(machineId);
	}
	
	@PostMapping
	public Machine saveMachine(@RequestBody MachineDTO machineDto) {
		return service.saveMachine(machineDto);
	}

	@PutMapping(value="/{id}")
	public Machine updateMachine(@PathVariable("id") int id,@RequestBody Machine machine) throws CanNotUpdateBrandNameException {
		return service.updateMachine(machine, id);
	}
	
	@DeleteMapping(value="/{id}")
	public String deleteMachine(@PathVariable("id") int id) {
		service.deleteMachine( id);
		return "Deleted Successfully";
	}
}
