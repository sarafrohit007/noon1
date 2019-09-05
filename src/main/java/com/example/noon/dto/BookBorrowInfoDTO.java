package com.example.noon.dto;

import java.util.Date;

public class BookBorrowInfoDTO {

	private String loginName;
	private String bookName;
	private String authorName;
	private String publisher;
	private Date toReturnDate;

	public Date getToReturnDate() {
		return toReturnDate;
	}

	public void setToReturnDate(Date toReturnDate) {
		this.toReturnDate = toReturnDate;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	@Override
	public String toString() {
		return "BookBorrowInfoDTO [loginName=" + loginName + ", bookName=" + bookName + ", authorName=" + authorName
				+ ", publisher=" + publisher + ", toReturnDate=" + toReturnDate + "]";
	}

}
