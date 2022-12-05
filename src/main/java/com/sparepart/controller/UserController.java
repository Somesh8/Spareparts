package com.sparepart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparepart.exception.CanNotUpdateBrandNameException;
import com.sparepart.exception.WrongInputException;
import com.sparepart.model.User;
import com.sparepart.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	UserService service;
	
	@GetMapping
	public List<User> getAllUsers() {
		return service.getAllUsers();
	}
	
//	@GetMapping(value="/{id}")
//	public User getUser(@PathVariable("id") int id) {
//		return service.getUser(id);
//	}
	
	@GetMapping(value="/{email}")
	public User getUserByEmail(@PathVariable("email") String email) {
		System.out.println(email);
		return service.getUserByEmail(email);
	}
	
	@PostMapping
	public User saveUser(@RequestBody User user) {
		return service.saveUser(user);
	}

	@PutMapping(value="/{id}")
	public User updateUser(@PathVariable("id") int id,@RequestBody User user) throws WrongInputException, CanNotUpdateBrandNameException {
		return service.updateUser(user, id);
	}
	
	@DeleteMapping(value="/{id}")
	public String deleteUser(@PathVariable("id") int id) {
		service.deleteUser( id);
		return "Deleted Successfully";
	}
}
