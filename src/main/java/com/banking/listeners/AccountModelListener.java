package com.banking.listeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

import com.banking.model.Account;
import com.banking.service.SequenceGeneratorService;

@Component
public class AccountModelListener extends AbstractMongoEventListener<Account>{
	private SequenceGeneratorService sequenceGenerator;

    @Autowired
    public AccountModelListener(SequenceGeneratorService sequenceGenerator) {
        this.sequenceGenerator = sequenceGenerator;
    }

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Account> event) {
        if (event.getSource().getId() < 1) {
            event.getSource().setId(sequenceGenerator.generateSequence(Account.SEQUENCE_NAME));
        }
    }
}
