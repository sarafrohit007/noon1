package com.example.noon.services;

import com.example.noon.dto.Book;
import com.example.noon.dto.User;

public interface INoonService {

	void addBook(Book book);

	void addUser(User user);

	void assignBook();

}
