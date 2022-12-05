package com.sparepart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparepart.model.MachineType;
import com.sparepart.model.Role;

public interface RoleRepo extends JpaRepository<Role, Integer>{

}
