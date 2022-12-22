package com.sparepart.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sparepart.dto.UserDto;
import com.sparepart.exception.CanNotUpdateBrandNameException;
import com.sparepart.model.Role;
import com.sparepart.model.User;
import com.sparepart.repository.RoleRepo;
import com.sparepart.repository.UserRepo;

@Service
@Transactional
public class UserImpl implements UserService {

	@Autowired
	private UserRepo repository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepo roleRepo;

	@Autowired
	private ModelMapper modelMapper;

	public static Map<String, Object> getResponse(List<User> userList, int pageNumber, long totalElements,
			int totalPages) {
		Map<String, Object> response = new HashMap<>();
		response.put("data", userList);
		response.put("currentPage", ++pageNumber);
		response.put("totalItems", totalElements);
		response.put("totalPages", totalPages);
		return response;
	}

	Pageable pageable = PageRequest.of(0, 3);

	@Override
	public List<User> getAllUsers() {
		return repository.findAll();
	}

	@Override
	public User getUser(int id) {
		return repository.findById(id).get();
	}

	@Override
	public User saveUser(User user) {
		return repository.save(user);
	}

	@Override
	public UserDto registerNewUser(UserDto userDto) {
		// TODO Auto-generated method stub
		User user = this.modelMapper.map(userDto, User.class);
		user.setPassword(this.passwordEncoder.encode(userDto.getPassword()));

		Role role = this.roleRepo.findById(2).get();
		user.getRoles().add(role);

		User newUser = this.repository.save(user);
		return this.modelMapper.map(newUser, UserDto.class);
	}

	@Override
	public User updateUser(User user, int id) throws CanNotUpdateBrandNameException {
		User userDB = repository.findById(id).get();

		if (Objects.nonNull(user.getFullname()) && !"".equalsIgnoreCase(user.getFullname())) {
			if (!user.getFullname().contains(userDB.getFullname())) {
				throw new CanNotUpdateBrandNameException("Can not update brand name");
			}
			userDB.setFullname(user.getFullname());
		}

		if (Objects.nonNull(user.getEmail()) && !"".equalsIgnoreCase(user.getEmail())) {
			userDB.setEmail(user.getEmail());
		}
		return repository.save(userDB);
	}

	@Override
	public void deleteUser(int id) {
		User userDB = repository.findById(id).get();
		repository.delete(userDB);
	}

	@Override
	public User getUserByEmail(String email) {
		// TODO Auto-generated method stub
		User userDB = repository.findByEmail(email);
		return userDB;
	}

	@Override
	public ResponseEntity<Map<String, Object>> getAllUserWithPagination(Pageable paging) {
		List<User> userList = new ArrayList<User>();
		try {
			Page<User> page = repository.findAll(paging);
			if (page.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			userList = page.getContent();
			Map<String, Object> response = new HashMap<>();
			response = getResponse(userList, page.getNumber(), page.getTotalElements(), page.getTotalPages());
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
