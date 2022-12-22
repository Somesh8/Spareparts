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

import com.sparepart.dto.PartsDTO;
import com.sparepart.exception.CanNotUpdateBrandNameException;
import com.sparepart.exception.WrongInputException;
import com.sparepart.model.Parts;
import com.sparepart.service.PartsService;

@RestController
@RequestMapping("/parts")
public class PartsController {
	@Autowired
	PartsService service;
	
	@GetMapping
	public List<Parts> getAllParts() {
		return service.getAllParts();
	}
	
	@GetMapping(value="/{id}")
	public Parts getPartById(@PathVariable("id") int id) {
		return service.getPartById(id);
	}
	
	@GetMapping(value="/pagination")
	public  ResponseEntity<Map<String, Object>> getAllPartsWithPagination(@RequestParam(defaultValue = "1", required = false, value="page") int page,
		      @RequestParam(defaultValue = "3", required = false, value="size") int size) {
		Pageable paging = PageRequest.of(--page, size);
		return service.getAllPartsWithPagination(paging);
	}
	
	@GetMapping(value="/search")
	public List<Parts> searchAllParts(@RequestParam("token") String token) {
		return service.searchAllParts(token);
	}
	
	@PostMapping
	public Parts savePart(@RequestBody PartsDTO part) throws WrongInputException {
		return service.savePart(part);
	}
	
	@PutMapping(value="/{id}")
	public Parts updatePart(@PathVariable("id") int id,@RequestBody Parts part) throws CanNotUpdateBrandNameException {
		return service.updatePart(part, id);
	}
	
	@DeleteMapping(value="/{id}")
	public String deletePart(@PathVariable("id") int id) {
		service.deletePart( id);
		return "Deleted Successfully";
	}
}
