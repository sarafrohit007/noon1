package com.example.noon.entity;

import java.util.Comparator;

public class UserIdGenerator implements Comparator<User>{

	@Override
	public int compare(User user1, User user2) {
		return user1.getId() - user2.getId();
	}

}
