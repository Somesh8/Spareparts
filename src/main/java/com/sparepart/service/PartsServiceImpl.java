package com.sparepart.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.sparepart.dto.PartsDTO;
import com.sparepart.exception.CanNotUpdateBrandNameException;
import com.sparepart.model.Machine;
import com.sparepart.model.Parts;
import com.sparepart.repository.MachineRepo;
import com.sparepart.repository.PartsRepo;

@Service
public class PartsServiceImpl implements PartsService {
	
	@Autowired
	private PartsRepo repository;
	@Autowired
	private MachineRepo machineRepository;

	Pageable pageable = PageRequest.of(0, 3);

	public static Map<String, Object> getResponse(List<Parts> companyList, int pageNumber, long totalElements,
			int totalPages) {
		Map<String, Object> response = new HashMap<>();
		response.put("data", companyList);
		response.put("currentPage", ++pageNumber);
		response.put("totalItems", totalElements);
		response.put("totalPages", totalPages);
		return response;
	}
	
	@Override
	public List<Parts> getAllParts() {
		return repository.findAll();
	}

	@Override
	public Parts getPartById(int id) {
		return repository.findById(id).get();
	}
	
	@Override
	public Parts savePart(PartsDTO partsDto) {
		Parts parts= new Parts();
		Machine machine = machineRepository.findById(partsDto.getPartMachineId()).get();
		parts.setPartDesc(partsDto.getPartDesc());
		parts.setPartName(partsDto.getPartName());
		parts.setPartMachineId(machine);
		return repository.save(parts);
	}

	@Override
	public Parts updatePart(Parts parts, int id) throws CanNotUpdateBrandNameException {
		Parts partsDB = repository.findById(id).get();

		if (Objects.nonNull(parts.getPartName())
				&& !"".equalsIgnoreCase(parts.getPartName())) {
			if (!parts.getPartName().contains(partsDB.getPartName())) {
				throw new CanNotUpdateBrandNameException("Can not update brand name");
			}
			partsDB.setPartName(parts.getPartName());
		}

		if (Objects.nonNull(parts.getPartDesc())
				&& !"".equalsIgnoreCase(parts.getPartDesc())) {
			partsDB.setPartDesc(parts.getPartDesc());
		}
		return repository.save(partsDB);
	}

	@Override
	public void deletePart(int id) {
		Parts partsDB = repository.findById(id).get();
		repository.delete(partsDB);
	}

	@Override
	public List<Parts> searchAllParts(String token) {
		return repository.findByPartNameContainingOrPartDescContaining(token, token);
	}

	@Override
	public ResponseEntity<Map<String, Object>> getAllPartsWithPagination(Pageable paging) {
		List<Parts> partsList = new ArrayList<Parts>();
		try {
			Page<Parts> page = repository.findAll(paging);
			if (page.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			partsList = page.getContent();
			Map<String, Object> response = new HashMap<>();
			response = getResponse(partsList, page.getNumber(), page.getTotalElements(), page.getTotalPages());
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}