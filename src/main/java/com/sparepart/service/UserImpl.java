package com.sparepart.service;

import java.util.List;
import java.util.Objects;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sparepart.exception.CanNotUpdateBrandNameException;
import com.sparepart.model.User;
import com.sparepart.repository.UserRepo;

@Service
@Transactional
public class UserImpl implements UserService {

	@Autowired
	UserRepo repository;
	
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
	public User updateUser(User user, int id) throws CanNotUpdateBrandNameException {
		User userDB = repository.findById(id).get();

		if (Objects.nonNull(user.getFullname())
				&& !"".equalsIgnoreCase(user.getFullname())) {
			if (!user.getFullname().contains(userDB.getFullname())) {
				throw new CanNotUpdateBrandNameException("Can not update brand name");
			}
			userDB.setFullname(user.getFullname());
		}

		if (Objects.nonNull(user.getEmail())
				&& !"".equalsIgnoreCase(user.getEmail())) {
			userDB.setEmail(user.getEmail());
		}
		return repository.save(userDB);
	}

	@Override
	public void deleteUser(int id) {
		User userDB = repository.findById(id).get();
		repository.delete(userDB);
	}

}
