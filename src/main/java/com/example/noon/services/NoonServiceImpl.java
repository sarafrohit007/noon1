package com.example.noon.services;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import org.springframework.stereotype.Service;

import com.example.noon.dto.Book;
import com.example.noon.dto.User;
import com.example.noon.entity.BookIdGenerator;
import com.example.noon.entity.UserIdGenerator;

@Service("noonServiceImpl")
public class NoonServiceImpl implements INoonService {

	public static LinkedList<com.example.noon.entity.Book> bookList = new LinkedList<com.example.noon.entity.Book>();
	public static LinkedHashMap<com.example.noon.entity.Book, Integer> bookInfoMap = new LinkedHashMap<com.example.noon.entity.Book, Integer>();
	public static LinkedList<com.example.noon.entity.User> userList = new LinkedList<com.example.noon.entity.User>();

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
			if (bookInfoMap.containsKey(entityBook)) {
				bookInfoMap.put(entityBook, bookInfoMap.get(entityBook) + 1);
			} else {
				bookInfoMap.put(entityBook, 1);
			}
		} else {
			entityBook.setBookName(book.getBookName());
			entityBook.setAuthorName(book.getAuthorName());
			entityBook.setPublisher(book.getPublisher());
			entityBook.setId(1);
			bookList.add(entityBook);
			bookInfoMap.put(entityBook, 1);

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

}
