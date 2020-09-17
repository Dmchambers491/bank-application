package com.dollarsbank.model;

public class Account {

//	public enum Type {
//		SAVINGS, CHECKING;
//	}

	private int id;
	private double balance;
//	private Type type;
	private double previous_amount;
	private String customer_id;

	public void deposit(double amount) {
		if (amount != 0) {
			balance = balance + amount;
			previous_amount = amount;
		}
	}

	public void withdraw(double amount) {
		if (amount != 0 && amount <= balance) {
			balance = balance - amount;
			previous_amount = -amount;
		}
	}

	public Account() {
		this(0, 0, 0, "N/A");
	}

	public Account(int id, double balance, double previous_amount, String customer_id) {
		super();
		this.id = id;
		this.balance = balance;
//		this.type = type;
		this.previous_amount = previous_amount;
		this.customer_id = customer_id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

//	public Type getType() {
//		return type;
//	}
//
//	public void setType(Type type) {
//		this.type = type;
//	}

	public double getPrevious_amount() {
		return previous_amount;
	}

	public void setPrevious_amount(double previous_amount) {
		this.previous_amount = previous_amount;
	}

	public String getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(String customer_id) {
		this.customer_id = customer_id;
	}

	@Override
	public String toString() {
		return "Account [id=" + id + ", balance=" + balance + ", previous_amount=" + previous_amount + ", customer_id="
				+ customer_id + "]";
	}

}
