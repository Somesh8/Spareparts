package com.sparepart.service;

import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sparepart.dto.PartsDTO;
import com.sparepart.exception.WrongInputException;
import com.sparepart.model.Machine;
import com.sparepart.model.Parts;
import com.sparepart.repository.MachineRepo;
import com.sparepart.repository.PartsRepo;

@Service
public class PartsServiceImpl implements PartsService {
	
	@Autowired
	PartsRepo repository;
	@Autowired
	MachineRepo machineRepository;

	String regex = "^\\s*[A-Za-z]+(?:\\s+[A-Za-z]+)*\\s*$";
	
	@Override
	public List<Parts> getAllParts() {
		return repository.findAll();
	}

	@Override
	public Parts savePart(PartsDTO partsDto) throws WrongInputException {
		if(!Pattern.compile(regex).matcher(partsDto.getPartName()).matches()) {
			throw new WrongInputException("Wrong part name");
		}
		Parts parts= new Parts();
		Machine machine = machineRepository.findById(partsDto.getPartMachineId()).get();
		parts.setPartDesc(partsDto.getPartDesc());
		parts.setPartName(partsDto.getPartName());
		parts.setPartMachineId(machine);
		return repository.save(parts);
	}

	@Override
	public Parts updatePart(Parts parts, int id) throws WrongInputException {
		if(!Pattern.compile(regex).matcher(parts.getPartName()).matches()) {
			throw new WrongInputException("Wrong part name");
		}
		
		Parts partsDB = repository.findById(id).get();

		if (Objects.nonNull(parts.getPartName())
				&& !"".equalsIgnoreCase(parts.getPartName())) {
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
	public List<Parts> getAllPartsForMachine(int mid) {
		Machine m = machineRepository.findById(mid).get();
		return repository.findByPartMachineId(m);	
	}

	@Override
	public List<Parts> searchAllParts(String token) {
		return repository.findByPartNameContainingOrPartDescContaining(token, token);
	}
}