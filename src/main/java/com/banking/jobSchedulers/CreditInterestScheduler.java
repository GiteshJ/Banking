package com.banking.jobSchedulers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.banking.common.CommonConstants;
import com.banking.model.Account;
import com.banking.repository.AccountRepository;

@Component
public class CreditInterestScheduler {
	@Autowired
	AccountRepository accountRepository;
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	//@Scheduled(cron = "0 0/5 * * * *")
	@Scheduled(cron = "0 0 12 31 3 ?")
	public void calculateInterest() {
		try {
			logger.info(CommonConstants.SCHEDULER_RUN_NOTIFY);
			List<Account> allAcc = accountRepository.findAll();
			
			allAcc.parallelStream().forEach(acc -> {
				double bal = acc.getBalance();
				double interest = bal*CommonConstants.INTEREST_RATE;
				bal = bal + interest;
				acc.setBalance(bal);
				accountRepository.save(acc);
			});
			
//			for(Account acc:allAcc) {
//				double bal = acc.getBalance();
//				double interest = bal*CommonConstants.INTEREST_RATE;
//				bal = bal + interest;
//				acc.setBalance(bal);
//			}
				
		}
		catch(Exception e){
			
		}
	}
}
