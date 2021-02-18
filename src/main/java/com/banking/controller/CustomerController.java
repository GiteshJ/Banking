package com.banking.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banking.common.CommonConstants;
import com.banking.customException.DuplicateUserNameException;
import com.banking.customException.InvalidDataException;
import com.banking.customException.UserNotFoundException;
import com.banking.dto.CustomerDetailsDto;
import com.banking.dto.KycDetailsDto;
import com.banking.dto.UserDto;
import com.banking.response.ApiEntity;
import com.banking.response.ApiResponseObject;
import com.banking.response.error.ApiError;
import com.banking.service.CustomerService;

@RestController
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	CustomerService customerService;
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@PostMapping("/addCustomer")
	public ResponseEntity<ApiResponseObject> addCustomer(@RequestBody UserDto user) {
		
		HttpStatus status;
		HttpHeaders httpHeader = new HttpHeaders();
		String message;
		
		try {
			customerService.addCustomer(user);
			status = HttpStatus.OK;
			message = CommonConstants.CUSTOMER_REGISTER_SUCCESS_MESSAGE;
			return new ResponseEntity<>(new ApiEntity(message),httpHeader,status);
			
		} 
		catch (InvalidDataException ex) {
			logger.error(ex.getMessage());
			status = HttpStatus.BAD_REQUEST;
			message = ex.getMessage();
			return new ResponseEntity<>(new ApiError(message),httpHeader,status); 
		}
		catch (DuplicateUserNameException ex) {
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

	@DeleteMapping("/deleteCustomer/{username}")
	public ResponseEntity<ApiResponseObject> deleteCustomer(@PathVariable String username) {
		HttpStatus status;
		HttpHeaders httpHeader = new HttpHeaders();
		String message;
		
		try {
			customerService.deleteCustomer(username);
			status = HttpStatus.OK;
			message = CommonConstants.CUSTOMER_DELETED_SUCCESS_MESSAGE;
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
		catch (Exception ex) {
			logger.error(ex.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			message = CommonConstants.SERVER_ERROR;
			return new ResponseEntity<>(new ApiError(message),httpHeader,status); 
		}
	}
	
	@PostMapping("/updateKYC")
	public ResponseEntity<ApiResponseObject> updateKyc(@RequestBody KycDetailsDto kycDetailsDto) {
		HttpStatus status;
		HttpHeaders httpHeader = new HttpHeaders();
		String message;
		
		try {
			customerService.updateKyc(kycDetailsDto);
			status = HttpStatus.OK;
			message = CommonConstants.KYC_UPDATED_SUCCESS_MESSAGE;
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
		catch (Exception ex) {
			logger.error(ex.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			message = CommonConstants.SERVER_ERROR;
			return new ResponseEntity<>(new ApiError(message),httpHeader,status); 
		}
	}
	
	@GetMapping("/{username}")
	public ResponseEntity<ApiResponseObject> getCustomer(@PathVariable String username) {
		
		HttpStatus status;
		HttpHeaders httpHeader = new HttpHeaders();
		String message;
		
		try {
			CustomerDetailsDto data =  customerService.getCustomerDetails(username);
			status = HttpStatus.OK;
			message = CommonConstants.CUSTOMER_DETAILS_SUCCESS_MESSAGE;
			return new ResponseEntity<>(new ApiEntity(message,data),httpHeader,status);
			
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
		catch (Exception ex) {
			logger.error(ex.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			message = CommonConstants.SERVER_ERROR;
			return new ResponseEntity<>(new ApiError(message),httpHeader,status); 
		}
	}
	
	@GetMapping("/all")
	public ResponseEntity<ApiResponseObject> getAllCustomer() {
		
		HttpStatus status;
		HttpHeaders httpHeader = new HttpHeaders();
		String message;
		
		try {
			List<CustomerDetailsDto> data =  customerService.getAllCustomerDetails();
			status = HttpStatus.OK;
			message = CommonConstants.CUSTOMER_DETAILS_SUCCESS_MESSAGE;
			return new ResponseEntity<>(new ApiEntity(message,data),httpHeader,status);
			
		} 
		catch (Exception ex) {
			logger.error(ex.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			message = CommonConstants.SERVER_ERROR;
			return new ResponseEntity<>(new ApiError(message),httpHeader,status); 
		}
	}
	
	//Apis to Test @Cacheable annotation works only with entity return type and not with 
	//response entities for that will have to use @cache custom annotation and AOP to save and read cache
	@GetMapping("/allTest")
	@Cacheable(value="customerMap")
	public List<CustomerDetailsDto> getAllCustomerTest() {
		
		try {
			List<CustomerDetailsDto> data =  customerService.getAllCustomerDetailsTest();
			return data;
			
		} 
		catch (Exception ex) {
			logger.error(ex.getMessage());
			return null;
		}
	}
}
