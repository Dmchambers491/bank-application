package com.dollarsbank.dao;

import java.util.List;

import com.dollarsbank.model.Account;

public interface AccountDAO {

	public Account getAccountById(String id);
	
	public List<Account> getAccountsByCustomerId(String id);
	
	public boolean addAccount(Account account);
	
	public boolean updateAccount(Account account);
}
