package com.banking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banking.dto.CustomerDetailsDto;
import com.banking.dto.KycDetailsDto;
import com.banking.service.CustomerService;

@RestController
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	CustomerService customerService;
	
	
	@PostMapping("/updateKYC")
	public String updateKyc(KycDetailsDto kycDetailsDto) {
		return customerService.updateKyc(kycDetailsDto);
	}
	
	@GetMapping("/{username}")
	public CustomerDetailsDto getCustomer(@PathVariable String username) {
		return customerService.getCustomerDetails(username);
	}
}
