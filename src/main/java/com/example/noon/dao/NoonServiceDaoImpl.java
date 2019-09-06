package com.example.noon.dao;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import org.springframework.stereotype.Repository;

import com.example.noon.dto.Book;
import com.example.noon.dto.BookBorrowInfoDTO;
import com.example.noon.dto.User;
import com.example.noon.entity.BookIdGenerator;
import com.example.noon.entity.UserIdGenerator;

@Repository("noonServiceDaoImpl")
public class NoonServiceDaoImpl implements INoonServiceDao {

	public static LinkedList<com.example.noon.entity.Book> bookList = new LinkedList<com.example.noon.entity.Book>();
	public static LinkedHashMap<com.example.noon.entity.Book, Integer> bookCountMap = new LinkedHashMap<com.example.noon.entity.Book, Integer>();
	public static LinkedList<com.example.noon.entity.User> userList = new LinkedList<com.example.noon.entity.User>();
	public static LinkedHashMap<com.example.noon.entity.User, LinkedList<com.example.noon.entity.Book>> userBookMap = new LinkedHashMap<com.example.noon.entity.User, LinkedList<com.example.noon.entity.Book>>();
	public static LinkedHashMap<com.example.noon.entity.Book, Integer> bookDistributedCountMap = new LinkedHashMap<com.example.noon.entity.Book, Integer>();

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
		LinkedList<com.example.noon.entity.Book> bookList = userBookMap.get(user);
		if (bookList != null && bookList.size() > 0) {
			for(com.example.noon.entity.Book book : bookList) {
				if(book.isAlreadyBooked()) {
					alreadyOrderedCount++;
					break; // Check for single/maximum book order
				}
			}
		}
		
		if(alreadyOrderedCount>0) {
			return; // customer has already booked more than 1 book.
		}
		
		
		

	}

}
