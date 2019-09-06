package com.example.noon.dao;

import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.noon.entity.UserBookDetail;

@Component("calculatingFineCronImpl")
public class CalculatingFineCronImpl {

	
	@Scheduled(cron = "0 0 12 * * *")
	public void calculateFineCron() {
		System.out.println("Inside calculating fine through cron....");
		calculateFineCronUtil();
	}

	private void calculateFineCronUtil() {
		List<UserBookDetail> userBookList = NoonServiceDaoImpl.allUserBookDetails;
		if (userBookList != null && userBookList.size() > 0) {
			
		}
	}
}
