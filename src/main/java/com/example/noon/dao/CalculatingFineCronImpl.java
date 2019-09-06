package com.example.noon.dao;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.noon.entity.FineDetailInfo;
import com.example.noon.entity.User;
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
		LinkedHashMap<User, LinkedList<FineDetailInfo>> userFinedetailsMap = NoonServiceDaoImpl.userFineDetails;
		if (userBookList != null && userBookList.size() > 0) {
			for (UserBookDetail userBookDetail : userBookList) {
				Date tentativeReturnDate = userBookDetail.getTentativeReturnDate();
				Date currentDate = new Date();
				if (currentDate.compareTo(tentativeReturnDate) > 0) {
					LinkedList<FineDetailInfo> userFineList = userFinedetailsMap.get(userBookDetail.getUser());
					if (userFineList == null) {
						
					} else {

					}
				}
			}
		}
	}
}
