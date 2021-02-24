package com.banking.actuator.contributor;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import com.banking.repository.AdminRepository;
import com.banking.repository.CustomerRepository;
import com.banking.repository.EmployeeRepository;

@Component
public class TotalUsersInfoContributor implements InfoContributor {

    @Autowired
    AdminRepository adminRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    EmployeeRepository employeeRepository;
    
    @Override
    public void contribute(Info.Builder builder) {
        Map<String, Long> userDetails = new HashMap<>();
       
        userDetails.put("ADMIN", adminRepository.count());
        userDetails.put("EMPLOYEE", employeeRepository.count());
        userDetails.put("CUSTOMER", customerRepository.count());

       
        builder.withDetail("users", userDetails);
    }
}
