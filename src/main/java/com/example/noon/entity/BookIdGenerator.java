package com.example.noon.entity;

import java.util.Comparator;

public class BookIdGenerator implements Comparator<Book>{

	@Override
	public int compare(Book book1, Book book2) {
		return book1.getId() - book2.getId();
	}

	
	

}
