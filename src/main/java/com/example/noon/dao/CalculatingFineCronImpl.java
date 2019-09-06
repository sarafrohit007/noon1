package com.example.noon.dao;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component("calculatingFineCronImpl")
public class CalculatingFineCronImpl {

	@Scheduled(cron = "0 0 12 * * *")
	public void calculateFineCron() {
		System.out.println("Inside calculating fine through cron....");
		calculateFineCronUtil();
	}

	private void calculateFineCronUtil() {
		
	}
}
