package com.banking.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.banking.ValidationUtil.UserValidation;
import com.banking.aspect.customAnnotation.Logging;
import com.banking.common.CommonConstants;
import com.banking.customException.DuplicateUserNameException;
import com.banking.customException.InvalidDataException;
import com.banking.dto.UserDto;
import com.banking.model.Admin;
import com.banking.repository.AdminRepository;

@Service
@Transactional
@Logging
public class AdminService {
	@Autowired
	AdminRepository adminRepository;
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	SequenceGeneratorService sequenceGenerator;
	
	public void addAdmin(UserDto user) throws Exception{
		
		
		if(!UserValidation.validate(user)) throw new InvalidDataException(CommonConstants.INVALID_DATA_ERROR_MESSAGE);
		
		Optional<Admin> adminOp =  adminRepository.findByUserName(user.getUserName());
		if(adminOp.isPresent()) throw new DuplicateUserNameException(CommonConstants.DUPLICATE_USERNAME_ERROR_MESSAGE);
		
		Admin admin = new Admin();
		admin.setUserName(user.getUserName());
		admin.setPassword(passwordEncoder.encode(user.getPassword()));
		admin.setFirstName(user.getFirstName());
		admin.setLastName(user.getLastName());
		admin.setRoles("ROLE_ADMIN");
		admin.setId(sequenceGenerator.generateSequence(Admin.SEQUENCE_NAME));
		adminRepository.save(admin);
		
		return;
		
	}
	
	
	
	
}
