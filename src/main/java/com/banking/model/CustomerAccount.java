package com.banking.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Document(collection = "CustomerAccount")
public class CustomerAccount {
	@Transient
    public static final String SEQUENCE_NAME = "customerAccount_sequence";
	
	@Id
    private long id;
    private Integer custId;
    private Integer accId;
}
