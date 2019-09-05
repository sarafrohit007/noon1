package com.example.noon.dao;

import com.example.noon.dto.Book;
import com.example.noon.dto.User;

public interface INoonServiceDao {

	void addBook(Book book);

	void addUser(User user);

	void assignBook();

}
