package com.example.noon.dto;

import java.util.Date;

public class BookBorrowInfo {

	private Integer bookId;
	private Integer userId;
	private Date borrowDate;
	private Date toReturnDate;
	private boolean returned;

	public Integer getBookId() {
		return bookId;
	}

	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Date getBorrowDate() {
		return borrowDate;
	}

	public void setBorrowDate(Date borrowDate) {
		this.borrowDate = borrowDate;
	}

	public Date getToReturnDate() {
		return toReturnDate;
	}

	public void setToReturnDate(Date toReturnDate) {
		this.toReturnDate = toReturnDate;
	}

	public boolean isReturned() {
		return returned;
	}

	public void setReturned(boolean returned) {
		this.returned = returned;
	}

	@Override
	public String toString() {
		return "BookBorrowInfo [bookId=" + bookId + ", userId=" + userId + ", borrowDate=" + borrowDate
				+ ", toReturnDate=" + toReturnDate + ", returned=" + returned + "]";
	}

}
