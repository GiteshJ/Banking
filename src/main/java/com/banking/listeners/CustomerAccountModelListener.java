package com.banking.listeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

import com.banking.model.CustomerAccount;
import com.banking.service.SequenceGeneratorService;


@Component
public class CustomerAccountModelListener extends AbstractMongoEventListener<CustomerAccount>{
	private SequenceGeneratorService sequenceGenerator;

    @Autowired
    public CustomerAccountModelListener(SequenceGeneratorService sequenceGenerator) {
        this.sequenceGenerator = sequenceGenerator;
    }

    @Override
    public void onBeforeConvert(BeforeConvertEvent<CustomerAccount> event) {
        if (event.getSource().getId() < 1) {
            event.getSource().setId(sequenceGenerator.generateSequence(CustomerAccount.SEQUENCE_NAME));
        }
    }
}
