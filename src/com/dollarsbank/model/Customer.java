package com.dollarsbank.model;

public class Customer {

	private String id;
	private String name;
	private String address;
	private String phone_number;
	private String password;

	public Customer(String id, String name, String address, String phone_number, String password) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.phone_number = phone_number;
		this.password = password;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone_number() {
		return phone_number;
	}

	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", name=" + name + ", address=" + address + ", phone_number=" + phone_number
				+ ", password=" + password + "]";
	}

}
