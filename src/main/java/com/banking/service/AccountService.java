package com.banking.service;

import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.banking.ValidationUtil.AccountValidation;
import com.banking.ValidationUtil.BankStatementValidation;
import com.banking.ValidationUtil.LinkAccountValidation;
import com.banking.ValidationUtil.TransferMoneyValidation;
import com.banking.ValidationUtil.UpdateBalanceValidation;
import com.banking.aspect.customAnnotation.Logging;
import com.banking.aspect.customAnnotation.TrackExecutionTime;
import com.banking.common.CommonConstants;
import com.banking.customException.AccountLinkedException;
import com.banking.customException.DuplicateAccNumException;
import com.banking.customException.InsufficientBalanceException;
import com.banking.customException.InvalidDataException;
import com.banking.customException.UserNotFoundException;
import com.banking.dto.BankStatementDto;
import com.banking.dto.LinkAccountDto;
import com.banking.dto.TransferMoneyDto;
import com.banking.dto.UpdateBalanceDto;
import com.banking.model.Account;
import com.banking.model.Customer;
import com.banking.model.CustomerAccount;
import com.banking.reportGenerators.BankStatementGenerator;
import com.banking.repository.AccountRepository;
import com.banking.repository.CustomerAccountRepository;
import com.banking.repository.CustomerRepository;

@Service
@Transactional
@Logging
public class AccountService {

	@Autowired
	AccountRepository accountRepository;
	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	CustomerAccountRepository customerAccountRepository;
	@Autowired
	SequenceGeneratorService sequenceGenerator;
	
	public void creatAccount(Account account) throws Exception {
		
		if(!AccountValidation.validation(account)) throw new InvalidDataException(CommonConstants.INVALID_DATA_ERROR_MESSAGE);
		if(accountRepository.findByAccNum(account.getAccNum())!=null) throw new DuplicateAccNumException(CommonConstants.DUPLICATE_ACCNUM_ERROR_MESSAGE);
		Account acc = new Account();
		acc.setAccNum(account.getAccNum());
		acc.setBalance(account.getBalance());
		acc.setAccType(account.getAccType());
		acc.setId(sequenceGenerator.generateSequence(Account.SEQUENCE_NAME));
		accountRepository.save(acc);
		
		return;
	}
	
	public void linkAccount(LinkAccountDto linkAccountDto) throws Exception {
		
		if(!LinkAccountValidation.validate(linkAccountDto)) 
			throw new InvalidDataException(CommonConstants.INVALID_DATA_ERROR_MESSAGE);
		
		Customer cust = customerRepository.findByUserName(linkAccountDto.getUsername());
		Account acc = accountRepository.findByAccNum(linkAccountDto.getAccNum());
		
		if(acc==null) 
			throw new InvalidDataException(CommonConstants.ACCOUNT_DOES_NOT_EXIST_ERROR_MESSAGE);
		if(cust==null) 
			throw new UserNotFoundException(CommonConstants.CUSTOMER_DOES_NOT_EXIST_ERROR_MESSAGE);
		if(customerAccountRepository.findByAccId(acc.getId())!=null) 
			throw new AccountLinkedException(CommonConstants.ACCOUNT_ALREADY_LINKED_ERROR_MESSAGE);
		
		CustomerAccount customerAccount = new CustomerAccount();
		customerAccount.setCustId(cust.getId());
		customerAccount.setAccId(acc.getId());
		customerAccount.setId(sequenceGenerator.generateSequence(CustomerAccount.SEQUENCE_NAME));
		customerAccountRepository.save(customerAccount);
					
		return;
	}
	
	
	public void updateBalance(UpdateBalanceDto updateBalance ) throws Exception{
		
		if(!UpdateBalanceValidation.validate(updateBalance)) throw new InvalidDataException(CommonConstants.INVALID_DATA_ERROR_MESSAGE);
		Account acc = accountRepository.findByAccNum(updateBalance.getAccNum());
		if(acc==null) 
			throw new InvalidDataException(CommonConstants.ACCOUNT_DOES_NOT_EXIST_ERROR_MESSAGE);
		acc.setBalance(acc.getBalance()+updateBalance.getBalance());
		accountRepository.save(acc);
		
		return;
		
	}
	
	public String getBalance(Integer accNum) throws Exception{
		
		if(accNum==null) throw new InvalidDataException(CommonConstants.INVALID_DATA_ERROR_MESSAGE);
		Account acc = accountRepository.findByAccNum(accNum);
		if(acc==null) 
			throw new InvalidDataException(CommonConstants.ACCOUNT_DOES_NOT_EXIST_ERROR_MESSAGE);
		return "Balance : "+acc.getBalance();
	}
	
	@TrackExecutionTime
	public void transferMoney(TransferMoneyDto transferMoneyDto) throws Exception {
		
		if(!TransferMoneyValidation.validate(transferMoneyDto)) throw new InvalidDataException(CommonConstants.INVALID_DATA_ERROR_MESSAGE);
		Account accSen = accountRepository.findByAccNum(transferMoneyDto.getAccNumSender());
		Account accRec = accountRepository.findByAccNum(transferMoneyDto.getAccNumReceiver());
		float value = transferMoneyDto.getBalance();
		if(accSen==null || accRec==null) throw new InvalidDataException(CommonConstants.ACCOUNT_DOES_NOT_EXIST_ERROR_MESSAGE);
		if(accSen.getBalance()-value<0) throw new InsufficientBalanceException(CommonConstants.INSUFFICIENT_BALANCE_ERROR_MESSAGE);
		accSen.setBalance(accSen.getBalance()- value);
		accRec.setBalance(accRec.getBalance()+ value);
		accountRepository.save(accSen);
		accountRepository.save(accRec);
		return;
	}
	
	@TrackExecutionTime
	public byte[] getStatement(BankStatementDto bankStatementDto) {
		try {
			if(!BankStatementValidation.validate(bankStatementDto)) throw new InvalidDataException(CommonConstants.INVALID_DATA_ERROR_MESSAGE);
			Account acc = accountRepository.findByAccNum(bankStatementDto.getAccNum());
			if(acc==null) 
				throw new InvalidDataException(CommonConstants.ACCOUNT_DOES_NOT_EXIST_ERROR_MESSAGE);
			return IOUtils.toByteArray(BankStatementGenerator.bankStatementPdfReport());
		}
		catch(Exception e) {
			return null;
		}
		
	}
	
	
	
}
