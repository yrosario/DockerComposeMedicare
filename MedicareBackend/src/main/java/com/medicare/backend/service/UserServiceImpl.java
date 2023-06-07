package com.medicare.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medicare.backend.model.User;
import com.medicare.backend.repository.UserRepository;


@Service
public class UserServiceImpl implements GenericService<User, Long> {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public User save(User user) {
		
		return userRepository.save(user);
	}

	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public User findById(Long id) {
		return userRepository.findById(id).isPresent() ? userRepository.findById(id).get() : null;
	}

	@Override
	public User update(User user) {
		return userRepository.findById(user.getUid()).isPresent() ? userRepository.save(user) : null;
			
	}

	@Override
	public boolean delete(Long id) {
		if(userRepository.findById(id).isPresent()) {
			userRepository.deleteById(id);
			return true;
		}
		
		return false;
	}
	
	public User findByUsername(String username) {
        List<User> users = userRepository.findAll();
		
		if(users.isEmpty())
			return null;
			
		
		for(User user : users)
			if(user.getUsername().equalsIgnoreCase(username))
				return user;
		
		return null;
	}

}
