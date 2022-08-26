package com.sparepart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparepart.model.MachineType;

public interface MachineTypeRepo extends JpaRepository<MachineType, Integer>{

}
