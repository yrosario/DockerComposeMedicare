package com.medicare.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medicare.backend.model.Role;
import com.medicare.backend.repository.RoleRepository;


@Service
public class RoleServiceImpl implements GenericService<Role, Long> {

	@Autowired
	private RoleRepository roleRepository;
	
	@Override
	public Role save(Role user) {
		
		return roleRepository.save(user);
	}

	@Override
	public List<Role> findAll() {
		return roleRepository.findAll();
	}

	@Override
	public Role findById(Long id) {
		return roleRepository.findById(id).isPresent() ? roleRepository.findById(id).get() : null;
	}

	@Override
	public Role update(Role user) {
		return roleRepository.findById(user.getId()).isPresent() ? roleRepository.save(user) : null;
			
	}

	@Override
	public boolean delete(Long id) {
		if(roleRepository.findById(id).isPresent()) {
			roleRepository.deleteById(id);
			return true;
		}
		
		return false;
	}

}
