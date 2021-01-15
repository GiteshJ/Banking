package com.banking.jobSchedulers;

import java.util.List;

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
	
	
	//@Scheduled(cron = "0 0/5 * * * *")
	@Scheduled(cron = "0 0 12 31 3 ?")
	public void calculateInterest() {
		try {
			System.out.println("Running Scheduler");
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
