package com.dollarsbank.dao;

import java.util.List;

import com.dollarsbank.model.Customer;

public interface CustomerDAO {

	public List<Customer> getAllCustomers();
	
	public Customer getCustomerById(String id);
	
	public Customer getCustomerByPassword(String password);
	
	public boolean addCustomer(Customer customer);
}
