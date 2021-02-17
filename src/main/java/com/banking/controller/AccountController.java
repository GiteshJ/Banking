package com.banking.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.banking.common.CommonConstants;
import com.banking.customException.AccountLinkedException;
import com.banking.customException.DuplicateAccNumException;
import com.banking.customException.InsufficientBalanceException;
import com.banking.customException.InvalidDataException;
import com.banking.customException.UserNotFoundException;
import com.banking.dto.AccountDetailsDto;
import com.banking.dto.BankStatementDto;
import com.banking.dto.LinkAccountDto;
import com.banking.dto.TransferMoneyDto;
import com.banking.dto.UpdateBalanceDto;
import com.banking.model.Account;
import com.banking.response.ApiEntity;
import com.banking.response.ApiResponseObject;
import com.banking.response.error.ApiError;
import com.banking.service.AccountService;

@RestController
@RequestMapping("/account")
public class AccountController {

	@Autowired
	AccountService accountService;
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@PostMapping("/addAccount")
	public ResponseEntity<ApiResponseObject> creatAccount(@RequestBody Account account) {
		HttpStatus status;
		HttpHeaders httpHeader = new HttpHeaders();
		String message;
		try {
			accountService.creatAccount(account);
			status = HttpStatus.OK;
			message = CommonConstants.ACCOUNT_CREATION_SUCCESS_MESSAGE;
			return new ResponseEntity<>(new ApiEntity(message),httpHeader,status);
			
		} 
		catch (InvalidDataException ex) {
			logger.error(ex.getMessage());
			status = HttpStatus.BAD_REQUEST;
			message = ex.getMessage();
			return new ResponseEntity<>(new ApiError(message),httpHeader,status); 
		}
		catch (DuplicateAccNumException ex) {
			logger.error(ex.getMessage());
			status = HttpStatus.CONFLICT;
			message = ex.getMessage();
			return new ResponseEntity<>(new ApiError(message),httpHeader,status); 
		}
		catch (Exception ex) {
			logger.error(ex.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			message = CommonConstants.SERVER_ERROR;
			return new ResponseEntity<>(new ApiError(message),httpHeader,status); 
		}
	}
	
	@PostMapping("/linkAccount")
	public ResponseEntity<ApiResponseObject> linkAccount(@RequestBody LinkAccountDto linkAccountDto) {
		HttpStatus status;
		HttpHeaders httpHeader = new HttpHeaders();
		String message;
		try {
			accountService.linkAccount(linkAccountDto);
			status = HttpStatus.OK;
			message = CommonConstants.ACCOUNT_LINKED_SUCCESS_MESSAGE;
			return new ResponseEntity<>(new ApiEntity(message),httpHeader,status);
			
		} 
		catch (InvalidDataException ex) {
			logger.error(ex.getMessage());
			status = HttpStatus.BAD_REQUEST;
			message = ex.getMessage();
			return new ResponseEntity<>(new ApiError(message),httpHeader,status); 
		}
		catch (UserNotFoundException ex) {
			logger.error(ex.getMessage());
			status = HttpStatus.OK;
			message = ex.getMessage();
			return new ResponseEntity<>(new ApiError(message),httpHeader,status); 
		}
		catch (AccountLinkedException ex) {
			logger.error(ex.getMessage());
			status = HttpStatus.OK;
			message = ex.getMessage();
			return new ResponseEntity<>(new ApiError(message),httpHeader,status); 
		}
		catch (Exception ex) {
			logger.error(ex.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			message = CommonConstants.SERVER_ERROR;
			return new ResponseEntity<>(new ApiError(message),httpHeader,status); 
		}
	}
	
	@PutMapping("/updateBalance")
	public ResponseEntity<ApiResponseObject> updateBalance(@RequestBody UpdateBalanceDto updateBalanceDto) {
		HttpStatus status;
		HttpHeaders httpHeader = new HttpHeaders();
		String message;
		try {
			accountService.updateBalance(updateBalanceDto);
			status = HttpStatus.OK;
			message = CommonConstants.BALANCE_UPDATE_SUCCESS_MESSAGE;
			return new ResponseEntity<>(new ApiEntity(message),httpHeader,status);
			
		} 
		catch (InvalidDataException ex) {
			logger.error(ex.getMessage());
			status = HttpStatus.BAD_REQUEST;
			message = ex.getMessage();
			return new ResponseEntity<>(new ApiError(message),httpHeader,status); 
		}
		catch (Exception ex) {
			logger.error(ex.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			message = CommonConstants.SERVER_ERROR;
			return new ResponseEntity<>(new ApiError(message),httpHeader,status); 
		}
	}
	
	@GetMapping("/balance/{accNum}")
	public ResponseEntity<ApiResponseObject> getBalance(@PathVariable Integer accNum) {
		 
		HttpStatus status;
		HttpHeaders httpHeader = new HttpHeaders();
		String message;
		try {
			String data = accountService.getBalance(accNum);
			status = HttpStatus.OK;
			message = CommonConstants.BALANCE_FECTH_SUCCESS_MESSAGE;
			return new ResponseEntity<>(new ApiEntity(message,data),httpHeader,status);
			
		} 
		catch (InvalidDataException ex) {
			logger.error(ex.getMessage());
			status = HttpStatus.BAD_REQUEST;
			message = ex.getMessage();
			return new ResponseEntity<>(new ApiError(message),httpHeader,status); 
		}
		catch (Exception ex) {
			logger.error(ex.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			message = CommonConstants.SERVER_ERROR;
			return new ResponseEntity<>(new ApiError(message),httpHeader,status); 
		}
	}
	
	@PostMapping("/tranferMoney")
	public ResponseEntity<ApiResponseObject> transferMoney(@RequestBody TransferMoneyDto transferMoneyDto) { 
		HttpStatus status;
		HttpHeaders httpHeader = new HttpHeaders();
		String message;
		try {
			accountService.transferMoney(transferMoneyDto);
			status = HttpStatus.OK;
			message = CommonConstants.MONEY_TRANSFER_SUCCESS_MESSAGE;
			return new ResponseEntity<>(new ApiEntity(message),httpHeader,status);
			
		} 
		catch (InvalidDataException ex) {
			logger.error(ex.getMessage());
			status = HttpStatus.BAD_REQUEST;
			message = ex.getMessage();
			return new ResponseEntity<>(new ApiError(message),httpHeader,status); 
		}
		catch (InsufficientBalanceException ex) {
			logger.error(ex.getMessage());
			status = HttpStatus.OK;
			message = ex.getMessage();
			return new ResponseEntity<>(new ApiError(message),httpHeader,status); 
		}
		catch (Exception ex) {
			logger.error(ex.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			message = CommonConstants.SERVER_ERROR;
			return new ResponseEntity<>(new ApiError(message),httpHeader,status); 
		}
	}
	
	@PostMapping("/statement")
	public ResponseEntity<byte[]> getStatement(@RequestBody BankStatementDto bankStatementDto) {
		
		
		byte[] contents = accountService.getStatement(bankStatementDto);
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_PDF);
	    String filename = "statement.pdf";
	    headers.setContentDispositionFormData(filename, filename);
	    ResponseEntity<byte[]> response = new ResponseEntity<>(contents, headers, HttpStatus.OK);
	    
	    logger.info(CommonConstants.BANK_STATEMENT_SUCCESS_MESSAGE);
	    
	    return response;
		
	}
	
	@GetMapping("/all")
	public ResponseEntity<ApiResponseObject> getAllAccounts() {
		 
		HttpStatus status;
		HttpHeaders httpHeader = new HttpHeaders();
		String message;
		try {
			List<AccountDetailsDto> data = accountService.getAllAccount();
			status = HttpStatus.OK;
			message = CommonConstants.BALANCE_FECTH_SUCCESS_MESSAGE;
			return new ResponseEntity<>(new ApiEntity(message,data),httpHeader,status);
			
		} 
		catch (Exception ex) {
			logger.error(ex.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			message = CommonConstants.SERVER_ERROR;
			return new ResponseEntity<>(new ApiError(message),httpHeader,status); 
		}
	}
}
