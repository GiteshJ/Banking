package com.banking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.banking.dto.UserDto;
import com.banking.model.Admin;
import com.banking.repository.AdminRepository;
import com.banking.repository.EmployeeRepository;
import com.banking.util.UserValidation;

@Service
@Transactional
public class AdminService {
	@Autowired
	AdminRepository adminRepository;
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	SequenceGeneratorService sequenceGenerator;
	
	public String addAdmin(UserDto user) {
		
		try {
			if(!UserValidation.validate(user)) throw new Exception("Invalid details!");
			Admin admin = new Admin();
			if(adminRepository.findByUserName(user.getUserName())!=null) throw new Exception("Invalid details!");
			admin.setUserName(user.getUserName());
			admin.setPassword(passwordEncoder.encode(user.getPassword()));
			admin.setFirstName(user.getFirstName());
			admin.setLastName(user.getLastName());
			admin.setRoles("ROLE_ADMIN");
			admin.setId(sequenceGenerator.generateSequence(Admin.SEQUENCE_NAME));
			System.out.println("ereeee");
			adminRepository.save(admin);
		}
		catch(Exception e) {
			e.printStackTrace();
			return "Admin Cannot be created try another username";
		}
		return "Admin Successfully Registered";
		
	}
	
	
	
	
}
