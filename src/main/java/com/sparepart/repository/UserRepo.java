package com.sparepart.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.sparepart.model.User;

public interface UserRepo extends JpaRepository<User, Integer>{

	User findByEmail(String email);
	Page<User> findAll(Pageable pageable);
}
