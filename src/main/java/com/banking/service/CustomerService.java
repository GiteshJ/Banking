package com.banking.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.banking.dto.AccountDetailsDto;
import com.banking.dto.CustomerDetailsDto;
import com.banking.dto.KycDetailsDto;
import com.banking.dto.UserDto;
import com.banking.model.Account;
import com.banking.model.Customer;
import com.banking.model.CustomerAccount;
import com.banking.model.Kyc;
import com.banking.repository.AccountRepository;
import com.banking.repository.CustomerAccountRepository;
import com.banking.repository.CustomerRepository;
import com.banking.repository.KycRepository;
import com.banking.util.KYCValidation;
import com.banking.util.UserValidation;

@Service
@Transactional
public class CustomerService {

	@Autowired
	KycRepository kycRepository;
	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	CustomerAccountRepository customerAccountRepository;
	@Autowired
	AccountRepository accountRepository;
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	SequenceGeneratorService sequenceGenerator;
	
	public String deleteCustomer(String username) {
		
		try {
			Customer cust = customerRepository.findByUserName(username);
			if(cust==null) throw new Exception("Invalid details!");
			customerRepository.deleteById(Long.valueOf(cust.getId()));
		}
		catch(Exception e) {
			return "Customer does not exist";
		}
		return "Customer Successfully Deleted";
		
	}
	
	public String addCustomer(UserDto user) {
		
		try {
			if(!UserValidation.validate(user)) throw new Exception("Invalid details!");
			if(customerRepository.findByUserName(user.getUserName())!=null) throw new Exception("Invalid details!");
			Customer cust = new Customer();
			cust.setUserName(user.getUserName());
			cust.setPassword(passwordEncoder.encode(user.getPassword()));
			cust.setFirstName(user.getFirstName());
			cust.setLastName(user.getLastName());
			cust.setId(sequenceGenerator.generateSequence(Customer.SEQUENCE_NAME));
			customerRepository.save(cust);
		}
		catch(Exception e) {
			return "Customer Cannot be created try another username";
		}
		return "Customer Successfully Registered";
		
	}
	
	public String updateKyc(KycDetailsDto kycDetailsDto) {
		try {
			if(!KYCValidation.validate(kycDetailsDto)) throw new Exception("Invalid Details");
			Customer cust = customerRepository.findByUserName(kycDetailsDto.getUserName());
			if(cust==null) throw new Exception("Invalid User");
			Kyc kycData = kycRepository.findByCustId(cust.getId());
			if(kycData==null) {
				kycData = new Kyc();
				kycData.setId(sequenceGenerator.generateSequence(Kyc.SEQUENCE_NAME));
			}
			
			kycData.setCustId(cust.getId());
			kycData.setAdhaarNum(kycDetailsDto.getAdhaarNum());
			kycData.setPanNum(kycDetailsDto.getPanNum());
			
			kycData = kycRepository.save(kycData);
			
			cust.setKycDetails(kycData.getId());
			customerRepository.save(cust);
			
			
		} 
		catch(Exception e) {
			e.printStackTrace();
			return "KYC unsuccessful";
		}
		return "KYC successfully updated";
	}

	public CustomerDetailsDto getCustomerDetails(String username) {

		CustomerDetailsDto customerDetails = new CustomerDetailsDto();
		
		try {
			Customer cust = customerRepository.findByUserName(username);
			if(cust==null) throw new Exception("Invalid Details");
			
			customerDetails.setFirstName(cust.getFirstName());
			customerDetails.setLastName(cust.getLastName());
			customerDetails.setUserName(cust.getUserName());
			
			Kyc kycData = kycRepository.findByCustId(cust.getId());
			if(kycData==null) {
				customerDetails.setAdhaarNum("");
				customerDetails.setPanNum("");
			}
			else {
				customerDetails.setAdhaarNum(kycData.getAdhaarNum());
				customerDetails.setPanNum(kycData.getPanNum());
			}
			
			List<AccountDetailsDto> accountDetailsList= new ArrayList<AccountDetailsDto>();
			List<CustomerAccount> custAcc = customerAccountRepository.findByCustId(cust.getId());
			if(custAcc!=null && !custAcc.isEmpty()) {
				for(CustomerAccount data: custAcc) {
					Optional<Account> acc = accountRepository.findById(data.getAccId());
					if(acc.isPresent()) {
						AccountDetailsDto  accDetails  = new AccountDetailsDto();
						accDetails.setAccNum(acc.get().getAccNum());
						accDetails.setBalance(acc.get().getBalance());
						accDetails.setAccType(acc.get().getAccType());
						accountDetailsList.add(accDetails);
					}
				}
			}
			customerDetails.setAccountList(accountDetailsList);
			
		}
		catch(Exception e) {
			return null;
		}
		return customerDetails;
	}


}