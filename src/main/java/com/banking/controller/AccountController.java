package com.banking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banking.dto.BankStatementDto;
import com.banking.dto.LinkAccountDto;
import com.banking.dto.TransferMoneyDto;
import com.banking.dto.UpdateBalanceDto;
import com.banking.model.Account;
import com.banking.service.AccountService;

@RestController
@RequestMapping("/account")
public class AccountController {

	@Autowired
	AccountService accountService;
	
	@PostMapping("/addAccount")
	public String creatAccount(@RequestBody Account account) {
		return accountService.creatAccount(account);
	}
	
	@PostMapping("/linkAccount")
	public String linkAccount(@RequestBody LinkAccountDto linkAccountDto) {
		return accountService.linkAccount(linkAccountDto);
	}
	
	@PutMapping("/updateBalance")
	public String updateBalance(@RequestBody UpdateBalanceDto updateBalanceDto) {
		return accountService.updateBalance(updateBalanceDto);
	}
	
	@GetMapping("/balance/{accNum}")
	public String getBalance(@PathVariable Integer accNum) {
		return accountService.getBalance(accNum);
	}
	
	@PostMapping("/tranferMoney")
	public String transferMoney(@RequestBody TransferMoneyDto transferMoneyDto) {
		return accountService.transferMoney(transferMoneyDto);
	}
	
	@PostMapping("/statement")
	public ResponseEntity<byte[]> getStatement(@RequestBody BankStatementDto bankStatementDto) {
		
		byte[] contents = accountService.getStatement(bankStatementDto);
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_PDF);
	    // Here you have to set the actual filename of your pdf
	    String filename = "statement.pdf";
	    headers.setContentDispositionFormData(filename, filename);
	    ResponseEntity<byte[]> response = new ResponseEntity<>(contents, headers, HttpStatus.OK);
	    return response;
		
	}
	
	
}
