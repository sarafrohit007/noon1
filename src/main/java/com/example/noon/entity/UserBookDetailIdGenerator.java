package com.example.noon.entity;

import java.util.Comparator;

public class UserBookDetailIdGenerator implements Comparator<UserBookDetail>{

	@Override
	public int compare(UserBookDetail userBookDetail1, UserBookDetail userBookDetail2) {
		return userBookDetail1.getId() - userBookDetail2.getId();
	}

}
