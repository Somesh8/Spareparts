package com.sparepart.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.sparepart.dto.PartsDTO;
import com.sparepart.exception.CanNotUpdateBrandNameException;
import com.sparepart.exception.WrongInputException;
import com.sparepart.model.Parts;

public interface PartsService {
	
	public List<Parts> getAllParts();

	public Parts getPartById(int id);
	
	public Parts savePart(PartsDTO partsDto) throws WrongInputException;
	
	public Parts updatePart(Parts parts, int id) throws CanNotUpdateBrandNameException;
	
	public void deletePart(int id);
	
	
	public List<Parts> searchAllParts(String token);

	public ResponseEntity<Map<String, Object>> getAllPartsWithPagination(Pageable paging);

}
