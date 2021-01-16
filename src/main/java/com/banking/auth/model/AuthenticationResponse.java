package com.banking.auth.model;

import java.io.Serializable;

public class AuthenticationResponse implements Serializable {


	private static final long serialVersionUID = 8817995236900393164L;
	private final String jwt;

    public AuthenticationResponse(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }
}
