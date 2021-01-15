package com.banking.auth.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banking.auth.model.AuthenticationRequest;
import com.banking.auth.model.AuthenticationResponse;
import com.banking.auth.model.TokenBlacklistSet;
import com.banking.auth.service.MyUserDetailsService;
import com.banking.jwtUtility.JwtUtil;

@RestController
public class AuthenticationController {
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
    TokenBlacklistSet tokenBlacklistSet;
	
	@Autowired
	private JwtUtil jwtTokenUtil;

	@Autowired
	private MyUserDetailsService userDetailsService;
	@Autowired
    private JwtUtil jwtUtil;
	@RequestMapping({ "/hello" })
	public String firstPage() {
		return "Hello World";
	}

	@PostMapping(value = "/authenticate")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {

		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
			);
		}
		catch (BadCredentialsException e) {
			throw new Exception("Incorrect username or password", e);
		}


		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());

		final String token = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(new AuthenticationResponse(token));
	}
	
	@GetMapping(value = "/signout")
	public String logout(HttpServletRequest request) throws Exception {
		final String authorizationHeader = request.getHeader("Authorization");

        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            tokenBlacklistSet.blackListedTokens.add(jwt);
        }
        return "Logged Out Successfully";
	}
}
