package com.banking.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banking.common.CommonConstants;
import com.banking.customException.DuplicateUserNameException;
import com.banking.customException.InvalidDataException;
import com.banking.dto.UserDto;
import com.banking.response.ApiEntity;
import com.banking.response.ApiResponseObject;
import com.banking.response.error.ApiError;
import com.banking.service.AdminService;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	AdminService adminService;
	
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@PostMapping("/addAdmin")
	public ResponseEntity<ApiResponseObject> saveAdmin(@RequestBody UserDto user) {
		
		HttpStatus status;
		HttpHeaders httpHeader = new HttpHeaders();
		String message;
		try {
			adminService.addAdmin(user);
			status = HttpStatus.OK;
			message = CommonConstants.ADMIN_REGISTER_SUCCESS_MESSAGE;
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
	
}
