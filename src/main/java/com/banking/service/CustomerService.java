package com.banking.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.banking.ValidationUtil.KYCValidation;
import com.banking.ValidationUtil.UserValidation;
import com.banking.aspect.customAnnotation.Logging;
import com.banking.aspect.customAnnotation.TrackExecutionTime;
import com.banking.common.CommonConstants;
import com.banking.customException.DuplicateUserNameException;
import com.banking.customException.InvalidDataException;
import com.banking.customException.UserNotFoundException;
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


@Service
@Transactional
@Logging
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
	
	public void deleteCustomer(String username) throws Exception  {
		
		if(username==null || username.trim().equalsIgnoreCase("")) throw new InvalidDataException(CommonConstants.INVALID_DATA_ERROR_MESSAGE);
		Customer cust = customerRepository.findByUserName(username);
		if(cust==null) throw new UserNotFoundException(CommonConstants.CUSTOMER_DOES_NOT_EXIST_ERROR_MESSAGE);
		customerRepository.deleteById(Long.valueOf(cust.getId()));
		return;
		
	}
	
	public void addCustomer(UserDto user) throws Exception {
		
		
		if(!UserValidation.validate(user)) throw new InvalidDataException(CommonConstants.INVALID_DATA_ERROR_MESSAGE);
		if(customerRepository.findByUserName(user.getUserName())!=null) throw new DuplicateUserNameException(CommonConstants.DUPLICATE_USERNAME_ERROR_MESSAGE);
		
		Customer cust = new Customer();
		cust.setUserName(user.getUserName());
		cust.setPassword(passwordEncoder.encode(user.getPassword()));
		cust.setFirstName(user.getFirstName());
		cust.setLastName(user.getLastName());
		cust.setId(sequenceGenerator.generateSequence(Customer.SEQUENCE_NAME));
		customerRepository.save(cust);
		
		return ;
		
	}
	
	public void updateKyc(KycDetailsDto kycDetailsDto) throws Exception {
		
		if(!KYCValidation.validate(kycDetailsDto)) throw new InvalidDataException(CommonConstants.INVALID_DATA_ERROR_MESSAGE);
		Customer cust = customerRepository.findByUserName(kycDetailsDto.getUserName());
		if(cust==null) throw new UserNotFoundException(CommonConstants.CUSTOMER_DOES_NOT_EXIST_ERROR_MESSAGE);
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
			
		return;
	}

	@TrackExecutionTime
	public CustomerDetailsDto getCustomerDetails(String username) throws Exception{

		CustomerDetailsDto customerDetails = new CustomerDetailsDto();
		
		
		if(username==null || username.equalsIgnoreCase("")) throw new InvalidDataException(CommonConstants.INVALID_DATA_ERROR_MESSAGE);
		Customer cust = customerRepository.findByUserName(username);
		if(cust==null) throw new UserNotFoundException(CommonConstants.CUSTOMER_DOES_NOT_EXIST_ERROR_MESSAGE);
		
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
		return customerDetails;
	}


}
