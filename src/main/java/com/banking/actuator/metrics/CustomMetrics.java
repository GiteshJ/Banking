package com.banking.actuator.metrics;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

@Component
public class CustomMetrics {

	
	@Autowired
	MeterRegistry meterRegistry;
	
	private Counter objectsCount;
    private AtomicLong averageObjectSize;
    
	@Bean
	public Counter getCounter() {
		objectsCount = Counter.builder("storage.tansactions.count")   
	        .tag("type","count")
	        .description("The number of money transactions in the system")
	        .register(meterRegistry);
		return objectsCount;
	}
   
	@Bean
	public AtomicLong getAverage() {
		averageObjectSize = meterRegistry.gauge("storage.moneyTransfer.total", new AtomicLong());
		return averageObjectSize;
	}
    
}
