package com.sparepart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparepart.model.User;

public interface UserRepo extends JpaRepository<User, Integer>{

}
