package com.sparepart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparepart.model.Machine;
import com.sparepart.model.Parts;

public interface PartsRepo extends JpaRepository<Parts, Integer>{

	List<Parts> findByPartName(String string);

	List<Parts> findByPartMachineId(Machine m);

	List<Parts> findByPartDescContaining(String token);

	List<Parts> findByPartNameContainingOrPartDescContaining(String token, String token1);

}
