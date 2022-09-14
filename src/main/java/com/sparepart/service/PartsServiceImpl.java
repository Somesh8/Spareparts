package com.sparepart.service;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
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

}