package com.banking.service;

import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
import com.banking.util.AccountValidation;
import com.banking.util.BankStatementValidation;
import com.banking.util.LinkAccountValidation;
import com.banking.util.TransferMoneyValidation;
import com.banking.util.UpdateBalanceValidation;

@Service
@Transactional
public class AccountService {

	@Autowired
	AccountRepository accountRepository;
	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	CustomerAccountRepository customerAccountRepository;
	@Autowired
	SequenceGeneratorService sequenceGenerator;
	
	public String creatAccount(Account account) {
		try {
			if(!AccountValidation.validation(account)) throw new Exception("Invalid details");
			if(accountRepository.findByAccNum(account.getAccNum())!=null) throw new Exception("Invalid details");
			Account acc = new Account();
			acc.setAccNum(account.getAccNum());
			acc.setBalance(account.getBalance());
			acc.setAccType(account.getAccType());
			acc.setId(sequenceGenerator.generateSequence(Account.SEQUENCE_NAME));
			accountRepository.save(acc);
		}
		catch(Exception e) {
			return "Cannot create account";
		}
		return "Account successfully created";
	}
	
	public String linkAccount(LinkAccountDto linkAccountDto) {
		try {
			if(!LinkAccountValidation.validate(linkAccountDto)) throw new Exception("Invalid details");
			
			Customer cust = customerRepository.findByUserName(linkAccountDto.getUsername());
			Account acc = accountRepository.findByAccNum(linkAccountDto.getAccNum());
			
			if(acc==null || cust==null) throw new Exception("Invalid details");
			if(customerAccountRepository.findByAccId(acc.getId())!=null) throw new Exception("Invalid details");
			CustomerAccount customerAccount = new CustomerAccount();
			customerAccount.setCustId(cust.getId());
			customerAccount.setAccId(acc.getId());
			customerAccount.setId(sequenceGenerator.generateSequence(CustomerAccount.SEQUENCE_NAME));
			customerAccountRepository.save(customerAccount);
					
		}
		catch(Exception e) {
			return "Cannot Link Account / Already Linked";
		}
		return "Account Linked Successfully";
	}
	
	public String updateBalance(UpdateBalanceDto updateBalance ) {
		try {
			if(!UpdateBalanceValidation.validate(updateBalance))throw new Exception("Invalid details");
			Account acc = accountRepository.findByAccNum(updateBalance.getAccNum());
			if(acc==null) throw new Exception("Invalid details");
			acc.setBalance(acc.getBalance()+updateBalance.getBalance());
			accountRepository.save(acc);
		}
		catch(Exception e) {
			return "Cannot update";
		}
		return "Updated";
		
	}
	
	public String getBalance(Integer accNum) {
		try {
			if(accNum==null)throw new Exception("Invalid details");
			Account acc = accountRepository.findByAccNum(accNum);
			if(acc==null) throw new Exception("Invalid details");
			return "Balance : "+acc.getBalance();
		}
		catch(Exception e) {
			return "Cannot update";
		}
	}
	
	public String transferMoney(TransferMoneyDto transferMoneyDto) {
		try {
			if(!TransferMoneyValidation.validate(transferMoneyDto)) throw new Exception("Invalid details");
			Account accSen = accountRepository.findByAccNum(transferMoneyDto.getAccNumSender());
			Account accRec = accountRepository.findByAccNum(transferMoneyDto.getAccNumReceiver());
			float value = transferMoneyDto.getBalance();
			if(accSen==null || accRec==null) throw new Exception("Invalid details");
			if(accSen.getBalance()-value<0) throw new Exception("Invalid details");
			accSen.setBalance(accSen.getBalance()- value);
			accRec.setBalance(accRec.getBalance()+ value);
			accountRepository.save(accSen);
			accountRepository.save(accRec);
					
		}
		catch(Exception e) {
			return "Cannot Transfer Money";
		}
		return "Money Transfered Successfully";
	}
	
	public byte[] getStatement(BankStatementDto dankStatementDto) {
		try {
			if(!BankStatementValidation.validate(dankStatementDto)) throw new Exception("Invalid details");
			return IOUtils.toByteArray(BankStatementGenerator.bankStatementPdfReport());
		}
		catch(Exception e) {
			 
		}
		return null;
	}
	
	
	
}
