package com.sparepart.service;

import java.util.List;

import com.sparepart.dto.UserDto;
import com.sparepart.exception.CanNotUpdateBrandNameException;
import com.sparepart.model.User;

public interface UserService {
	
	public List<User> getAllUsers();
	
	public User saveUser(User User);
	
	public User updateUser(User User, int id) throws CanNotUpdateBrandNameException;
	
	public void deleteUser(int id) ;

	public User getUser(int id);

	public User getUserByEmail(String username);

	public UserDto registerNewUser(UserDto userDto);
}
