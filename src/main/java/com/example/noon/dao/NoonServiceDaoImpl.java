package com.example.noon.dao;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import org.springframework.stereotype.Repository;

import com.example.noon.dto.Book;
import com.example.noon.dto.BookBorrowInfoDTO;
import com.example.noon.dto.User;
import com.example.noon.entity.BookIdGenerator;
import com.example.noon.entity.FineDetailInfo;
import com.example.noon.entity.UserBookDetail;
import com.example.noon.entity.UserIdGenerator;

@Repository("noonServiceDaoImpl")
public class NoonServiceDaoImpl implements INoonServiceDao {

	public static LinkedList<com.example.noon.entity.Book> bookList = new LinkedList<com.example.noon.entity.Book>(); // Total number of books in the system
	public static LinkedHashMap<com.example.noon.entity.Book, Integer> bookCountMap = new LinkedHashMap<com.example.noon.entity.Book, Integer>(); //count of each different type book. suppose a book- with 3 count, b-book with 5 count. 
	public static LinkedList<com.example.noon.entity.User> userList = new LinkedList<com.example.noon.entity.User>(); // total number of users
	public static LinkedHashMap<com.example.noon.entity.User, LinkedList<com.example.noon.entity.UserBookDetail>> userBookMap = new LinkedHashMap<com.example.noon.entity.User, LinkedList<com.example.noon.entity.UserBookDetail>>(); // map of books issued to customer
	public static LinkedHashMap<com.example.noon.entity.Book, Integer> bookDistributedCountMap = new LinkedHashMap<com.example.noon.entity.Book, Integer>(); // boos distributed count map. suppose a - book whose 2 copies are distributed
	public static LinkedHashMap<com.example.noon.entity.User, LinkedList<com.example.noon.entity.FineDetailInfo>> userFineDetails = new LinkedHashMap<com.example.noon.entity.User, LinkedList<com.example.noon.entity.FineDetailInfo>>(); // fine datil map of user.
	
	public static final Integer maxFineLimitToBlockUser = 100;

	@Override
	public void addBook(Book book) {
		com.example.noon.entity.Book entityBook = new com.example.noon.entity.Book();
		if (bookList != null && bookList.size() > 0) {
			entityBook.setBookName(book.getBookName());
			entityBook.setAuthorName(book.getAuthorName());
			entityBook.setPublisher(book.getPublisher());
			Collections.sort(bookList, new BookIdGenerator());
			int maxId = bookList.get(bookList.size() - 1).getId();
			entityBook.setId(maxId + 1);
			bookList.add(entityBook);
			if (bookCountMap.containsKey(entityBook)) {
				bookCountMap.put(entityBook, bookCountMap.get(entityBook) + 1);
			} else {
				bookCountMap.put(entityBook, 1);
			}
		} else {
			entityBook.setBookName(book.getBookName());
			entityBook.setAuthorName(book.getAuthorName());
			entityBook.setPublisher(book.getPublisher());
			entityBook.setId(1);
			bookList.add(entityBook);
			bookCountMap.put(entityBook, 1);

		}

	}

	@Override
	public void addUser(User user) {
		com.example.noon.entity.User entityUser = new com.example.noon.entity.User();
		entityUser.setUserName(user.getUserName());
		entityUser.setLoginName(user.getLoginName());
		if (userList != null && userList.size() > 0) {
			if (userList.contains(entityUser)) {
				return;
			}
			Collections.sort(userList, new UserIdGenerator());
			int maxId = userList.get(userList.size() - 1).getId();
			entityUser.setId(maxId + 1);
			entityUser.setCreatedDate(new Date());
			entityUser.setMembershipActive(true);
			userList.add(entityUser);
		} else {
			entityUser.setId(1);
			entityUser.setCreatedDate(new Date());
			entityUser.setMembershipActive(true);
			userList.add(entityUser);
		}

	}

	@Override
	public void assignBook(BookBorrowInfoDTO bookBorrowInfo) {
		com.example.noon.entity.Book entityBook = new com.example.noon.entity.Book();
		entityBook.setAuthorName(bookBorrowInfo.getAuthorName());
		entityBook.setBookName(bookBorrowInfo.getBookName());
		entityBook.setPublisher(bookBorrowInfo.getPublisher());
		if (!bookList.contains(entityBook)) {
			return; // checking for if book already in the system or not....
		} else {
			int totalBookCount = bookCountMap.get(entityBook);
			int distributeBookCount = bookCountMap.get(entityBook);
			if (distributeBookCount >= totalBookCount) {
				return; // checking for max.. quantity of this book distributed to toal quantity of this
						// book.
			}
		}
		com.example.noon.entity.User user = new com.example.noon.entity.User();

		user.setLoginName(bookBorrowInfo.getLoginName());

		if (!userList.contains(user)) {
			return; // checking for user not available in the system
		}

		int alreadyOrderedCount = 0;
		LinkedList<com.example.noon.entity.UserBookDetail> userBookDetails = userBookMap.get(user);
		if (userBookDetails != null && userBookDetails.size() > 0) {
			for (com.example.noon.entity.UserBookDetail userBookDetail : userBookDetails) {
				if (!userBookDetail.isReturned()) {
					alreadyOrderedCount++;
					break; // Check for single/maximum book order
				}
			}
		}

		if (alreadyOrderedCount > 0) {
			return; // customer has already booked more than 1 book.
		}
		
		double fineOnAsingleUser = 0;
		LinkedList<com.example.noon.entity.FineDetailInfo> fineDetailsList = userFineDetails.get(user);
		
		if (fineDetailsList != null && fineDetailsList.size() > 0) {
			for(FineDetailInfo fineDetail :  fineDetailsList) {
				if (!fineDetail.isPaid()) {
					fineOnAsingleUser+=fineDetail.getAmount();
				}
			}
		}
		
		if (fineOnAsingleUser > maxFineLimitToBlockUser) {
			return; // check for maximum fine limit
		}
		
		Integer totalNumberOfCopiesOfThisBook = bookCountMap.get(entityBook);
		
		Integer totalCpiesOfThisBookDistributed = bookDistributedCountMap.get(entityBook);
		
		if (totalNumberOfCopiesOfThisBook != null && totalCpiesOfThisBookDistributed != null
				&& totalCpiesOfThisBookDistributed >= totalNumberOfCopiesOfThisBook) {
			return; // distributed copies of this book are more than the total number of books.
		}
		
		// if all the test cases passed// then 
		if(userBookDetails == null) {
			userBookDetails = new LinkedList<com.example.noon.entity.UserBookDetail>();
		}
		
		Calendar cal = Calendar.getInstance();
		Date issueDate = cal.getTime();
		cal.add(Calendar.DAY_OF_YEAR, 7);
		Date returnDate = cal.getTime();
		UserBookDetail userBookDetail = new UserBookDetail();
		userBookDetail.setUser(user);
		userBookDetail.setBook(entityBook);
		userBookDetail.setDate(issueDate);
		//userBookDetails.add(entityBook);
		//userBookMap.put(user,bookList); // putting books list booked by user  

		if (totalNumberOfCopiesOfThisBook == null) {
			bookCountMap.put(entityBook, 1);
		} else {
			bookCountMap.put(entityBook, bookCountMap.get(entityBook) + 1);
		}

		if (totalCpiesOfThisBookDistributed == null) {
			bookDistributedCountMap.put(entityBook, 1);
		} else {
			bookDistributedCountMap.put(entityBook, bookDistributedCountMap.get(entityBook) + 1);
		}
	}

}
