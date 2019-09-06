package com.example.noon.entity;

public class FineDetailInfo {

	private Integer id;
	private User user;
	private double amount;
	private boolean paid = false;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public boolean isPaid() {
		return paid;
	}

	public void setPaid(boolean paid) {
		this.paid = paid;
	}

	@Override
	public String toString() {
		return "FineDetailInfo [id=" + id + ", user=" + user + ", amount=" + amount + ", paid=" + paid + "]";
	}

}
