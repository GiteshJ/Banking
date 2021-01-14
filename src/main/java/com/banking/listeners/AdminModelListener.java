package com.banking.listeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

import com.banking.model.Admin;
import com.banking.service.SequenceGeneratorService;

@Component
public class AdminModelListener extends AbstractMongoEventListener<Admin>{
	private SequenceGeneratorService sequenceGenerator;

    @Autowired
    public AdminModelListener(SequenceGeneratorService sequenceGenerator) {
        this.sequenceGenerator = sequenceGenerator;
    }

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Admin> event) {
        if (event.getSource().getId() < 1) {
            event.getSource().setId(sequenceGenerator.generateSequence(Admin.SEQUENCE_NAME));
        }
    }
}
