package com.example.noon.dao;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.noon.entity.FineDetailIdGenerator;
import com.example.noon.entity.FineDetailInfo;
import com.example.noon.entity.User;
import com.example.noon.entity.UserBookDetail;

/* Cron to calculate user fine will run once every midnight. But controller is also ther to manually calculate*/

@Component("calculatingFineCronImpl")
public class CalculatingFineCronImpl {
	
	public static final Integer PER_LATE_DAY_FINE  = 10;

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
						FineDetailInfo fineDetail = new FineDetailInfo();
						LinkedList<FineDetailInfo> fineDetails = NoonServiceDaoImpl.allFineDetails;
						if (fineDetails != null && fineDetails.size() > 0) {
							Collections.sort(NoonServiceDaoImpl.allFineDetails, new FineDetailIdGenerator());
							int maxId = NoonServiceDaoImpl.allFineDetails
									.get(NoonServiceDaoImpl.allFineDetails.size() - 1).getId();
							fineDetail.setId(maxId+1);
							fineDetail.setBook(userBookDetail.getBook());
							fineDetail.setAmount(PER_LATE_DAY_FINE);
							fineDetail.setUser(userBookDetail.getUser());
							fineDetail.setPaid(false);
							NoonServiceDaoImpl.allFineDetails.add(fineDetail);
						} else {
							fineDetail.setId(1);
							fineDetail.setBook(userBookDetail.getBook());
							fineDetail.setAmount(PER_LATE_DAY_FINE);
							fineDetail.setUser(userBookDetail.getUser());
							fineDetail.setPaid(false);
							NoonServiceDaoImpl.allFineDetails.add(fineDetail);
						}
						NoonServiceDaoImpl.userFineDetails.put(userBookDetail.getUser(), NoonServiceDaoImpl.allFineDetails);
					} else {
						for (FineDetailInfo fineDetail : userFineList) {
							if (fineDetail.getUser().equals(userBookDetail.getUser())
									&& fineDetail.getBook().equals(userBookDetail.getBook())) {
								fineDetail.setAmount(fineDetail.getAmount()+PER_LATE_DAY_FINE);
							}
						}
					}
				}
			}
		}
	}
}
