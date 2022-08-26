package com.sparepart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparepart.model.Company;

public interface CompanyRepo extends JpaRepository<Company, Integer>{

}
