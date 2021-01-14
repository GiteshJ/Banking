package com.banking.listeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

import com.banking.model.AccountType;
import com.banking.service.SequenceGeneratorService;

@Component
public class AccountTypeModelListener extends AbstractMongoEventListener<AccountType>{
	private SequenceGeneratorService sequenceGenerator;

    @Autowired
    public AccountTypeModelListener(SequenceGeneratorService sequenceGenerator) {
        this.sequenceGenerator = sequenceGenerator;
    }

    @Override
    public void onBeforeConvert(BeforeConvertEvent<AccountType> event) {
        if (event.getSource().getId() < 1) {
            event.getSource().setId(sequenceGenerator.generateSequence(AccountType.SEQUENCE_NAME));
        }
    }
}
