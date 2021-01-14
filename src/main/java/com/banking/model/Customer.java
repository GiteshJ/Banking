package com.banking.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Document(collection = "Customer")
public class Customer {
	@Transient
    public static final String SEQUENCE_NAME = "customer_sequence";
	
	@Id
    private long id;
	@Indexed(unique=true)
    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private Long kycDetails;
}
