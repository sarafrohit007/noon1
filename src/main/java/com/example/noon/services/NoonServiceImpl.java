package com.example.noon.services;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.noon.dao.INoonServiceDao;
import com.example.noon.dto.Book;
import com.example.noon.dto.User;
import com.example.noon.entity.BookIdGenerator;
import com.example.noon.entity.UserIdGenerator;

@Service("noonServiceImpl")
public class NoonServiceImpl implements INoonService {
	
	@Autowired
	private INoonServiceDao noonServiceDao;

	

	@Override
	public void addBook(Book book) {
		noonServiceDao.addBook(book);
		
	}

	@Override
	public void addUser(User user) {
		noonServiceDao.addUser(user);
		
	}

}
