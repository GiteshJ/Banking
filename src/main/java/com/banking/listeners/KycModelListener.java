package com.banking.listeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

import com.banking.model.Kyc;
import com.banking.service.SequenceGeneratorService;


@Component
public class KycModelListener extends AbstractMongoEventListener<Kyc>{
	private SequenceGeneratorService sequenceGenerator;

    @Autowired
    public KycModelListener(SequenceGeneratorService sequenceGenerator) {
        this.sequenceGenerator = sequenceGenerator;
    }

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Kyc> event) {
        if (event.getSource().getId() < 1) {
            event.getSource().setId(sequenceGenerator.generateSequence(Kyc.SEQUENCE_NAME));
        }
    }
}
