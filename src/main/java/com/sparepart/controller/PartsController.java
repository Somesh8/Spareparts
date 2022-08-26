package com.sparepart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sparepart.dto.PartsDTO;
import com.sparepart.exception.ResourceNotFoundException;
import com.sparepart.exception.WrongInputException;
import com.sparepart.model.Parts;
import com.sparepart.service.PartsServiceImpl;

@RestController
public class PartsController {
	@Autowired
	PartsServiceImpl service;
	
	@GetMapping(value="/parts")
	public List<Parts> getAllParts() {
		return service.getAllParts();
	}
	
	@GetMapping(value="/parts/machine/{mid}")
	public List<Parts> getAllPartsForMachine(@PathVariable("mid") int cid) throws ResourceNotFoundException {
		return service.getAllPartsForMachine(cid);
	}
	
	@GetMapping(value="/parts/search")
	public List<Parts> searchAllParts(@RequestParam("token") String token) throws ResourceNotFoundException {
		return service.searchAllParts(token);
	}
	
	@PostMapping(value="/part")
	public Parts savePart(@RequestBody PartsDTO part) throws WrongInputException {
		return service.savePart(part);
	}
	
	@PutMapping(value="/part/{id}")
	public Parts updatePart(@PathVariable("id") int id,@RequestBody Parts part) throws WrongInputException {
		return service.updatePart(part, id);
	}
	
	@DeleteMapping(value="/part/{id}")
	public String deletePart(@PathVariable("id") int id) {
		service.deletePart( id);
		return "Deleted Successfully";
	}
}
