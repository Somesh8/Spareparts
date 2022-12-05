package com.sparepart.dto;

import java.util.HashSet;
import java.util.Set;

import com.sparepart.model.Role;

import lombok.Data;

@Data
public class UserDto {
	private int userId;
	
	private String fullname;
	
	private String email;
	
	private String password;
	
	private int age;
	
	private Set<Role> roles = new HashSet<>();
}
