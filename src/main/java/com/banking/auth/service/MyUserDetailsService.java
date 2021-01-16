package com.banking.auth.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.banking.auth.model.MyAdminDetails;
import com.banking.auth.model.MyEmpDetails;
import com.banking.model.Admin;
import com.banking.model.Employee;
import com.banking.repository.AdminRepository;
import com.banking.repository.EmployeeRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	AdminRepository adminRepository;
	@Autowired
	EmployeeRepository employeeRepository;
	
	
	
//    @Override
//    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
//        return new User("foo", passwordEncoder.encode("foo"),
//                new ArrayList<>());
//    }
	
	@Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<Admin> admin = adminRepository.findByUserName(userName);

        Optional<Employee> emp = employeeRepository.findByUserName(userName);
        
        if(!admin.isPresent() && !emp.isPresent()) throw new UsernameNotFoundException("Not found: " + userName);
        
        if(admin.isPresent()) return admin.map(MyAdminDetails::new).get();
        return emp.map(MyEmpDetails::new).get();
        
    }
}