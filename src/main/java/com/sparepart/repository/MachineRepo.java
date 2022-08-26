package com.sparepart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparepart.model.Machine;

public interface MachineRepo extends JpaRepository<Machine, Integer>{

}
